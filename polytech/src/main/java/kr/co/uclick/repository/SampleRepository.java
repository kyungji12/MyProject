package kr.co.uclick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import kr.co.uclick.entity.Sample;

public interface SampleRepository //jpa repository를 extends받아서 smapleReposityory가 
//interface만 있음.
		extends JpaRepository<Sample, Long>, QuerydslPredicateExecutor<Sample>, CustomSampleRepository {

	public List<Sample> findSampleByName(String name); //메소드 선언, 자동으로 select query를 작성해주고 이것을 list에 담아서 retrun
//	public List<Sample> findSampleByNumber(Integer number);
}
