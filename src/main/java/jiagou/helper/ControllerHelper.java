package jiagou.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jiagou.annotation.Action;
import jiagou.model.Handler;
import jiagou.model.Request;

public class ControllerHelper {
	private static final Map<Request, Handler> map = new HashMap<>();

	static {
		Set<Class<?>> classSet = ClassHelper.getControllerClassSet();
		for (Class<?> class1 : classSet) {
			Method[] methods = class1.getDeclaredMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(Action.class)) {
					Action action = method.getAnnotation(Action.class);
					String mapping = action.value();
					if (mapping.matches("\\w+:/\\w*")) {
						String[] req = mapping.split(":");
						String requestMethod = req[0];
						String requestPath = req[1];
						Request request = new Request(requestMethod, requestPath);
						Handler handler = new Handler(class1, method);
						map.put(request, handler);
					}

				}
			}
		}

	}

	public static Handler getHandler(String requestMethod, String requestPath) {
		Request request = new Request(requestMethod, requestPath);
		return map.get(request);
	}
}
