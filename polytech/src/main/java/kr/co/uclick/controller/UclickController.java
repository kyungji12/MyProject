package kr.co.uclick.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.uclick.entity.UserInfo;
import kr.co.uclick.service.UserService;
import kr.co.uclick.entity.ContactInfo;
import kr.co.uclick.service.ContactService;

@Controller // 컨트롤러임을 선언
public class UclickController {

	private static final Logger logger = LoggerFactory.getLogger(UclickController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private ContactService contactService;

	//메인 화면 - 사원 전체 리스트 	
	@GetMapping(value = "/")
	public String allView(Model model) {
		logger.info("=*=*=*=*=*=*=*=*=*=*=*=*=*=allView=*=*=*=*=*=*=*=*=*=*=*=*=*=");
		return "redirect:/0";
	}
	
	@GetMapping(value = "/{page}")
   public String allView(@PathVariable("page") Integer page, Model model){
      Pageable pagee = PageRequest.of(page, 10); //page는 값을 주고, size는 10개씩
      if (page == null || page < 0) {
			page = 0;
		}
      long cntTmpL = userService.userCount();
      double cntTmpD = cntTmpL/10.0;
      long finalVal = (long)(Math.ceil(cntTmpD));
      
      long startTmp = page/5;
      long startRange = startTmp*5;
      long endRange = ((startTmp+1)*5)-1;
      if(endRange >= finalVal) { 
    	  endRange=finalVal-1;
      }
      if (endRange < 0) { //테이블이 비었을 경우
    	  endRange = 0;
      }
      model.addAttribute("totalCnt", cntTmpL);
      model.addAttribute("startRange", startRange);
      model.addAttribute("endRange", endRange);
      model.addAttribute("pageNum", finalVal);
      model.addAttribute("page", userService.getList(pagee));
      return "page";
   }	   

	@GetMapping(value = "/oneView/{id}")
	public String oneView(@PathVariable("id") Long id, Model model) {
		logger.info("=*=*=*=*=*=*=*=*=*=*=*=*=*=oneView=*=*=*=*"+id+"*=*=*=*=*=*=*=*=");
		UserInfo user = userService.findUserInfoById(id);
		model.addAttribute("oneView", user);
		return "oneView";
	}

	@GetMapping(value = "/editForm/{id}") // 수정화면
	public String editForm(@PathVariable("id") Long id, Model model) {
		logger.info("=*=*=*=*=*=*=*=*=*=*=*=*=*=editForm=*=*=*=*=*=*=*=*=*=*=*=*=*=");
		UserInfo user = userService.findUserInfoById(id);
		model.addAttribute("editForm", user);
		return "editForm";
	}

	@GetMapping(value = "newForm.html") // 입력부
	public String newForm() {
		logger.info("=*=*=*=*=*=*=*=*=*=*=*=*=*=newForm=*=*=*=*=*=*=*=*=*=*=*=*=*=");
		return "newForm";// newForm.jsp를 return
	}

	@GetMapping(value = "/oneView/{id}/newContactForm")
	public String newContactForm(@PathVariable("id") Long id, Model model) {
		logger.info("=*=*=*=*=*=*=*=*=*=*=*=*=*=newContactForm=*=*=*=*=*=*=*=*=*=*=*=*=*=");
		model.addAttribute("oneViewCon", userService.findUserInfoById(id)); //이름 알아내기위해 
		model.addAttribute("contact", id);
		return "newContactForm";
	}
	//번호 중복검사
	@ResponseBody
	@GetMapping(value = "/checkExist/{type}/{contact}")
	public String duplCheck(@PathVariable("type") String type, @PathVariable("contact") String contact) {
		return contactService.findContactInfoByTypeAndContact(type, contact);
	}
	
	

	@PostMapping(value = "save.html") // 사원 추가/ 수정 저장화면
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public String save(UserInfo user) {
		logger.info("=*=*=*=*=*=*=*=*=*=*=*=*=*=save=*=*=*=*=*=*=*=*=*=*=*=*=*=");
		try {
			Calendar calt = Calendar.getInstance();
			user.setRegiDate(calt.getTime());
			user.setLastUpdate(calt.getTime());
			userService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/oneView/" + user.getId();
	}
	
	@PostMapping(value = "saveContact.html")
		public String saveContact(HttpServletRequest request){	//saveContact 입력 Form에서 값 request.getParameter해오기
		logger.info("=*=*=*=*=*=*=*=*=*=*=*=*=*=saveContact=*=*=*=*=*=*=*=*=*=*=*=*=*=");
		ContactInfo contactInfo = new ContactInfo();
		
		String tempid = ""; //String type의 빈 id 선언
		String tempuserId = request.getParameter("userId"); //Contact table의 userId 받아오기
		Long userId = Long.parseLong(tempuserId); //String으로 받아온 userId를 Long type으로 바꿔주기
		
		String type = request.getParameter("type"); //Contact table의 연락처 타입 받아오기
		String contact = request.getParameter("contact"); //Contact table의 연락처 받아오기
		
		contactInfo.setUserId(userService.findUserInfoById(userId));
		//Contact table의 userId로 User정보 찾고 ContactInfo UserId로 set해주기  
		contactInfo.setType(type); //받아온 값 set
		contactInfo.setContact(contact); //받아온 값 set
	
		if (request.getParameter("id") == null) { //받아온 id값이 null이면 연락처 신규 입력
			logger.info("=*=*=*=*=*=*=*=*=*=*=*=*=*=연락처 신규 입력=*=*=*=*=*=*=*=*=*=*=*=*=*=");
			UserInfo tmpUser = new UserInfo(); //빈 깡통
			tmpUser.setId(userId); //빈 UserInfo table 깡통에 받아온 값이자 사번인 userId를 입력
			contactInfo.setUserId(tmpUser); //그것을 contactInfo의 userId로 set
			logger.debug("{}",contactInfo.getContact()); //값을 잘 받아왔는지 출력해보기
			
			UserInfo userInfo = userService.findUserInfoById(userId); //userId로 사원 찾고 
			userInfo.getContacts().add(contactInfo);//찾은 사원 정보에 contact정보 추가
			userService.save(userInfo); //연락처가 추가된 사원 정보 저장
		} else { //받아온 id값이 null이면 연락처 신규 입력
			logger.info("=*=*=*=*=*=*=*=*=*=*=*=*=*=연락처 수정=*=*=*=*=*=*=*=*=*=*=*=*=*=");
			tempid = request.getParameter("id");// 수정시 필요한 contact id
			Long id = Long.parseLong(tempid); //String으로 받아온 Id를 Long type으로 바꿔주기
			contactInfo = contactService.findContactInfoById(id);
			
			contactInfo.setType(type);
			contactInfo.setContact(contact);
			contactInfo.setUserId(userService.findUserInfoById(userId));
			contactService.saveContact(contactInfo);
		}
		return "redirect:/oneView/" + contactInfo.getUserId().getId();
	}
	//삭제
	@GetMapping(value = "/delete/{id}") // 삭제화면
	public String delete(@PathVariable("id") Long id, Model model) {
		logger.info("=*=*=*=*=*=*=*=*=*=*=*=*=*=delete=*=*=*=*=*=*=*=*=*=*=*=*=*=");
		try {
			UserInfo user = userService.findUserInfoById(id);
			userService.delete(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/0";
	}
	
	//전체삭제, 선택삭제
	@RequestMapping(value = "/deleteSelection.html", method = RequestMethod.POST)
	public String deleteSelection(HttpServletRequest request, Model model) { //id로 삭제하니까 id값 받기
		logger.info("=*=*=*=*=*=*=*=*=*=*=*=*=*=deleteSelection=*=*=*=*=*=*=*=*=*=*=*=*=*=");
		String[] array = request.getParameterValues("checkOne");
		for (String id : array) {
			UserInfo user = userService.findUserInfoById(Long.parseLong(id));
			userService.delete(user);
		}
		return "redirect:/0";
	}
	
	
	
	// 검색
	@GetMapping(value = "/search/{option}")
	public String search(@PathVariable("option") String option, String key, Model model) {
		logger.info("=*=*=*=*=*=*=*=*=*=*=*=*=*=search=*=*=*=*=*=*=*=*=*=*=*=*=*=");
		model.addAttribute("key", key);
		if (option.equals("all")){ //전체검색
			List<UserInfo> result = new ArrayList<UserInfo>(); //최종결과물이 들어갈 리스트
			List<UserInfo> userInfoAll = userService.findAll(); //전체 user정보 가져오기
			for (UserInfo usertmp : userInfoAll) { //둘이 비교
				if(usertmp.getName().contains(key)) {
					result.add(usertmp);
				}else if(usertmp.getPosition().contains(key)) {
					result.add(usertmp);
				}else if(usertmp.getDepartment().contains(key)) {
					result.add(usertmp);
				}else {
					List<ContactInfo> contacts = usertmp.getContacts();//유저가 가지고있는 연락처 정보를 리스트에 넣고
					for(ContactInfo contactstmp : contacts) {
						if(contactstmp.getContact().contains(key)) {
							result.add(usertmp);
							break;
						}
					}
				}
			}
			model.addAttribute("searchUsers", result);
		}else if (option.equals("name")) {
			model.addAttribute("option", "이름");
			model.addAttribute("searchUsers", userService.findUserInfoByNameLike(key));
		} else if (option.equals("department")) {
			model.addAttribute("option", "부서");
			model.addAttribute("searchUsers", userService.findUserInfoByDepartmentLike(key));
		} else if (option.equals("position")) {
			model.addAttribute("option", "직급");
			model.addAttribute("searchUsers", userService.findUserInfoByPositionLike(key));
		} else if (option.equals("contact")) {
			List<UserInfo> userInfo = new ArrayList<UserInfo>();
			List<ContactInfo> contacts = contactService.findContactInfoByContactLike(key);
			for(ContactInfo ucCont : contacts) {
				userInfo.add(ucCont.getUserId());
			}
			model.addAttribute("searchUsers", userInfo);
		}
		return "search";
	}

////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping(value = "/oneView/{id}/editContactForm/{contactId}")
	public String editContactForm(@PathVariable("id") Long id, @PathVariable("contactId") Long contactId, Model model) {
		logger.info("=*=*=*=*=*=*=*=*=*=*=*=*=*=editContactForm=*=*=*=*=*=*=*=*=*=*=*=*=*=");
		userService.findUserInfoById(id);
		model.addAttribute("contact", contactService.findContactInfoById(contactId));
		model.addAttribute("oneViewCon", userService.findUserInfoById(id)); //이름 알아내기위해 
		model.addAttribute("id", id);
		return "editContactForm";
	}

	@GetMapping(value = "/oneView/{id}/deleteContact/{contactId}")
	public String deleteContact(@PathVariable("id") Long id, @PathVariable("contactId") Long contactId) {
		try {
			contactService.deleteContact(contactId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/oneView/" + id;
	}

}
