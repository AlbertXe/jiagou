package jiagou.proxy;

import java.lang.reflect.Method;

import jiagou.service.Hello;
import jiagou.service.HelloImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CGLibProxy implements MethodInterceptor {
	private static CGLibProxy instance = new CGLibProxy();

	private CGLibProxy() {
	}

	public static CGLibProxy getInstance() {
		return instance;
	}

	public <T> T getProxy(Class<T> cls) {
		return (T) Enhancer.create(cls, this);
	}

	@Override
	public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy proxy) throws Throwable {
		before();
		Object object = proxy.invokeSuper(arg0, arg2);
		after();
		return object;
	}

	private void before() {
		System.out.println("BEFORE");
	}

	private void after() {
		System.out.println("AFTER");
	}

	public static void main(String[] args) {
		Hello helloImpl = CGLibProxy.getInstance().getProxy(HelloImpl.class);
		helloImpl.say("hong");
	}
}
