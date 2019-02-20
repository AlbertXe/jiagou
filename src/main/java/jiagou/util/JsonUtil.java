package jiagou.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public static <T> String toJson(T obj) {
		try {
			return MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T toObj(String json, Class<T> clz) {
		try {
			return MAPPER.readValue(json, clz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
