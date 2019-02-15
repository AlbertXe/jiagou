package jiagou.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import jiagou.annotation.Controller;
import jiagou.annotation.Service;
import jiagou.util.ClassUtil;

public final class ClassHelper {
	private static final Set<Class<?>> set;

	static {
		String basePackages = ConfigHelper.getBasePackages();
		set = ClassUtil.getClassSet(basePackages);
	}

	/**
	 * 获取包下所有类
	 * 
	 * @return
	 */
	public static Set<Class<?>> getClassSet() {
		return set;
	}

	public static Set<Class<?>> getServiceClassSet() {
		Set<Class<?>> rtn = new HashSet<>();
		for (Class<?> class1 : set) {
			if (class1.isAnnotationPresent(Service.class))
				rtn.add(class1);
		}
		return rtn;
	}

	public static Set<Class<?>> getControllerClassSet() {
		Set<Class<?>> rtn = new HashSet<>();
		for (Class<?> class1 : set) {
			if (class1.isAnnotationPresent(Controller.class))
				rtn.add(class1);
		}
		return rtn;
	}

	public static Set<Class<?>> getBeanClassSet() {
		Set<Class<?>> rtn = new HashSet<>();
		rtn.addAll(getServiceClassSet());
		rtn.addAll(getControllerClassSet());
		return rtn;
	}

	/**
	 * 获取包下父类的所有子类
	 */
	public static Set<Class<?>> getClassSetBySuper(Class<?> superclass) {
		Set<Class<?>> rtn = new HashSet<>();
		for (Class<?> class1 : set) {
			if (superclass.isAssignableFrom(class1) && !superclass.equals(class1)) {
				rtn.add(class1);
			}
		}
		return rtn;
	}

	/**
	 * 获得带有某注解的所有类
	 */
	public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
		Set<Class<?>> rtn = new HashSet<>();
		for (Class<?> class1 : set) {
			if (class1.isAnnotationPresent(annotationClass)) {
				rtn.add(class1);
			}
		}
		return rtn;
	}
}
