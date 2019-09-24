package kr.co.uclick.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;

@Cacheable
@Cache(region="UserInfo", usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class UserInfo { //사원 정보 class
	
	@Id
	@TableGenerator(name = "user", allocationSize=1)//사원정보
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "user")
	
	@Column(name="id", unique=true, nullable=false)
	private Long id; //사원 id
	
	@Column(name="name", length=20, nullable=false)
	private String name; //사원 이름
	
	@Column(name="email", length=50)
	private String email; //이메일 
	
	@Column(name="position", length=30, nullable=false)
	private String position; //직급
	
	@Column(name="department",length=30, nullable=false)
	private String department; //사원 부서
	
	@Column(name="regiDate",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, insertable = true)
	@CreationTimestamp 
	private Date regiDate; //사원 등록일
	
	@Column(name="lastUpdate",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = true, insertable = true)
	private Date lastUpdate; //최근수정일
	
	@Column(name="special")
	private String special; //사원 특이사항
	
	@Cache(region="UserInfo.contacts", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="userId") //지연로딩 
	private List<ContactInfo> contacts; //연락처 테이블

////////////////////////////////////////////////////////////////////////////
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Date getRegiDate() {
		return regiDate;
	}

	public void setRegiDate(Date regiDate) {
		this.regiDate = regiDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public List<ContactInfo> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactInfo> contacts) {
		this.contacts = contacts;
	}
	



}
