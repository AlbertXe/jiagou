package jiagou.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/demo";
	private static final String username = "root";
	private static final String password = "xhw";
	private static ThreadLocal<Connection> connLocal = new ThreadLocal<>();

	public static Connection getConnection() {
		Connection connection = connLocal.get();
		try {
			if (connection == null) {
				Class.forName(driver);
				connection = DriverManager.getConnection(url, username, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			connLocal.set(connection);
		}
		return connection;
	}

	public static void close() {
		Connection connection = connLocal.get();
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connLocal.remove();
		}
	}
}
