package kr.co.uclick.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class MyWebApplicationInitializer implements WebApplicationInitializer {
//서블릿은 3.0 이후부터 web.xml 없이도 서블릿 컨텍스트 초기화 작업이 가능해졌다.
//프레임워크 레벨에서 직접 초기화할 수 있게 도와주는 ServletContainerInitializer API를 제공하기 때문이다.
//그래서 web.xml을 대체한다.
//이 인터페이스를 구현한 클래스를 만들어두면 웹 어플리케이션이 시작할 때 자동으로 onStartup() 메서드가 실행된다.
//여기서 초기화 작업을 수행하면 된다.	
	@Override
	public void onStartup(ServletContext servletCxt) {
		//dispatcher 서블릿은 모든 서블릿을 분석해서 dispatch할 준비를 한다.

		// Create the 'root' Spring application context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(SpringConfiguration.class);

		// Manage the lifecycle of the root application context(servlet-context 해당하는 파일)
		servletCxt.addListener(new ContextLoaderListener(rootContext));

		// Create the dispatcher servlet's Spring application context(dispatcher-context 해당하는 파일)
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(SpringWebConfiguration.class);

		// Register and map the dispatcher servlet
		//서블릿 웹 애플리케이션 컨텍스트는 서블릿 안에서 초기화되고 서블릿이 종료될 떄 같이 종료된다.
		//이때 사용되는 서블릿이 DispatcherServlet이다.
		//이것을 자바코드로 바꾼것(root-context,java-context를 자바 코드로 분리)
		ServletRegistration.Dynamic dispatcher = servletCxt.addServlet("politech",
				new DispatcherServlet(dispatcherContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");

		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");//utf-8 필터
		characterEncodingFilter.setForceEncoding(true);
		servletCxt.addFilter("characterEncodingFilter", characterEncodingFilter).addMappingForUrlPatterns(null, false,
				"/*");
	}

}
