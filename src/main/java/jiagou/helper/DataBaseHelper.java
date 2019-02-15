package jiagou.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jiagou.service.CustomerService;
import jiagou.util.PropsUtil;

public class DataBaseHelper {
	private static final String DRIVER;
	private static final String URL;
	private static final String USERNAME;
	private static final String PASSWORD;
	private static final QueryRunner QUERY_RUNNER = new QueryRunner();
	private static final ThreadLocal<Connection> connHolder = new ThreadLocal<>();
	private static final BasicDataSource DATA_SOURCE = new BasicDataSource();

	private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
	static {
		Properties props = PropsUtil.loadProps("db.properties");
		DRIVER = props.getProperty("jdbc.driver");
		URL = props.getProperty("jdbc.url");
		USERNAME = props.getProperty("jdbc.username");
		PASSWORD = props.getProperty("jdbc.password");

		DATA_SOURCE.setDriverClassName(DRIVER);
		DATA_SOURCE.setUrl(URL);
		DATA_SOURCE.setUsername(USERNAME);
		DATA_SOURCE.setPassword(PASSWORD);

		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			log.error("jdbc driver class not found!");
		}
	}

	public static void executeSqlFile(String filePath) {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		try {
			String sql = null;
			while ((sql = reader.readLine()) != null) {
				executeUpdate(sql);
			}
		} catch (IOException e) {
			log.error("execute a sql file error");
		}

	}

	public static Connection getConnection() {
		Connection connection = connHolder.get();
		if (connection == null) {
			try {
				connection = DATA_SOURCE.getConnection();
			} catch (SQLException e) {
				log.error("connection error");
			} finally {
				connHolder.set(connection);
			}
		}
		return connection;
	}

	public static void closeConnection() {
		Connection conn = connHolder.get();
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("connection close failure");
			} finally {
				connHolder.remove();
			}
		}
	}

	public static <T> List<T> queryEntityList(Class<T> clz,String sql,Object...params){
		List<T> result = null;
		try {
			Connection connection = getConnection();
			result = QUERY_RUNNER.query(connection, sql, new BeanListHandler<>(clz), params);
		} catch (SQLException e) {
			log.error("QueryRUnner.query  failure");
		} finally {
			closeConnection();
		}
		return result;
	}

	public static <T> T queryEntity(Class<T> clz, String sql, Object... params) {
		T result = null;
		Connection connection = getConnection();
		try {
			result = QUERY_RUNNER.query(connection, sql, new BeanHandler<>(clz), params);
		} catch (SQLException e) {
			log.error("queryEntity error");
		} finally {
			closeConnection();
		}
		return result;
	}

	public static <T> boolean updateEntity(Class<T> clz, long id, Map<String, Object> map) {
		if (map == null || map.isEmpty())
			return false;

		String sql = "update" + clz.getSimpleName() + " set";
		StringBuilder columns = new StringBuilder();
		for (String key : map.keySet()) {
			columns.append(key).append("=?, ");
		}
		sql += columns.substring(0, columns.lastIndexOf(", ")) + " where id = ?";

		List<Object> params = new ArrayList<>();
		params.addAll(map.values());
		params.add(id);
		return executeUpdate(sql, params.toArray()) == 1;
	}

	public static <T> boolean insertEntity() {
		return false;
	}

	public static <T> boolean deleteEntity() {
		return false;
	}

	private static int executeUpdate(String sql, Object... array) {
		int rows = 0;
		Connection conn = DataBaseHelper.getConnection();
		try {
			rows = QUERY_RUNNER.update(conn, sql, array);
		} catch (SQLException e) {
			log.error("update error");
		}
		return rows;
	}

	/**
	 * 开启事物
	 */
	public static void beginTransaction() {
		Connection connection = getConnection();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connHolder.set(connection);
		}
	}

	/**
	 * 提交事物
	 */
	public static void commitTransaction() {
		Connection connection = getConnection();
		try {
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connHolder.remove();
		}
	}

	/**
	 * 回滚事物
	 */
	public static void rollbackTransaction() {
		Connection connection = getConnection();
		try {
			connection.rollback();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connHolder.remove();
		}
	}
}
