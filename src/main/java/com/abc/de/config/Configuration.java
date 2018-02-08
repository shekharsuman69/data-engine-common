package com.abc.de.config;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Configuration class used to load the user provided properties file at the
 * command line.
 * 
 * @author Shekhar Suman
 * @version 1.0
 * @since 2017-03-01
 *
 */
public class Configuration implements Serializable {

	private static final long serialVersionUID = 3880663494270857830L;

	private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

	private Config config;

	/**
	 * @param confPath
	 */
	public Configuration(String confPath) {
		try {
			config = ConfigFactory.load(confPath);
		} catch (Exception e) {
			LOGGER.error("spark.data.engine.properties not found on classpath, EXITING.", e);
			System.exit(1);
		}
	}

	/**
	 * @param path
	 * @param defaultValue
	 * @return
	 */
	public boolean getBooleanValue(String path, boolean defaultValue) {
		boolean value;
		if (config.hasPath(path)) {
			value = config.getBoolean(path);
		} else {
			value = defaultValue;
		}
		return value;
	}

	/**
	 * @param path
	 * @param defaultValue
	 * @return
	 */
	public String getStringValue(String path, String defaultValue) {
		String value;
		if (config.hasPath(path)) {
			value = config.getString(path);
		} else {
			value = defaultValue;
		}
		return value;
	}

	/**
	 * @param path
	 * @param defaultValue
	 * @return
	 */
	public Integer getIntegerValue(String path, int defaultValue) {
		Integer value;
		if (config.hasPath(path)) {
			value = config.getInt(path);
		} else {
			value = defaultValue;
		}
		return value;
	}

	/**
	 * @param path
	 * @param defaultValue
	 * @return
	 */
	public long getLongValue(String path, long defaultValue) {
		long value;
		if (config.hasPath(path)) {
			value = config.getLong(path);
		} else {
			value = defaultValue;
		}
		return value;
	}
}
