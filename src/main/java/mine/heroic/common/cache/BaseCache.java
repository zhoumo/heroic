package mine.heroic.common.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mine.heroic.common.BaseEntity;

public abstract class BaseCache {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	public abstract Object get(String key);

	public abstract void add(String key, Object obj);

	public abstract void delete(String key);

	public abstract boolean available(Class<? extends BaseEntity> clazz);

	public void update(String key, Object obj) {
		this.delete(key);
		this.add(key, obj);
	}

	public String getKey(Object obj) {
		StringBuilder builder = new StringBuilder(obj.getClass().getName());
		if (obj instanceof BaseEntity) {
			BaseEntity entity = (BaseEntity) obj;
			builder.append("@");
			builder.append(entity.getId());
		}
		logger.debug("cache key: " + builder.toString());
		return builder.toString();
	}
}
