package mine.heroic.common.cache;

import mine.heroic.common.BaseEntity;
import mine.heroic.common.annotation.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisService extends BaseCache {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void add(String key, Object obj) {
		redisTemplate.opsForValue().set(key, obj);
	}

	@Override
	public void delete(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public boolean available(Class<? extends BaseEntity> clazz) {
		if (clazz.isAnnotationPresent(Cache.class)) {
			Cache cache = clazz.getAnnotation(Cache.class);
			return cache.useRedis();
		}
		return false;
	}
}
