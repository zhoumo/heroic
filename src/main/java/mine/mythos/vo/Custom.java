package mine.mythos.vo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Custom {

	public DataTable dataTable;

	public Dialog dialog;

	public Set<Validate> validates;

	public DataTable instanceDataTable() {
		return new DataTable();
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public Dialog instanceDialog() {
		return new Dialog();
	}

	public Dialog getDialog() {
		return dialog;
	}

	public void setDialog(Dialog dialog) {
		this.dialog = dialog;
	}

	public Set<Validate> getValidates() {
		return validates;
	}

	public void setValidates(Set<Validate> validates) {
		this.validates = validates;
	}

	public class DataTable {

		public List<String> keys;

		public List<String> titles;

		public List<String> getKeys() {
			return keys;
		}

		public void setKeys(List<String> keys) {
			this.keys = keys;
		}

		public List<String> getTitles() {
			return titles;
		}

		public void setTitles(List<String> titles) {
			this.titles = titles;
		}
	}

	public class Dialog {

		public String key;

		public List<Layout> layout;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public List<Layout> getLayout() {
			return layout;
		}

		public void setLayout(List<Layout> layout) {
			this.layout = layout;
		}
	}

	public Layout instanceLayout() {
		return new Layout();
	}

	public class Layout {

		public String title;

		public String key;

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

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
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

	public Validate instanceValidate() {
		return new Validate();
	}

	public class Validate {

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
