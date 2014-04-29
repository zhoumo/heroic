package mine.mythos.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import mine.mythos.util.BeanUtil;

public abstract class BaseController extends BaseClass {

	protected Map<String, Object> model = new HashMap<String, Object>();

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	protected abstract void configure();

	protected void jsonpCallback(HttpServletResponse response, String callback, Object value) throws IOException {
		StringBuilder builder = new StringBuilder(callback);
		builder.append("(");
		builder.append(BeanUtil.convertBeanToJson(value));
		builder.append(")");
		response.getWriter().write(builder.toString());
	}
}
