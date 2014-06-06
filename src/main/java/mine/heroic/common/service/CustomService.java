package mine.heroic.common.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import mine.heroic.common.BaseEntity;
import mine.heroic.common.annotation.Component;
import mine.heroic.common.annotation.Title;
import mine.heroic.common.annotation.Validate;
import mine.heroic.util.BeanUtil;
import mine.heroic.vo.Custom;

@Service
public class CustomService<T extends BaseEntity> {

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
