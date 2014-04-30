package mine.heroic.common.cache;

import mine.heroic.common.BaseClass;
import mine.heroic.common.BaseEntity;

public abstract class BaseCache extends BaseClass {

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
		return builder.toString();
	}
}
