package com.revature.rrdex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.rrdex.model.PokeData;
import com.revature.rrdex.model.Ability;
import com.revature.rrdex.util.JDBCUtility;

public class AbilityDAO {
	/**
	 * Finds ability in the database by given Name, returns null if nothing is found
	 * @param String name
	 * @return Ability
	 */
	
	public Ability findAbilityByName(String name) {

		String sqlQuery = "SELECT * " + "FROM ability a " + "WHERE a.ability = ? ";
	

		try (Connection connection = JDBCUtility.getConnection()) {
			PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
			
			pstmt.setString(1, name);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int id = rs.getInt(1);
				String abilityName = rs.getString(2);
				String description = rs.getString(3);
				return new Ability(id, abilityName, description);
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Returns all abilities in the database
	 * @return ArrayList<Ability>
	 */
	public ArrayList<Ability> getAllAbilityData() {
		String sqlQuery = "SELECT * " + "FROM Ability";
		ArrayList<Ability> abilities = new ArrayList<>();

		try (Connection connection = JDBCUtility.getConnection()) {
			Statement stmt = connection.createStatement(); // Simple Statement, not to be confused Prepared Statement
			ResultSet rs = stmt.executeQuery(sqlQuery);

			// A ResultSet is basically an object representing the rows of data returned
			// from a query
			// it maintains a cursor that points to the current row of data
			// Initially, the cursor is placed before the very first row of data

			// | row 1 _ row 2 _ row 3
			// if you call the next() method on a ResultSet, it will move it forward
			// row 1 | row 2 _ row 3
			// row 1 _ row 2 | row 3
			// row 1 _ row 2 _ row 3 |

			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String abilityDesc = rs.getString(3);

				Ability abilityObj = new Ability(id, name, abilityDesc);

				abilities.add(abilityObj);
			}

			return abilities;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return abilities;
	}
	
	/**
	 * Inserts the given ability and also returns the ability object if inserted correctly
	 * @param String name
	 * @param String description
	 * @return Ability
	 */
	public Ability insertAbilityData(String name, String description) {
		try (Connection connection = JDBCUtility.getConnection()) {
			connection.setAutoCommit(false);
			Ability foundAbility = findAbilityByName(name);
			String sqlQuery = "INSERT INTO ability " + "(ability, description) " + "VALUES " + "(?, ?)";

			PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, name);
			pstmt.setString(2, description);

			if (pstmt.executeUpdate() != 1) {
				throw new SQLException("Inserting ability failed, no rows were affected");
			}

			int autoId = 0;
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				autoId = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Inserting Ability failed, no ID generated");
			}

			connection.commit();

			return new Ability(autoId, name, description);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**Called in put requests, updates the given ability
	 * @param String name
	 * @param String description
	 * @return
	 */
	public Ability updateAbilityData(String name, String description) {
		try (Connection connection = JDBCUtility.getConnection()) {
			connection.setAutoCommit(false);
			Ability foundAbility = findAbilityByName(name);
			String sqlQuery = "UPDATE ability " + "SET description = ? " + "WHERE ability = ?";

			PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, description);
			pstmt.setString(2, name);

			if (pstmt.executeUpdate() != 1) {
				throw new SQLException("Updating ability failed, no rows were affected");
			}
			

			int autoId = 0;
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				autoId = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Updating Ability failed, no ID generated");
			}

			connection.commit();

			return new Ability(autoId, name, description);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	/**
	 * Deletes the ability from data base given the name, throws exception if ability doesnt exist
	 * @param name
	 * @return
	 */
	public boolean deleteAbilityData(String name) {
		try (Connection connection = JDBCUtility.getConnection()) {
			connection.setAutoCommit(false);
			String sqlQuery = "DELETE FROM ability WHERE ability=?";

			PreparedStatement pstmt = connection.prepareStatement(sqlQuery);

			pstmt.setString(1, name);

			if (pstmt.executeUpdate() != 1) {
				throw new SQLException("Deleting ability " + name + "failed, no rows were affected");
			}

			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	
}
