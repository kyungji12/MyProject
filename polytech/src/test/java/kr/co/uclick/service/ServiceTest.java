package kr.co.uclick.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
 

import kr.co.uclick.configuration.SpringConfiguration;
import kr.co.uclick.entity.UserInfo;
import kr.co.uclick.entity.ContactInfo;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)

public class ServiceTest {
	@Autowired
	UserService userService;
	@Autowired
	ContactService contactService;

	//사원 추가
	@Ignore
	@Test
	public void addUserTest() {
		UserInfo userInfo = new UserInfo();
		userInfo.setName("test");
		userInfo.setPosition("test");
		userInfo.setDepartment("test");
		userInfo.setEmail("test");
		userService.save(userInfo);
	}
	
	//연락처 추가
	@Ignore
	@Test
	public void addContactTest() {
		Long id = (long) 1; //userId =1 인 사원에 연락처 추가 
		UserInfo userInfo = userService.findUserInfoById(id);
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setType("휴대전화");
		contactInfo.setContact("010-8570-7475");
		contactInfo.setUserId(userInfo);
		contactService.saveContact(contactInfo);		
	}
	
	//사원 정보 수정
	@Ignore
	@Test
	public void editUserTest() {
		UserInfo userInfo = new UserInfo();
		userInfo = userService.findUserInfoById((long)529); //addUserTest에서 생성된 test의 id값
		userInfo.setName("test수정");
		userInfo.setPosition("test수정");
		userInfo.setDepartment("test수정");
		userInfo.setEmail("test수정");
		userService.save(userInfo);
	}
	
	//연락처 수정
	@Ignore
	@Test
	public void editContactTest() {
		ContactInfo contactInfo = new ContactInfo();
		contactInfo = contactService.findContactInfoById((long)625); //addContactTest에서 생성된 id값
		contactInfo.setType("직장");
		contactInfo.setContact("010-8570-7475");
		contactService.saveContact(contactInfo);	
	}
	
	//사원 삭제
	@Ignore
	@Test
	public void deleteUserTest() {
		UserInfo userInfo = new UserInfo();
		userInfo = userService.findUserInfoById((long)529);
		userService.delete(userInfo);
	}
	
	//연락처 삭제
	@Ignore
	@Test
	public void deleteContactTest() {
		Long id = (long)625;
		contactService.deleteContact(id);
	}
	
	//전체검색
	@Ignore
	@Test
	public void searchAll() {
//		String keyword = "실험";
		List<UserInfo> userInfo = new ArrayList<UserInfo>();
		userInfo = userService.findAll();
		for (UserInfo user : userInfo) {
			System.out.println("id: "+user.getId());
			System.out.println("name: "+user.getName());
			System.out.println("position: "+user.getPosition());
			System.out.println("department: "+user.getDepartment());
			System.out.println("email: "+user.getEmail());
			List<ContactInfo> contactInfo = (List<ContactInfo>)user.getContacts();
			if(contactInfo != null) {
				for(ContactInfo contact : contactInfo) {
					System.out.println("type: "+contact.getType());
					System.out.println("contact: "+contact.getContact());
				}
			}
		}
	}
	//검색
	@Ignore
	@Test
	public void searchByUserName() {
		String keyword = "실험";
		List<UserInfo> userInfo = new ArrayList<UserInfo>();
		//userInfo = userService.findAll();	//전체검색
		userInfo = userService.findUserInfoByNameLike(keyword); //이름으로 검색
		//userInfo = userService.findUserInfoByPositionLike(keyword); //직급으로 검색
		//userInfo = userService.findUserInfoByDepartmentLike(keyword); //부서로 검색
		for (UserInfo user : userInfo) {
			System.out.println("id: "+user.getId());
			System.out.println("name: "+user.getName());
			System.out.println("position: "+user.getPosition());
			System.out.println("department: "+user.getDepartment());
			System.out.println("email: "+user.getEmail());
			List<ContactInfo> contactInfo = (List<ContactInfo>)user.getContacts();
			if(contactInfo != null) { //검색한 사람에게 연락처가 있다면 출력
				for(ContactInfo contact : contactInfo) {
					System.out.println("type: "+contact.getType());
					System.out.println("contact: "+contact.getContact());
				}
			}
		}
	}
	//번호로 검색
	@Ignore
	@Test
	public void searchByContact() {
		String number = "010";
		List<ContactInfo> contactInfo = contactService.findContactInfoByContactLike(number);
		for(ContactInfo contact : contactInfo) {
			System.out.println("id: "+contact.getUserId().getId());
			System.out.println("name: "+contact.getUserId().getName());
			System.out.println("position: "+contact.getUserId().getPosition());
			System.out.println("department: "+contact.getUserId().getDepartment());
			System.out.println("email: "+contact.getUserId().getEmail());
			System.out.println("type: "+contact.getType());
			System.out.println("number: "+contact.getContact());
		}
	}	
	
//	@Ignore
	@Test
	public void test0() {
		Long userId = 1L;
		ContactInfo contactInfo = new ContactInfo();
		UserInfo tmpUser = new UserInfo(); //빈 깡통
		tmpUser.setId(userId);
		contactInfo.setUserId(tmpUser);
		
		contactInfo.setUserId(tmpUser);
		
		UserInfo userInfo = userService.findUserInfoById(userId);
		userInfo.getContacts().add(contactInfo);
		
		System.out.println(userInfo.getContacts().add(contactInfo));
		
		System.out.println("tmpUser.getId(): "+tmpUser.getId());
		System.out.println("tmpUser.getName(): "+tmpUser.getName());
	}
}
