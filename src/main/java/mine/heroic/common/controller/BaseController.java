package mine.heroic.common.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mine.heroic.util.BeanUtil;

public abstract class BaseController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected Map<String, Object> model = new HashMap<String, Object>();

	protected abstract void configure();

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	public void jsonpCallback(HttpServletResponse response, String callback, Object value) throws IOException {
		StringBuilder builder = new StringBuilder(callback);
		builder.append("(");
		builder.append(BeanUtil.convertBeanToJson(value));
		builder.append(")");
		response.getWriter().write(builder.toString());
	}
}
