package kr.co.uclick.entity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cacheable
@Cache(region="ContactInfo", usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity 
public class ContactInfo { //연락처 정보 class
	
	@Id 
	@TableGenerator(name = "contactInfo",allocationSize=1) //연락할 수 있는 정보
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "contactInfo")

	@Column(name="id", unique=true, nullable=false)
	private Long id; //id

	@Column(name="type", length=20)
	private String type; //연락처 타입
	
	@Column(name="contact", length=20)
	private String contact;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="userId", nullable=false)
	private UserInfo userId; //해당 연락처와 일치하는 사원의 ID
////////////////////////////////////////////////////////////////////////////

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public UserInfo getUserId() {
		return userId;
	}

	public void setUserId(UserInfo userId) {
		this.userId = userId;
	}

}
