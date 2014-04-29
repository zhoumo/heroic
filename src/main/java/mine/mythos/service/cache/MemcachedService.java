package mine.mythos.service.cache;

import mine.mythos.annotation.Cache;
import mine.mythos.base.BaseCache;
import mine.mythos.base.BaseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whalin.MemCached.MemCachedClient;

@Component
public class MemcachedService extends BaseCache {

	@Autowired
	private MemCachedClient memCachedClient;

	@Override
	public Object get(String key) {
		return memCachedClient.get(key);
	}

	@Override
	public void add(String key, Object obj) {
		memCachedClient.set(key, obj);
	}

	@Override
	public void delete(String key) {
		memCachedClient.delete(key);
	}

	@Override
	public boolean available(Class<? extends BaseEntity> clazz) {
		if (clazz.isAnnotationPresent(Cache.class)) {
			Cache cache = clazz.getAnnotation(Cache.class);
			return cache.useMemcached();
		}
		return false;
	}
}
