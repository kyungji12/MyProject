package kr.co.uclick.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import kr.co.uclick.entity.ContactInfo;
@Repository
public interface ContactRepository 
	extends JpaRepository<ContactInfo, Long>,
	QuerydslPredicateExecutor<ContactInfo>{
	//query cache
	@QueryHints(value= {
			@QueryHint(name="org.hibernate.cacheable", value="true"),
			@QueryHint(name="org.hibernate.cacheMode", value="NORMAL")
	})	
	//아이디로 검색
	public ContactInfo findContactInfoById(Long id);
	// 번호로 검색
	public ContactInfo findContactInfoByContact(String contact);	
	public List<ContactInfo> findContactInfoByContactLike(String contact);	
	//중복검사용
	public ContactInfo findContactInfoByTypeAndContact(String type, String contact);
}
