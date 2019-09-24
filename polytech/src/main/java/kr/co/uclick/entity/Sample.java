package kr.co.uclick.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity //실제 DB의 테이블과 매칭될 클래스임을 명시즉, 테이블과 링크될 클래스임을 나타낸다.
public class Sample { //기본 생성자

	@Id //해당 테이블의 pk필드를 나타낸다.
	@TableGenerator(name = "sample")
	//키 생성 전용 테이블을 하나 만들고, 여기에 이름과 값으로 사용할 컬럼을 만들어 데이타베이스의 시퀀스처럼 동작하게 하는 전략.
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sample")
	//@GeneratedValue는 주키의 값을 위한 자동 생성 전략을 명시하는데 사용
	private Long id;
	private String name;
	private int number;
	private Date regiDate;


	///////////////////////////////////////////////////////////////////////////////
	public Long getId()  {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
///////////////////////////////////////////////////////////////////////////////
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
///////////////////////////////////////////////////////////////////////////////
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
///////////////////////////////////////////////////////////////////////////////

	public Date getRegiDate() {
		return regiDate;
	}

	public void setRegiDate(Date regiDate) {
		this.regiDate = regiDate;
	}

	
	
}
