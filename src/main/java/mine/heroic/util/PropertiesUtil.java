package mine.heroic.util;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

public class PropertiesUtil {

	private static HashMap<String, PropertiesUtil> propertiesMap = new HashMap<String, PropertiesUtil>();

	private Properties properties;

	private void readFile(String filePath) {
		properties = new Properties();
		try {
			FileInputStream fileInputStream = new FileInputStream(this.getClass().getResource(filePath).getFile());
			properties.load(fileInputStream);
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static PropertiesUtil getInstance(String propertiesName) {
		PropertiesUtil propertiesUtil = propertiesMap.get(propertiesName);
		if (propertiesUtil == null) {
			propertiesUtil = new PropertiesUtil();
			propertiesUtil.readFile(propertiesName);
			propertiesMap.put(propertiesName, propertiesUtil);
		}
		return propertiesUtil;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}
