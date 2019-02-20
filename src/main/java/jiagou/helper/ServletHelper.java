package jiagou.helper;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletHelper {
	private static final Logger log = LoggerFactory.getLogger(ServletHelper.class);
	private static ThreadLocal<ServletHelper> servletHelper = new ThreadLocal<>();

	private HttpServletRequest request;
	private HttpServletResponse response;

	private ServletHelper(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public static void init(HttpServletRequest request, HttpServletResponse response) {
		servletHelper.set(new ServletHelper(request, response));
	}

	/**
	 * 销毁
	 */
	public static void destroy() {
		servletHelper.remove();
	}

	/**
	 * 获取request
	 */
	public static HttpServletRequest getRequest() {
		return servletHelper.get().request;
	}
	/**
	 * 获取response
	 */
	public static HttpServletResponse getResponse() {
		return servletHelper.get().response;
	}
	/**
	 * 获取session
	 */
	public static HttpSession getSession() {
		return getRequest().getSession();
	}
	
	/**
	 * 获取servletcontext
	 */
	public static ServletContext getServletContext() {
		return getRequest().getServletContext();
	}

	/**
	 * set requset attr
	 */
	public static void setRequsetAttr(String key, Object value) {
		getRequest().setAttribute(key, value);
	}

	/**
	 * get request attr
	 */
	public static Object getRequestAttr(String key) {
		return getRequest().getAttribute(key);
	}

	/**
	 * remove request attr
	 */
	public static void removeRequestAttr(String key) {
		getRequest().removeAttribute(key);
	}

	/**
	 * 重定向
	 */
	public static void sendDirect(String location) {
		try {
			getResponse().sendRedirect(getRequest().getContextPath() + location);
		} catch (IOException e) {
			log.error("sendRedirect failure" + location);
			e.printStackTrace();
		}
	}
}
