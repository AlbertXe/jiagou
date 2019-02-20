package jiagou.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CodeUtil {

	public static String encode(String source) {
		String encode = null;
		try {
			encode = URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		return encode;
	}

	public static String decode(String source) {
		String decode = null;
		try {
			decode = URLDecoder.decode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decode;
	}
}
