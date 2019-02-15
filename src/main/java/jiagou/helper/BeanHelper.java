package jiagou.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jiagou.util.ReflectionUtil;

public class BeanHelper {
	private static final Map<Class<?>, Object> MAP = new HashMap<>();
	static {
		Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
		for (Class<?> class1 : classSet) {
			MAP.put(class1, ReflectionUtil.newInstance(class1));
		}
	}

	public static Map<Class<?>, Object> getBeanMap() {
		return MAP;
	}

	public static <T> T getBean(Class<T> cls) {
		return (T) MAP.get(cls);
	}

	public static void setBean(Class<?> cls, Object obj) {
		MAP.put(cls, obj);
	}
}
