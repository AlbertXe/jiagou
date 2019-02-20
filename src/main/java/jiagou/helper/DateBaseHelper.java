package jiagou.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jiagou.service.CustomerService;
import jiagou.util.PropsUtil;

public class DateBaseHelper {
	private static final String DRIVER;
	private static final String URL;
	private static final String USERNAME;
	private static final String PASSWORD;
	private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
	static {
		Properties props = PropsUtil.loadProps("db.properties");
		DRIVER = props.getProperty("jdbc.driver");
		URL = props.getProperty("jdbc.url");
		USERNAME = props.getProperty("jdbc.username");
		PASSWORD = props.getProperty("jdbc.password");

		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			log.error("jdbc driver class not found!");
		}
	}

	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			log.error("connection error");
		}
		return connection;
	}

	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("connection close failure");
			}
		}
	}
}
