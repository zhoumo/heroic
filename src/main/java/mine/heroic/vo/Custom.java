package mine.heroic.vo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Custom {

	public String key;

	public List<Column> table;

	public List<Element> dialog;

	public Set<Constraint> validate;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<Column> getTable() {
		return table;
	}

	public void setTable(List<Column> table) {
		this.table = table;
	}

	public List<Element> getDialog() {
		return dialog;
	}

	public void setDialog(List<Element> dialog) {
		this.dialog = dialog;
	}

	public Set<Constraint> getValidate() {
		return validate;
	}

	public void setValidate(Set<Constraint> validate) {
		this.validate = validate;
	}

	public static class Column {

		public String title;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}

	public static class Element {

		public String title;

		public String type;

		public String url;

		public Object text;

		public Object value;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Object getText() {
			return text;
		}

		public void setText(Object text) {
			this.text = text;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	}

	public static class Constraint {

		public String key;

		public Map<String, Object> constraints;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public Map<String, Object> getConstraints() {
			return constraints;
		}

		public void setConstraints(Map<String, Object> constraints) {
			this.constraints = constraints;
		}
	}
}
