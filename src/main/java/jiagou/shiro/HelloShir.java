package jiagou.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloShir {
	private static final Logger log = LoggerFactory.getLogger(HelloShir.class);

	public static void main(String[] args) {
		// init
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.properties");
		SecurityUtils.setSecurityManager(factory.getInstance());

		// 1 用户
		Subject subject = SecurityUtils.getSubject();
		// 2.令牌
		UsernamePasswordToken token = new UsernamePasswordToken("shiro", "1234");

		try {
			subject.login(token);
		} catch (Exception e) {
			log.error("log failure" + e);
			return;
		}
		log.info("log success " + subject.getPrincipal());
		subject.logout();

	}
}
