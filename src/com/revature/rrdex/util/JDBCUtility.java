package com.revature.rrdex.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;
import org.apache.log4j.Logger;

public class JDBCUtility {
	static final Logger logger = Logger.getLogger(JDBCUtility.class);
	/**
	 * Simply connects to the database
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		String url = System.getenv("DB_URL");
		String username = System.getenv("DB_USERNAME");
		String password = System.getenv("DB_PASSWORD");
		Connection connection = null;
		
		DriverManager.registerDriver(new Driver());
		connection = DriverManager.getConnection(url, username, password);
		
		logger.debug("Connected to database");
		return connection;
		
	}
}
