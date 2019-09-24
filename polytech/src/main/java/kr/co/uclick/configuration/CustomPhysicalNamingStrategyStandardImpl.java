package kr.co.uclick.configuration;

import java.util.Locale;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
//PhysicalNamingStrategyStandardImpl = 변수 이름을 그대로 사용
//테이블 이름을 올바르게 사용하기위한 대,소문자,언더바까지 생각해주는 설정(카멜 케이스를 언더스코어 버전으로 변경)
//(단어와 단어 구분시 자바 : 카멜 표기법 / DB : 언더스코어(_) 표기법)
public class CustomPhysicalNamingStrategyStandardImpl extends PhysicalNamingStrategyStandardImpl {
	private static final long serialVersionUID = -6972754781554141247L;

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		//테이블 이름
		return new Identifier(addUnderscores(name.getText()), name.isQuoted());
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
		//컬럼명
		return new Identifier(addUnderscores(name.getText()), name.isQuoted());
	}

	protected static String addUnderscores(String name) {
		//언더 스코어 표기법 : 클래스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭한다.
		//Ex) SalesManage스.java -> sales_manager table
		final StringBuilder buf = new StringBuilder(name.replace('.', '_'));
		for (int i = 1; i < buf.length() - 1; i++) {
			if (Character.isLowerCase(buf.charAt(i - 1)) && Character.isUpperCase(buf.charAt(i))
					&& Character.isLowerCase(buf.charAt(i + 1))) {
				buf.insert(i++, '_');
			}
		}
		return buf.toString().toLowerCase(Locale.ROOT);
	}
}
