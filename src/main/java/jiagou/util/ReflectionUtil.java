package jiagou.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReflectionUtil {
	private static final Logger log = LoggerFactory.getLogger(ReflectionUtil.class);

	public static Object newInstance(Class cls) {
		Object newInstance = null;
		try {
			newInstance = cls.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			log.error("newInstance error", e);
		}
		return newInstance;
	}

	public static Object invokeMethod(Object obj, Method method, Object... params) {
		Object result = null;
		try {
			method.setAccessible(true);
			result = method.invoke(obj, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void setField(Object obj, Field field, Object value) {
		try {
			field.setAccessible(true);
			field.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
