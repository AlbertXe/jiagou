package jiagou.model;

public class Request {
	private String requsetMehtod;
	private String requestPath;

	public Request(String requsetMehtod, String requestPath) {
		this.requsetMehtod = requsetMehtod;
		this.requestPath = requestPath;
	}

	public String getRequsetMehtod() {
		return requsetMehtod;
	}

	public void setRequsetMehtod(String requsetMehtod) {
		this.requsetMehtod = requsetMehtod;
	}

	public String getRequestPath() {
		return requestPath;
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}

}
