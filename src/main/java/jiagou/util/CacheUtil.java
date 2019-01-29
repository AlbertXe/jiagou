package jiagou.util;

import org.apache.commons.lang3.StringUtils;

public class CacheUtil {

	public static String castString(Object object) {
		return object != null ? String.valueOf(object) : "";
	}

	public static int castInt(Object object) {
		int result = 0;
		String strValue = castString(object);
		if (StringUtils.isNotEmpty(strValue))
			result = Integer.parseInt(strValue);
		return result;
	}

	public static boolean castBoolean(Object object) {
		boolean result = false;
		String strValue = castString(object);
		if (StringUtils.isNotEmpty(strValue))
			result = Boolean.parseBoolean(strValue);
		return result;
	}

}
