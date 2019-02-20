package jiagou.proxy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyThreadLocal<T> {
	private Map<Thread, T> map = Collections.synchronizedMap(new HashMap<>());

	public T initialValue() {
		return null;
	}

	public void set(T value) {
		map.put(Thread.currentThread(), value);
	}

	public T get() {
		T value = map.get(Thread.currentThread());
		if (value == null && !map.containsKey(Thread.currentThread())) {
			value = initialValue();
			map.put(Thread.currentThread(), value);
		}
		return value;
	}

	public void remove() {
		map.remove(Thread.currentThread());
	}
}
