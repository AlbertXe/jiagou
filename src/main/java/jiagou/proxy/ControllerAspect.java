package jiagou.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerAspect extends AspectProxy {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private long begin;

	@Override
	public void before(Class<?> cls, Method method, Object[] params) {
		log.debug("class: %s", cls.getName());
		log.debug("method: %s", method.getName());
		begin = System.currentTimeMillis();
	}

	@Override
	public void after(Class<?> cls, Method method, Object[] params, Object result) {
		log.debug(String.format("time: %dms", System.currentTimeMillis() - begin));
	}

}
