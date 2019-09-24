package kr.co.uclick.service;


import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.entity.UserInfo;
import kr.co.uclick.repository.UserRepository;

@Service
@Transactional	//하나라도 실패하면 롤백, 모두 성공하면 커밋
public class UserService {
	
	@Autowired 
	UserRepository userRepository;
	
	//Select One (id로 찾기)
	@Transactional
	public UserInfo findUserInfoById(Long id) {
		UserInfo userInfo = userRepository.findUserInfoById(id);
		Hibernate.initialize(userInfo.getContacts());
		return userInfo;
	}
	//Select All 
	@Transactional(readOnly = true)
	public List<UserInfo> findAll() {
		List<UserInfo> userInfo = userRepository.findAll();
		for (UserInfo tmp : userInfo) {
			Hibernate.initialize(tmp.getContacts());
		}
		return userRepository.findAll();
	}
	@Transactional(readOnly = true)	
	public Page<UserInfo> getList(Pageable pagee) {
		return userRepository.findAll(pagee);
	}
	
	//Select All(name으로 찾기)
	@Transactional(readOnly = true)
	public List<UserInfo> findUserInfoByNameLike(String name) {
		List<UserInfo> userInfo = userRepository.findUserInfoByNameLike(name);
		for (UserInfo tmp : userInfo) {
			Hibernate.initialize(tmp.getContacts());
		}
		return userRepository.findUserInfoByNameLike("%"+name+"%");
	}
	
	//부서로 찾기
	@Transactional(readOnly = true)
	public List<UserInfo> findUserInfoByDepartmentLike(String department){
		List<UserInfo> userInfo = userRepository.findUserInfoByDepartmentLike(department);
		for (UserInfo tmp : userInfo) {
			Hibernate.initialize(tmp.getContacts());
		}
		return userRepository.findUserInfoByDepartmentLike("%"+department+"%");
	}
	
	//직급으로 찾기
	@Transactional(readOnly = true)
	public List<UserInfo> findUserInfoByPositionLike(String position){
		List<UserInfo> userInfo = userRepository.findUserInfoByPositionLike(position);
		for (UserInfo tmp : userInfo) {
			Hibernate.initialize(tmp.getContacts());
		}
		return userRepository.findUserInfoByPositionLike("%"+position+"%");
	}
	
	//insert & update
	public void save(UserInfo entity) {
		userRepository.save(entity);
	}
	
	//delete
	public void delete(UserInfo entity) {
		userRepository.delete(entity);
	}

	//페이지네이션
	public long userCount() {
		return userRepository.count();
	}

	
	
}
