package jiagou.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jiagou.model.Customer;
import jiagou.service.CustomerService;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
	private CustomerService customerService;

	@Override
	public void init() throws ServletException {
		customerService = new CustomerService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Customer> customers = customerService.getCustomerList();
		req.setAttribute("customers", customers);
		req.getRequestDispatcher("/WEB-INF/view/customer.jsp").forward(req, resp);
	}
}
