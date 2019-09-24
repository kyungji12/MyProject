package kr.co.uclick.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.uclick.entity.Sample;
import kr.co.uclick.entity.UserInfo;
import kr.co.uclick.service.SampleService;
import kr.co.uclick.service.UserService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class SpringConfigurationTest {

	@Autowired
	UserService userService;

	@Test
	public void test() {
		UserInfo sample = new UserInfo();
		sample.setName("park");
//		sample.setNumber(10);
		userService.save(sample);
	}

}
