package jiagou.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 
 * @ClassName: ProxyManager
 * @Description:创建代理对象
 * @author: 谢洪伟
 * @date: 2019年2月12日 上午9:36:32
 */
public class ProxyManager {
	public static <T> T createProxy(Class<?> targetClass, final List<Proxy> proxies) {
		T result = (T) Enhancer.create(targetClass, new MethodInterceptor() {

			@Override
			public Object intercept(Object paramObject, Method paramMethod, Object[] paramArrayOfObject, MethodProxy paramMethodProxy) throws Throwable {
				return new ProxyChain(targetClass, paramObject, paramMethod, paramMethodProxy, paramArrayOfObject, proxies).doProxyChain();
			}
		});
		return result;
	}
}
