package kr.co.uclick.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration 
//어노테이션기반 환경구성을 돕는다. Spring IOC Container에게 해당 클래스를 Bean구성 Class임을 알려주는 것.
//이 Configuration안에 들어있는 bean들은 따로 메모리를 잡아먹지 않고 바로 호출?할 수 있다.
@EnableWebMvc
//자동으로 WebMvcConfigurationSupport 클래스가 Bean에 등록된다.
@ComponentScan("kr.co.uclick.controller")
//@componet어노테이션 및 @Service, @Repository, @Controller어노테이션이 부여된Class들을 자동으로 Scan하여 Bean으로 등록하는 역할
public class SpringWebConfiguration implements WebMvcConfigurer {

	@Override
	//슈퍼 클래스에 존재하는 필드나 메서드를 서브 클래스에서 재정의하여 사용할때 사용(오버라이딩을 통한 슈퍼클래스를 생성할 때에는 super 키워드를 사용)
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		//슈퍼 클래스에 존재하는 필드나 메서드를 서브 클래스에서 재정의하여 사용할때 사용(오버라이딩을 통한 슈퍼클래스를 생성할 때에는 super 키워드를 사용)	
		configurer.favorPathExtension(false);	//프로퍼티 값을 보고 URL의 확장자에서 리턴 포맷을 결정 여부
		configurer.favorParameter(true);		//URL 호출 시 특정 파라미터로 리턴포맷 전달 허용 여부 ex)a.do?format=json
		configurer.parameterName("mediaType");	//Parameter이름 설정
		configurer.ignoreAcceptHeader(true);	//HttpRequest Header의 Accept 무시 여부
		configurer.useJaf(false);
		configurer.mediaType("xml", MediaType.APPLICATION_XML);		//매핑할 ContentType을 작성
		configurer.mediaType("json", MediaType.APPLICATION_JSON);	//매핑할 ContentType을 작성
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//무언가를 진행할 때 특정 작업을 수행하는 Intercepter 설정
		registry.addInterceptor(new LocaleChangeInterceptor());
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
   //@RequestMapping에 등록되지 않은 요청 또는 JSP에 대한 요청을 처리하는 DefaultServletHandler 설정(이곳으로 요청전달됨)	
		configurer.enable();
	}

	@Bean
	public LocaleResolver LocaleResolver() {
	//LocaleResolver를 이용해서 웹 요청과 관련된 Locale을 추출하고, 이 Locale 객체를 이용해서 알맞은 언어의 메시지를 선택	
		return new CookieLocaleResolver(); //쿠키를 이용해서 Locale 정보를 저장
	}

	@Bean
	public MessageSource messageSource() {
		//MessageSource: 객체에서 로컬라이징된 메세지 얻기
		//메세지 설정 파일을 모아놓고 각 국가마다 로컬라이징을 함으로서 쉽게 각 지역에 맞춘 메세지를 제공할 수 있다.
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("validate-message.properties");
		return resourceBundleMessageSource;
	}

	@Bean
	public InternalResourceViewResolver InternalResourceViewResolver() {
		//InternalResourceViewResolver:뷰이름으로부터 JSP나 Tiles 연동을 위한 View 객체를 리턴
		////웹 어플리케이션의 WAR 파일 내에 포함된 뷰 템플릿을 찾는다
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
		//경로는 논리적 뷰 이름에 접두어와 접미어를 붙여서 구성
		internalResourceViewResolver.setSuffix(".jsp");
		////SpringWebConfiguration.java에 있는 internalResourceViewResolver.setPrefix("/WEB-INF/jsp/")에 의해서 생략될 수 있다.
		//여기서 리턴값에 sample을 입력하지 않아도 위 메서드 이름으로 찾아서 리턴한다.
		return internalResourceViewResolver;
		
	}
}
