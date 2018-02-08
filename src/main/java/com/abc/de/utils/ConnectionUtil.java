package com.abc.de.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abc.de.config.Configuration;
import com.abc.de.config.Constants;

/**
 * Connection utility class used to get Phoenix connection object. command line.
 * 
 * @author Shekhar Suman
 * @version 1.0
 * @since 2017-03-01
 *
 */
public final class ConnectionUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionUtil.class);

	private ConnectionUtil() {

	}

	/**
	 * Get Pheonix connection object
	 * 
	 * @param config
	 *            configuration object holding url, driver info etc.
	 * @return Connection object
	 * @throws Exception
	 */
	public static Connection getPhoenixConnection(Configuration config) throws Exception {
		String phoenixUrl = null;
		Connection conn = null;
		try {
			phoenixUrl = config.getStringValue(Constants.PHOENIX_JDBC_URL, null);
			Class.forName(Constants.PHOENIX_DRIVER);
			conn = DriverManager.getConnection(phoenixUrl);
		} catch (Exception e) {
			LOGGER.error("Error getting Phoenix Connection", e);
		}
		return conn;
	}

}
