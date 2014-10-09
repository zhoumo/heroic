package mine.heroic.common.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mine.heroic.common.BaseEntity;
import mine.heroic.common.annotation.Render;
import mine.heroic.common.annotation.Key;
import mine.heroic.common.annotation.Validate;
import mine.heroic.util.BeanUtil;
import mine.heroic.vo.Custom;

import org.springframework.stereotype.Service;

@Service
public class CustomService<T extends BaseEntity> {

	public String resolveCustom(Class<T> clazz) throws IOException {
		Custom custom = new Custom();
		custom.setKey(clazz.getAnnotation(Key.class).value());
		buildTable(custom, clazz);
		buildDialog(custom, clazz);
		buildValidate(custom, clazz);
		return BeanUtil.convertBeanToJson(custom);
	}

	private void buildTable(Custom custom, Class<T> clazz) {
		List<Custom.Column> table = new ArrayList<Custom.Column>();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(Render.class) && field.getAnnotation(Render.class).table()) {
				Custom.Column column = new Custom.Column();
				column.setTitle(field.getName());
				table.add(column);
			}
		}
		custom.setTable(table);
	}

	private void buildDialog(Custom custom, Class<T> clazz) {
		List<Custom.Element> dialog = new ArrayList<Custom.Element>();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(Render.class) && field.getAnnotation(Render.class).dialog()) {
				Render componentAnnotation = field.getAnnotation(Render.class);
				Custom.Element element = new Custom.Element();
				element.setTitle(field.getName());
				element.setType(componentAnnotation.type());
				element.setText(componentAnnotation.text());
				element.setValue(componentAnnotation.value());
				element.setUrl(componentAnnotation.url());
				dialog.add(element);
			}
		}
		custom.setDialog(dialog);
	}

	private void buildValidate(Custom custom, Class<T> clazz) {
		Set<Custom.Constraint> validate = new HashSet<Custom.Constraint>();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(Validate.class)) {
				Custom.Constraint constraint = new Custom.Constraint();
				Map<String, Object> constraintMap = new HashMap<String, Object>();
				Validate validateAnnotation = field.getAnnotation(Validate.class);
				constraintMap.put("required", validateAnnotation.required());
				constraintMap.put("unique", validateAnnotation.unique());
				constraintMap.put("maxlength", validateAnnotation.maxLength());
				constraintMap.put("minlength", validateAnnotation.minLength());
				constraint.setKey(field.getName());
				constraint.setConstraints(constraintMap);
				validate.add(constraint);
			}
		}
		custom.setValidate(validate);
	}
}
