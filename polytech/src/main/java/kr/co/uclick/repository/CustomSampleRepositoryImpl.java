package kr.co.uclick.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomSampleRepositoryImpl implements CustomSampleRepository {

	private static final Logger logger = LoggerFactory.getLogger(CustomSampleRepositoryImpl.class);

	@Override
	public void doSample(String name) {
		logger.debug("doSample : {}, {}", name, 1); //디버그 logg찍기 
	}
	
	@Override
	public void doSample(int number) {
		logger.debug("doSample : {}, {}", number, 1); //디버그 logg찍기 
	}

}
