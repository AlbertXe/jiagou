package jiagou.model;

import java.util.HashMap;
import java.util.Map;

public class View {
	private String path;
	private Map<String, Object> map = new HashMap<>();

	public View(String path) {
		this.path = path;
	}

	public View addModel(String key, Object value) {
		map.put(key, value);
		return this;
	}

	public String getPath() {
		return path;
	}

	public Map<String, Object> getMap() {
		return map;
	}

}
