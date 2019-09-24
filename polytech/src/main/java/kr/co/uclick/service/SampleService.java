package kr.co.uclick.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.entity.QSample;
import kr.co.uclick.entity.Sample;
import kr.co.uclick.repository.SampleRepository;

@Service//서비스임을 선언
@Transactional//클래스에 트랜잭션 기능이 적용된 프록시 객체가 생성된다
public class SampleService {

	@Autowired//SampleService에 SampleRepository를 주입하기 위하여 사용
	private SampleRepository sampleRepository; //SampleRepository를 전달 받음

	public void delete(Sample entity) {
		sampleRepository.delete(entity);// delete() : 레코드 삭제
	}

	@Transactional(readOnly = true)//트랜잭션 속성
	public List<Sample> findAll() {//jpaRepository를 상속한 인터페이스를 통해 도메인 Entity 하나에 대해서 가능한 기능 중 하나이다.
		return sampleRepository.findAll();// findOne() : primary key로 레코드 한건 찾기 /
										  //findAll() : 전체 레코드 불러오기.정렬(sort),페이징(pageable) 가능 / count() : 레코드 갯수
	}

	@Transactional(readOnly = true)//트랜잭션 속성
	public List<Sample> findSampleByName(String name) {

		sampleRepository.findAll(QSample.sample.name.eq(name));//(1)
		sampleRepository.doSample(name);//sampleRepository의 doSample(name)

		return sampleRepository.findSampleByName(name);//(2), (1)과 (2)는 동일
	}  

	public void save(Sample sample) {
		sampleRepository.save(sample);//save() : 레코드 저장(insert,update)
	}

	public void findById(Long sampleId) {
		sampleRepository.findById(sampleId);//findBy~:쿼리를 요청하는 메서드임을 알린다.
	}
}
