package mine.heroic.common.cache;

import mine.heroic.common.BaseEntity;
import mine.heroic.common.annotation.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongodbService extends BaseCache {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Object get(String key) {
		String[] keys = key.split("@");
		try {
			return mongoTemplate.findById(Long.parseLong(keys[1]), Class.forName(keys[0]));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void add(String key, Object obj) {
		mongoTemplate.save(obj);
	}

	@Override
	public void delete(String key) {
		mongoTemplate.remove(get(key));
	}

	@Override
	public boolean available(Class<? extends BaseEntity> clazz) {
		if (clazz.isAnnotationPresent(Cache.class)) {
			Cache cache = clazz.getAnnotation(Cache.class);
			return cache.useMongodb();
		}
		return false;
	}
}
