package jiagou.helper;

import java.lang.reflect.Field;
import java.util.Map;

import jiagou.annotation.Inject;
import jiagou.util.ReflectionUtil;

public class IocHelper {
	static {
		// bean map 映射关系
		Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
		for (Class<?> cls : beanMap.keySet()) {
			Object bean = beanMap.get(cls);

			Field[] fields = cls.getDeclaredFields();// 成员变量
			for (Field field : fields) {
				if (field.isAnnotationPresent(Inject.class)) {
					Class<?> fieldType = field.getType();
					Object fieldInstance = beanMap.get(fieldType);
					if (fieldInstance != null) { // 如果不空就反射赋值
						ReflectionUtil.setField(bean, field, fieldInstance);
					}

				}
			}
		}
	}
}
