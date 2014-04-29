package mine.mythos.base;

import java.lang.reflect.ParameterizedType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class BaseClass {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected String getResourcePath() {
		return getClass().getClassLoader().getResource("/").getPath();
	}

	protected Class getGenericType() {
		return (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
}
