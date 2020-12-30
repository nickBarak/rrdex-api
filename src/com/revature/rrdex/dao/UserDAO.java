package com.revature.rrdex.dao;

import java.sql.*;

import com.revature.rrdex.model.User;
import com.revature.rrdex.util.JDBCUtility;

public class UserDAO {
	/**
	 * Checks if the user and password correspond to database to perform a login
	 * Very rudimentary and insecure! 
	 * @param String username
	 * @param String password
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public User checkLogin(String username, String password) throws SQLException, ClassNotFoundException {
		try (Connection connection = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM users WHERE username = ? and password = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
	
			ResultSet result = statement.executeQuery();
	
			User user = null;
	
			if (result.next()) {
				user = new User();
				user.setUsername(result.getString("username"));
			}
			
			connection.close();
			return user;
		} catch(SQLException e){
			e.printStackTrace();
		}
		return null; 
	}

	public boolean checkExists(String username) throws SQLException {
		String sql = "SELECT username FROM users WHERE username = ?;";

		try (Connection connection = JDBCUtility.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return true;
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public User insertUser(String username, String password) throws SQLException, ClassNotFoundException {
		try (Connection connection = JDBCUtility.getConnection()) {
			String sql = "INSERT INTO users " 
					+ "(username, password) " 
					+ "VALUES " + "(?,?)";
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, username);
			statement.setString(2, password);
	
			if (statement.executeUpdate() != 1) {
				throw new SQLException("Inserting user failed, no rows were affected");
			}

			int autoId = 0;
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				autoId = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Inserting userfailed, no ID generated");
			}
			
			connection.close();
			return new User(autoId, username, password);
		} catch(SQLException e){
			e.printStackTrace();
		}
		return null; 
	}
}
