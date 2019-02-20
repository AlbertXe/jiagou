package jiagou.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import jiagou.service.Hello;
import jiagou.service.HelloImpl;

public class DynamicProxy implements InvocationHandler {
	private Object target;

	public DynamicProxy(Object target) {
		this.target = target;
	}

	public <T> T getProxy() {
		return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		before();
		Object object = method.invoke(target, args);
		after();
		return object;
	}

	private void before() {
		System.out.println("before");
	}

	private void after() {
		System.out.println("after");
	}

	public static void main(String[] args) {
		Hello hello = new HelloImpl();
		DynamicProxy dynamicProxy = new DynamicProxy(hello);
		Hello helloProxy = (Hello) Proxy.newProxyInstance(Hello.class.getClassLoader(), hello.getClass().getInterfaces(), dynamicProxy);
		helloProxy.say("xiehongwei");

		Hello helloproxy2 = dynamicProxy.getProxy();
		helloproxy2.say("xiexie");
	}

}
