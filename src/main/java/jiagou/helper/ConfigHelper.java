package jiagou.helper;

import java.util.Properties;

import jiagou.util.PropsUtil;

public class ConfigHelper {
	private static final String DRIVER = "jdbc.driver";
	private static final String URL = "jdbc.url";
	private static final String USERNAME = "jdbc.username";
	private static final String PASSWORD = "jdbc.password";
	private static final String BASEPACKAGES = "app.base_packages";
	private static final String JSPPATH = "app.jsp_path";
	private static final String ASSETPATH = "app.asset_path";

	private static final Properties pros = new PropsUtil().loadProps("db.properties");

	public static String getJdbcDriver(){
		return PropsUtil.getString(pros, DRIVER);
	}

	public static String getJdbcUrl() {
		return PropsUtil.getString(pros, URL);
	}

	public static String getJdbcUsername() {
		return PropsUtil.getString(pros, USERNAME);
	}

	public static String getJdbcPassword() {
		return PropsUtil.getString(pros, PASSWORD);
	}

	public static String getBasePackages() {
		return PropsUtil.getString(pros, BASEPACKAGES);
	}

	public static String getJspPath() {
		return PropsUtil.getString(pros, JSPPATH, "/WEB-INF/view/");
	}

	public static String getAssetPath() {
		return PropsUtil.getString(pros, ASSETPATH, "/asset/");
	}
}
