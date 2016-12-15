package sail.beans.support;

/**
 * Title:        public javabean
 * Description:  server public javabeans
 * Copyright:    Copyright (c) 2001 - 2006
 * Company:      talkweb
 * @author       mark, tanghao
 * @version      1.1
 */

import java.io.InputStream;
import java.util.Properties;

public class PropertyBean {
	private static final String cfgFileName = "/config/cfg.properties";

	public static final Properties getProperties() {
		InputStream is = PropertyBean.class.getResourceAsStream(cfgFileName);
		Properties props = new Properties();

		try {
			props.load(is);
		} catch (Exception e) {
			return null;
		}
		return props;
	}

	public static final String getProperty(String key) {
		String value = null;
		InputStream is = PropertyBean.class.getResourceAsStream(cfgFileName);
		Properties props = new Properties();

		try {
			props.load(is);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		value = props.getProperty(key);

		try {
			if (value != null) {
				value = new String(value.trim().getBytes("ISO-8859-1"),
						"gb2312");
			}
			is.close();
		} catch (Exception ex) {
		}

		props.clear();
		props = null;
		is = null;
		return value;
	}

	public static final Properties getProperties(String cfgFileName) {
		InputStream is = PropertyBean.class.getResourceAsStream(cfgFileName);
		Properties props = new Properties();

		try {
			props.load(is);
			is.close();
		} catch (Exception e) {
			return null;
		}
		return props;
	}

	public static final String getProperty(String cfgFileName, String key) {
		String value = null;
		InputStream is = PropertyBean.class.getResourceAsStream(cfgFileName);
		Properties props = new Properties();

		try {
			props.load(is);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		value = props.getProperty(key);

		try {
			if (value != null) {
				value = new String(value.trim().getBytes("ISO-8859-1"),
						"gb2312");
			}
			is.close();
		} catch (Exception ex) {
		}

		props.clear();
		props = null;
		is = null;
		return value;
	} 

	public static final String getProperty(Properties props, String key) {
		String value = null;   

		value = props.getProperty(key);

		try {
			if (value != null) {
				value = new String(value.trim().getBytes("ISO-8859-1"),
						"gb2312");
			} 
		} catch (Exception ex) {
		} 
		return value;
	}
}
