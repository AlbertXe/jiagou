package jiagou.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropsUtil {
	private static final Logger log = LoggerFactory.getLogger(PropsUtil.class);
	
	/**
	 * 加载文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static Properties loadProps(String fileName) {
		InputStream is = null;
		Properties properties = null;

		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			if (is == null)
				throw new FileNotFoundException(fileName + "file not found");
			properties = new Properties();
			properties.load(is);
		} catch (IOException e) {
			log.error("load file props failure", e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.error("close input stream failure", e);
				}
			}
		}
		return properties;
	}

	public static String getString(Properties pros, String key) {
		return getString(pros, key, "");
	}

	/**
	 * 获取字符型属性 默认值
	 * 
	 * @param pros
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(Properties pros, String key, String defaultValue) {
		String value = defaultValue;
		if (pros.contains(key))
			value = pros.getProperty(key);
		return value;
	}

	public static int getInt(Properties pros, String key) {
		return getInt(pros, key, 0);
	}

	public static int getInt(Properties pros, String key, int defaultValue) {
		int value = defaultValue;
		if (pros.contains(key)) {
			value = CacheUtil.castInt(pros.get(key));
		}
		return value;
	}

	public static boolean getBoolean(Properties pros, String key) {
		return getBoolean(pros, key, false);
	}

	public static boolean getBoolean(Properties pros, String key, boolean defaultValue) {
		boolean result = defaultValue;
		if (pros.contains(key))
			result = CacheUtil.castBoolean(pros.getProperty(key));
		return result;
	}

}
