package mine.heroic.common.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import mine.heroic.common.BaseDao;
import mine.heroic.common.BaseEntity;
import mine.heroic.common.cache.BaseCache;
import mine.heroic.vo.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class BaseService<T extends BaseEntity> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BaseDao<T> baseDao;

	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	private List<BaseCache> cacheList;

	public List<BaseCache> getCacheList() {
		return cacheList;
	}

	public void setCacheList(List<BaseCache> cacheList) {
		this.cacheList = cacheList;
	}

	public Page<T> pagination(Page<T> page, Class<T> clazz) {
		StringBuilder builder = new StringBuilder("from ");
		builder.append(clazz.getName()).append(" order by id desc");
		return baseDao.findPage(page, builder.toString());
	}

	public void saveOrUpdate(T entity) {
		baseDao.saveOrUpdate(entity);
		if (cacheList != null) {
			for (BaseCache baseCache : cacheList) {
				String key = baseCache.getKey(entity);
				if (baseCache.available(entity.getClass())) {
					baseCache.add(key, entity);
				}
			}
		}
	}

	public void delete(T entity) {
		baseDao.delete(entity);
		if (cacheList != null) {
			for (BaseCache baseCache : cacheList) {
				String key = baseCache.getKey(entity);
				if (baseCache.available(entity.getClass())) {
					baseCache.delete(key);
				}
			}
		}
	}

	public BaseEntity get(Long id, Class<?> clazz) {
		StringBuilder builder = new StringBuilder("from ");
		builder.append(clazz.getName()).append(" where id = ?").append(" order by id desc");
		List<T> list = baseDao.find(builder.toString(), id);
		return list.size() == 0 ? null : list.get(0);
	}

	public List<T> findAll(Class<T> clazz) {
		return baseDao.findAll(clazz.getName());
	}

	public boolean checkUnique(String field, String value, Long id, Class<T> clazz) {
		String oldValue = value;
		if (id != null) {
			try {
				Object obj = get(id, clazz);
				Method method = obj.getClass().getMethod("get" + field.replaceFirst(field.substring(0, 1), field.substring(0, 1).toUpperCase()));
				oldValue = method.invoke(obj, new Object[0]).toString();
			} catch (Exception e) {
				logger.error("can not find old value.", e);
			}
		}
		StringBuilder builder = new StringBuilder("from ");
		builder.append(clazz.getName());
		builder.append(" where ").append(field).append(" = ?");
		List<T> list = baseDao.find(builder.toString(), value);
		return list.size() == 0 ? true : (value.equals(oldValue) ? (id == null ? false : true) : false);
	}

	public List<T> findBy(String propertyName, Object value, Class<?> clazz) {
		StringBuilder builder = new StringBuilder("from ");
		builder.append(clazz.getName()).append(" where ").append(propertyName).append(" = ?").append(" order by id desc");
		return baseDao.find(builder.toString(), value);
	}

	@SuppressWarnings("unchecked")
	public <E> List<E> createCollectionsByIds(E entity, String ids) {
		List<E> list = new ArrayList<E>();
		for (String id : ids.split(" ")) {
			if (StringUtils.isEmpty(id)) {
				continue;
			}
			list.add((E) get(Long.parseLong(id), entity.getClass()));
		}
		return list;
	}
}
