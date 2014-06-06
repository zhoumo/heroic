package mine.heroic.common;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mine.heroic.common.BaseClass;
import mine.heroic.common.BaseDao;
import mine.heroic.common.BaseEntity;
import mine.heroic.common.annotation.Component;
import mine.heroic.common.annotation.Title;
import mine.heroic.common.annotation.Validate;
import mine.heroic.common.cache.BaseCache;
import mine.heroic.util.BeanUtil;
import mine.heroic.util.StringUtil;
import mine.heroic.vo.Custom;
import mine.heroic.vo.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class BaseService<T extends BaseEntity> extends BaseClass {

	@Autowired
	private BaseDao<T> baseDao;

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
		if (cacheList != null) {
			for (BaseCache baseCache : cacheList) {
				String key = baseCache.getKey(entity);
				if (baseCache.available(entity.getClass())) {
					baseCache.add(key, entity);
				}
			}
		}
		baseDao.saveOrUpdate(entity);
	}

	public void delete(T entity) {
		if (cacheList != null) {
			for (BaseCache baseCache : cacheList) {
				String key = baseCache.getKey(entity);
				if (baseCache.available(entity.getClass())) {
					baseCache.delete(key);
				}
			}
		}
		baseDao.delete(entity);
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
				Method method = obj.getClass().getMethod("get" + StringUtil.upperFirstLetter(field));
				oldValue = method.invoke(obj, new Object[0]).toString();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
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
	public <E> Set<E> createCollectionsByIds(E entity, String ids) {
		Set<E> set = new HashSet<E>();
		for (String id : ids.split(",")) {
			if (StringUtils.isEmpty(id)) {
				continue;
			}
			set.add((E) get(Long.parseLong(id), entity.getClass()));
		}
		return set;
	}

	public String resolveCustom(Class<T> clazz) throws IOException {
		Custom custom = new Custom();
		buildDataTable(custom, clazz);
		buildDialog(custom, clazz);
		buildValidate(custom, clazz);
		return BeanUtil.convertBeanToJson(custom);
	}

	private void buildDataTable(Custom custom, Class<T> clazz) {
		Custom.DataTable dataTable = custom.instanceDataTable();
		List<String> keyList = new ArrayList<String>();
		List<String> titleList = new ArrayList<String>();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(Title.class)) {
				Title titleAnnotation = field.getAnnotation(Title.class);
				if (titleAnnotation.show()) {
					keyList.add(field.getName());
					titleList.add(titleAnnotation.value());
				}
			}
		}
		dataTable.setKeys(keyList);
		dataTable.setTitles(titleList);
		custom.setDataTable(dataTable);
	}

	private void buildDialog(Custom custom, Class<T> clazz) {
		Custom.Dialog dialog = custom.instanceDialog();
		dialog.setKey(clazz.getSimpleName());
		List<Custom.Layout> layoutList = new ArrayList<Custom.Layout>();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(Component.class)) {
				Component componentAnnotation = field.getAnnotation(Component.class);
				Custom.Layout layout = custom.instanceLayout();
				layout.setKey(field.getName());
				layout.setTitle(field.isAnnotationPresent(Title.class) ? field.getAnnotation(Title.class).value() : field.getName());
				layout.setType(componentAnnotation.type());
				layout.setText(componentAnnotation.text());
				layout.setValue(componentAnnotation.value());
				layout.setUrl(componentAnnotation.url());
				layoutList.add(layout);
			}
		}
		dialog.setLayout(layoutList);
		custom.setDialog(dialog);
	}

	private void buildValidate(Custom custom, Class<T> clazz) {
		Set<Custom.Validate> validateSet = new HashSet<Custom.Validate>();
		for (Field field : clazz.getDeclaredFields()) {
			Custom.Validate validate = custom.instanceValidate();
			validate.setKey(field.getName());
			Map<String, Object> constraintMap = new HashMap<String, Object>();
			if (field.isAnnotationPresent(Validate.class)) {
				Validate validateAnnotation = field.getAnnotation(Validate.class);
				constraintMap.put("required", validateAnnotation.required());
				constraintMap.put("unique", validateAnnotation.unique());
				constraintMap.put("maxlength", validateAnnotation.maxLength());
				constraintMap.put("minlength", validateAnnotation.minLength());
			}
			validate.setConstraints(constraintMap);
			validateSet.add(validate);
		}
		custom.setValidates(validateSet);
	}
}
