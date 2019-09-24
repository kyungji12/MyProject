package kr.co.uclick.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import kr.co.uclick.entity.ContactInfo;
import kr.co.uclick.entity.UserInfo;

@Repository
public interface UserRepository
		extends JpaRepository<UserInfo, Long>,
				QuerydslPredicateExecutor<UserInfo>{	
	//Query Cache
	@QueryHints(value= {
			@QueryHint(name="org.hibernate.cacheable", value="true"),
			@QueryHint(name="org.hibernate.cacheMode", value="NORMAL")
	})
	public Page<UserInfo> findAll(Pageable pageable);	
	//이름으로 검색
	public List<UserInfo> findUserInfoByNameLike(String name);
	//아이디로 검색	
	public UserInfo findUserInfoById(Long id);	
	//부서로 검색
	public List<UserInfo> findUserInfoByDepartmentLike(String department);
	//직급으로 검색
	public List<UserInfo> findUserInfoByPositionLike(String position);
}
