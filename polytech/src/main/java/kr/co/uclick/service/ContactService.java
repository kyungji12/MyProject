package kr.co.uclick.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import kr.co.uclick.entity.ContactInfo;
import kr.co.uclick.repository.ContactRepository;
import kr.co.uclick.repository.UserRepository;

@Service
@Transactional
public class ContactService {
	
	@Autowired
	ContactRepository contactRepository;
	
	@Autowired 
	UserRepository userRepository;

	//검색
	@Transactional
	public ContactInfo findContactInfoById(Long id) {
		ContactInfo contactInfo = contactRepository.findContactInfoById(id);
		Hibernate.initialize(contactInfo.getContact());
		return contactInfo;
	}
	
	public ContactInfo findContactInfoByContact(String contact) {
		return contactRepository.findContactInfoByContact(contact);
	}
	//중복검사용
	public String findContactInfoByTypeAndContact(String type, String contact) {
		String result = "";
		if(contactRepository.findContactInfoByTypeAndContact(type, contact) == null) {
			result = "true";
		}else {
			result = "false";
		}
		return result;
	}
	////개인 번호로 검색
	public List<ContactInfo> findContactInfoByContactLike(String contact){
		return contactRepository.findContactInfoByContactLike("%"+contact+"%");
	}
	//수정
	public void saveContact(ContactInfo entity) {
		contactRepository.save(entity);
	}
	//삭제
	public void deleteContact(long id) {
		contactRepository.findContactInfoById(id).getUserId().setContacts(null);
		contactRepository.deleteById(id);
	}

}
