package jiagou.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jiagou.helper.DataBaseHelper;
import jiagou.model.Customer;

public class CustomerService {
	private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

	// public List<Customer> getCustomerList() {
	// Connection conn = null;
	// List<Customer> customers = null;
	// try {
	// customers = new ArrayList<>();
	// conn = DataBaseHelper.getConnection();
	// PreparedStatement prepareStatement = conn.prepareStatement("select * from
	// customer");
	// ResultSet resultSet = prepareStatement.executeQuery();
	// while (resultSet.next()) {
	// Customer customer = new Customer();
	// customer.setId(resultSet.getLong("id"));
	// customer.setName(resultSet.getString("name"));
	// customer.setContact(resultSet.getString("contact"));
	// customer.setTelephone(resultSet.getString("telephone"));
	// customer.setEmail(resultSet.getString("email"));
	// customer.setRemark(resultSet.getString("remark"));
	// customers.add(customer);
	// }
	//
	// } catch (SQLException e) {
	// log.error("execute sql error" + e);
	// } finally {
	// DataBaseHelper.closeConnection(conn);
	// }
	// return customers;
	// }

	public List<Customer> getCustomerList() {
		String sql = "select * from customer";
		return DataBaseHelper.queryEntityList(Customer.class, sql, null);

	}

	public Customer getCustomer(Long id) {
		String sql = "select * from customer where id = ?";
		return DataBaseHelper.queryEntity(Customer.class, sql, id);
	}

	public boolean createCustomer(Map<String, Object> fieldMap) {
		return false;
	}

	public boolean updateCustomer(Long id, Map<String, Object> fieldMap) {
		return false;
	}

	public boolean deleteCustomer(Long id) {
		return false;
	}
}
