package mine.heroic.util;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unchecked")
public class BeanUtil {

	private static ApplicationContext ctx = null;

	public static void initSpringApplicationContext() {
		ctx = ContextLoaderListener.getCurrentWebApplicationContext();
	}

	public static <T> T getSpringBean(String name) {
		if (ctx == null) {
			initSpringApplicationContext();
		}
		return (T) ctx.getBean(name);
	}

	public static void copyProperties(Object destination, Object origin) {
		try {
			BeanUtils.copyProperties(destination, origin);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static String convertBeanToJson(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		JsonGenerator generator = new JsonFactory().createGenerator(writer);
		mapper.writeValue(generator, object);
		generator.close();
		return writer.toString();
	}
}
