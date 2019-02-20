package jiagou.helper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jiagou.annotation.Aspect;
import jiagou.annotation.Service;
import jiagou.proxy.AspectProxy;
import jiagou.proxy.Proxy;
import jiagou.proxy.ProxyManager;
import jiagou.proxy.TransactionProxy;

public class AOPHelper {
	private final Logger log = LoggerFactory.getLogger(AOPHelper.class);

	static {
		try {
			Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
			Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
			for (Class<?> key : targetMap.keySet()) {
				// key : 目标类 value ：代理list
				Object proxy = ProxyManager.createProxy(key, targetMap.get(key));
				BeanHelper.setBean(key, proxy);
			}

		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获得该注解的所有类
	 * 
	 * @param aspect
	 * @return
	 */
	public static Set<Class<?>> createTargetClassSet(Aspect aspect) {
		Set<Class<?>> rtn = new HashSet<>();
		Class<? extends Annotation> annotation = aspect.value();

		if (annotation != null && !annotation.equals(Aspect.class)) {
			rtn.addAll(ClassHelper.getClassSetByAnnotation(annotation));
		}
		return rtn;
	}

	/**
	 * 代理类和 目标类映射关系，一个代理可以对多个目标
	 */
	public static Map<Class<?>, Set<Class<?>>> createProxyMap() {
		Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
		addAspectProxy(proxyMap);// 普通切面代理
		addTransactionProxy(proxyMap);// 事物切面代理
		return proxyMap;
	}

	private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
		Set<Class<?>> set = ClassHelper.getClassSetBySuper(AspectProxy.class);
		for (Class<?> class1 : set) {
			if (class1.isAnnotationPresent(Aspect.class)) {// Aspect 注解
				Aspect aspect = class1.getAnnotation(Aspect.class);
				Set<Class<?>> targetClassSet = createTargetClassSet(aspect);// 获得该注解的所有类
				proxyMap.put(class1, targetClassSet);
			}
		}
	}

	private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
		Set<Class<?>> targetClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
		proxyMap.put(TransactionProxy.class, targetClassSet);
	}

	/**
	 * 目标类与代理对象之间的映射关系
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * 
	 */
	private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws InstantiationException, IllegalAccessException {
		Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
		for (Class<?> proxyClass : proxyMap.keySet()) {
			// key:class1 value:set class
			for (Class<?> targetClass : proxyMap.get(proxyClass)) {
				Proxy proxy = (Proxy) proxyClass.newInstance();
				if (targetMap.containsKey(proxyClass)) {
					targetMap.get(targetClass).add(proxy);
				} else {
					List<Proxy> list = new ArrayList<>();
					list.add(proxy);
					targetMap.put(targetClass, list);
				}
			}
		}
		return targetMap;
	}
}
