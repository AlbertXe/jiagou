package test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jiagou.model.Customer;
import jiagou.service.CustomerService;

public class CustomerServiceTest {
	CustomerService customerService;

	public CustomerServiceTest() {
		this.customerService = new CustomerService();
	}

	@Before
	public void init() {

	}

	@Test
	public void test2() {
		System.out.println("123");
	}

	@Test
	public void getCustomerListTest() {
		List<Customer> customerList = customerService.getCustomerList();
		Assert.assertEquals(2, customerList.size());
	}

	@Test
	public void getCustomerTest() {
		long id = 1;
		Customer customer = customerService.getCustomer(id);
		Assert.assertNotNull(customer);
		System.out.println(customer);
	}
}
