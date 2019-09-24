package kr.co.uclick.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ignite.cache.hibernate.HibernateRegionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration 
//어노테이션기반 환경구성을 돕는다. Spring IOC Container에게 해당 클래스를 Bean구성 Class임을 알려주는 것.
//이 Configuration안에 들어있는 bean들은 따로 메모리를 잡아먹지 않고 바로 호출?할 수 있다.
@ImportResource(locations = "classpath:applicationContext-ignite.xml")
//@Import어노테이션은 기본적으로 Java파일에 대한 Import를 위해 사용한다. 
//모든 설정을 JavaConfig로 변경하면 좋겠지만, 시간 관계상이나 복잡한 것들은 나중에 하려고 xml파일 형태로 남겨둔 것들이 있다.
//그런 Configuration들을 Import하기위해 사용.
@ComponentScan({ "kr.co.uclick.service" })
//@componet어노테이션 및 @Service, @Repository, @Controller어노테이션이 부여된Class들을 자동으로 Scan하여 Bean으로 등록하는 역할
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
//DataSourceTransactionManager Bean을 찾아 Transaction Manager로 사용
@EnableSpringConfigured		//설정
@EnableJpaRepositories(basePackages = "kr.co.uclick.repository") //spring jpa repositories부터 로딩
public class SpringConfiguration {

	@Bean //어플리케이션의 핵심을 이루는 객체, 우리가 컨테이너에 공급하는 설정 메타 데이터 (XML파일)에 의해 생성
	@Primary //같은 우선순위로 있는 클래스가 여러개 있을시 그 중 가장먼저 우선순위로 주입할 클래스 타입을 선택
	public DataSource dataSource() { //dataSource설정
		//DataSource: 기존 JDBC프로그램 구현으로 DBMS와 연동작업을 할 때는 웹 클라이언트로부터 요청이 있을때마다
		//DB서버에 연결하기위해 Connection객체를 얻어내야 했다. 하지만 불필요한 연결에 의한 서버 자원의 낭비를 발생시키기 때문에 
		//Connection Pool개념의 DataSource를 사용
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/polytech?serverTimezone=UTC");
		//"jdbc:mysql://localhost/kopo14","root","ykj0112"
		dataSource.setUsername("root"); //데이터베이스 아이디
		dataSource.setPassword("ykj0112"); //데이터베이스 비밀번호
		return dataSource;
	}

	@Bean
	@DependsOn("igniteSystem")//(applicationContext-ignite.xml안에 있음)우선순위
	@Primary
	//FactoryBean:어떤 종류의 객체라도 ApplicationContext에서 사용할 수 있도록 해준다는 의미에서 간접적인 계층을 추가하는 특별한 bean이다.
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		//LocalContainerEntityManagerFactoryBean:SessionFactoryBean과 동일하게 DataSource와 Hibernate Property, Entity가 위치한 package를 지정
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan("kr.co.uclick.entity");
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		//JpaVendorAdapter: JPA는 여러 구현체가 존재하기 때문에 구현체별 설정을 지원하기 위한 클래스
		// hibernate를 사용하기 때문에 HibernateJpaVendorAdapter를 사용
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		return em;
	}

	@Bean
	@Primary
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		//PlatformTransactionManager : 스프링 트랜잭션 추상화의 핵심 인터페이스
		//Transaction을 사용하기 위한 PlatformTransactionManager interface의 구현체를 등록.
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		//JpaTransactionManager : JPA를 지원하는 TransactionManager를 등록
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		//PersistenceExceptionTranslationPostProcessor을 등록해주면
		//@Repository 클래스들에 대해 자동으로 예외를 Spring의 DataAccessException으로 일괄 변환해준다.
		return new PersistenceExceptionTranslationPostProcessor();
	}

	public Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty(AvailableSettings.HBM2DDL_AUTO, "update");
		//mapped entity class를 분석하여  schema를 자동으로 생성(development환경에서는 update, production 환경에서는 none 사용)
		properties.setProperty(AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString());
		//SQL 정렬해서 보기
		properties.setProperty(AvailableSettings.SHOW_SQL, Boolean.TRUE.toString());
		//SQL 보기
		properties.setProperty(AvailableSettings.DIALECT, MySQL5Dialect.class.getName());
		//dialect지정하면 Hibernate는 지정된 프로퍼티중에서 적절한 기본값을 사용 : 여기서는 MySQL5Dialect
		properties.setProperty(AvailableSettings.STATEMENT_BATCH_SIZE, "1000");
		//최대 JDBC 배치 크기
		properties.setProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE, Boolean.TRUE.toString());
		//L2 cache 연동 설정
		properties.setProperty(AvailableSettings.USE_QUERY_CACHE, Boolean.TRUE.toString());
		//Query cache 연동설정
		properties.setProperty(AvailableSettings.GENERATE_STATISTICS, Boolean.FALSE.toString());
		//Hibernate는 자신의 정보를 MBean으로 내보내주는 기능을 가지고있으며, SessionFactory.getStatistics()를 통해
		//통계정보를 볼 수도 있다. 이를 위해 hibernate.generate_statistics=true로 설정
		properties.setProperty(AvailableSettings.CACHE_REGION_FACTORY, HibernateRegionFactory.class.getName());
		//RegionFactory: Contract for building second level cache regions. 구현 클래스
		properties.setProperty("org.apache.ignite.hibernate.ignite_instance_name", "cafe-grid");
		//ignite_instance_name 이름 설정
		properties.setProperty("org.apache.ignite.hibernate.default_access_type", "NONSTRICT_READ_WRITE");
		//default 접근방법 : 같은 패키지 내에서만 접근 가능.(접근범위 : private < default < protected < public 순으로 보다 많은 접근을 허용한다)
		//객체의 동시 수정 등에 대한 고려를 하지 않고 캐싱한다 (엄격하지 않은 읽기/쓰기)
		properties.setProperty(AvailableSettings.PHYSICAL_NAMING_STRATEGY,
				CustomPhysicalNamingStrategyStandardImpl.class.getName());
		return properties;
	}

}
