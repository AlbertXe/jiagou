package jiagou.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamUtil {

	public static String getString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = "";
		StringBuffer sb = new StringBuffer();
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
		}
		return sb.toString();
	}
}
