package jiagou.helper;

import jiagou.util.ClassUtil;

public class HelpLoader {
	public static void init() {
		Class<?>[] clss = { ClassHelper.class, BeanHelper.class, IocHelper.class, ControllerHelper.class };
		for (Class<?> class1 : clss) {
			ClassUtil.loadClass(class1.getName(), false);
		}
	}
}
