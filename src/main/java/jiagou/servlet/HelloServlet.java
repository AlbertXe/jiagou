package jiagou.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:sss");
		String date = format.format(new Date());
		req.setAttribute("date", date);
		req.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(req, resp);
	}
}
