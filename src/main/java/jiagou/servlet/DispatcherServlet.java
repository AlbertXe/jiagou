package jiagou.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jiagou.helper.BeanHelper;
import jiagou.helper.ConfigHelper;
import jiagou.helper.ControllerHelper;
import jiagou.helper.HelpLoader;
import jiagou.helper.ServletHelper;
import jiagou.model.Data;
import jiagou.model.Handler;
import jiagou.model.Param;
import jiagou.model.View;
import jiagou.util.CodeUtil;
import jiagou.util.JsonUtil;
import jiagou.util.ReflectionUtil;
import jiagou.util.StreamUtil;

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		// 1.初始化自己的 helper2.register jsp处理器3.静态资源处理器
		HelpLoader.init();
		ServletContext servletContext = config.getServletContext();
		ServletRegistration jsp = servletContext.getServletRegistration("jsp");
		jsp.addMapping(ConfigHelper.getJspPath() + "*");
		ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
		defaultServlet.addMapping(ConfigHelper.getAssetPath() + "*");
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletHelper.init(req, resp);// 封装到这个类，方便以后调用 线程安全的。

		try {
			// 1.请求方法及路径 参数
			String reqMethod = req.getMethod().toLowerCase();
			String pathInfo = req.getPathInfo();
			Handler handler = ControllerHelper.getHandler(reqMethod, pathInfo);
			// 2.获取处理器
			if (handler != null) {
				// 获得处理类 和参数
				Class<?> controllerClass = handler.getControllerClass();
				Object bean = BeanHelper.getBean(controllerClass);

				Map<String, Object> paramMap = new HashMap<>();
				Enumeration<String> parameterNames = req.getParameterNames();
				while (parameterNames.hasMoreElements()) {
					String nextElement = parameterNames.nextElement();
					paramMap.put(nextElement, req.getParameter(nextElement));
				}
				String body = CodeUtil.decode(JsonUtil.toJson(StreamUtil.getString(req.getInputStream())));
				String[] params = body.split("&");// a=3&c=3&b=5
				for (String param : params) {
					String[] keyvalue = param.split("=");// a 3
					paramMap.put(keyvalue[0], keyvalue[1]);
				}

				Param param = new Param(paramMap);
				Method method = handler.getActionMethod();
				Object result = null;
				if (param.isEmpty()) {
					result = ReflectionUtil.invokeMethod(bean, method);
				} else {
					result = ReflectionUtil.invokeMethod(bean, method, param);
				}

				// 3.方法返回值
				if (result instanceof View) {
					View view = (View) result;
					String path = view.getPath();
					if (path.startsWith("/")) {
						resp.sendRedirect(req.getContextPath() + path);
					} else {
						Map<String, Object> map = view.getMap();
						for (String key : map.keySet()) {
							req.setAttribute(key, map.get(key));
						}
						req.getRequestDispatcher(ConfigHelper.getJspPath() + path).forward(req, resp);
					}
				} else if (result instanceof Data) {
					Data data = (Data) result;
					Object obj = data.getData();
					resp.setContentType("application/json");
					resp.setCharacterEncoding("utf-8");
					PrintWriter writer = resp.getWriter();
					writer.write(JsonUtil.toJson(obj));
					writer.flush();
					writer.close();
				}
			}
		} catch (Exception e) {
		} finally {
			ServletHelper.destroy();
		}
	}
}
