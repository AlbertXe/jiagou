package jiagou.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: AspectProxy
 * @Description:切面代理
 * @author: 谢洪伟
 * @date: 2019年2月12日 上午9:48:16
 */
public abstract class AspectProxy implements Proxy {
	private final Logger log = LoggerFactory.getLogger(AspectProxy.class);

	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result = null;// 目标类 目标方法及参数
		Class<?> cls = proxyChain.getTargetClass();
		Method method = proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();

		try {
			if (Intercept(cls, method, params)) {
				before(cls, method, params);
				result = proxyChain.doProxyChain();
				after(cls, method, params, result);
			} else {
				result = proxyChain.doProxyChain();
			}
		} catch (Exception e) {
			log.error("proxy failure", e);
			error(cls, method, params, e);
			throw e;
		} finally {
			end();
		}
		return result;
	}

	public void end() {

	}

	public void error(Class<?> cls, Method method, Object[] params, Exception e) {

	}

	public void after(Class<?> cls, Method method, Object[] params, Object result) {

	}

	public void before(Class<?> cls, Method method, Object[] params) {

	}

	public boolean Intercept(Class<?> cls, Method method, Object[] params) {
		return true;
	}

}
