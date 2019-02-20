package jiagou.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

public class ProxyChain {
	private final Class<?> targetClass;
	private final Object targetObject;
	private final Method targetMethod;
	private final MethodProxy methodProxy;
	private final Object[] methodParams;

	private List<Proxy> proxies = new ArrayList<>();
	private int proxyIndex = 0;

	public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxies) {
		this.targetClass = targetClass;
		this.targetObject = targetObject;
		this.targetMethod = targetMethod;
		this.methodProxy = methodProxy;
		this.methodParams = methodParams;
		this.proxies = proxies;
	}

	public Object doProxyChain() throws Throwable {
		Object result = null;
		if (proxyIndex < proxies.size()) {
			result = proxies.get(proxyIndex++).doProxy(this);
		} else {
			result = methodProxy.invokeSuper(targetObject, methodParams);
		}
		return result;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public Object getTargetObject() {
		return targetObject;
	}

	public Method getTargetMethod() {
		return targetMethod;
	}

	public Object[] getMethodParams() {
		return methodParams;
	}

}
