package jiagou.model;

import java.util.Map;

public class Param {
	private Map<String, Object> map;

	public Param(Map<String, Object> map) {
		this.map = map;
	}

	public Long getLong(String name) {
		return Long.valueOf((String) map.get(name));

	}

	public Map<String, Object> getMap() {
		return map;
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

}
