package com.revature.rrdex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.rrdex.model.Ability;
import com.revature.rrdex.model.PokeData;
import com.revature.rrdex.util.JDBCUtility;

public class PokeDataDAO {

	public ArrayList<PokeData> getAllPokeData() {

		String sqlQuery = "SELECT * " + "FROM PokeData p " + "INNER JOIN ability a " + "ON p.ability_id = a.id";

		ArrayList<PokeData> pokemon = new ArrayList<>();

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
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String type1 = rs.getString("type1");
				String type2 = rs.getString("type2");
				
				int abilityId = rs.getInt("ability_id");
				String ability = rs.getString("ability");
				String abilityDesc = rs.getString("description");

				Ability abilityObj = new Ability(abilityId, ability, abilityDesc);
				PokeData pokemonObj = new PokeData(id, name, type1, type2, abilityObj);

				pokemon.add(pokemonObj);
			}

			return pokemon;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pokemon;
	}
	
	public PokeData getPokeData(String pkmnName) {
		String sqlQuery = "SELECT * " + "FROM pokedata, ability " + "WHERE pokedata.name = ? and Pokedata.ability_id = ability.id";

		try (Connection connection = JDBCUtility.getConnection()) {
			PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
			
			pstmt.setString(1, pkmnName);


			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String type1 = rs.getString("type1");
				String type2 = rs.getString("type2");
				
				int abilityId = rs.getInt("ability_id");
				String ability = rs.getString("ability");
				String abilityDesc = rs.getString("description");
				Ability abilityObj = new Ability(abilityId, ability, abilityDesc);
				PokeData pokemonObj = new PokeData(id, name, type1, type2, abilityObj);
				
				return pokemonObj;
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public ArrayList<PokeData> getPokeDataType(String type) {
		String sqlQuery = "SELECT DISTINCT ON (pokedata.name) pokedata.id as pokedataId, pokedata.name, pokedata.type1, pokedata.type2, ability.id as abilityId, ability.ability, ability.description " + "FROM pokedata, ability " + "WHERE pokedata.type1 = ? OR pokedata.type2 = ? " + 
				"AND pokedata.ability_id = ability.id " + "ORDER BY pokedata.name;";
		ArrayList<PokeData> pokemon = new ArrayList<>();
		
		try (Connection connection = JDBCUtility.getConnection()) {
			PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
			
			pstmt.setString(1, type);
			pstmt.setString(2, type);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("pokedataId");
				String name = rs.getString("name");
				String type1 = rs.getString("type1");
				String type2 = rs.getString("type2");
				int abilityId = rs.getInt("abilityId");
				String ability = rs.getString("ability");
				String abilityDesc = rs.getString("description");

				Ability abilityObj = new Ability(abilityId, ability, abilityDesc);
				PokeData pokemonObj = new PokeData(id, name, type1, type2, abilityObj);

				pokemon.add(pokemonObj);
			}
			return pokemon;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public PokeData insertPokeData(String pkmnName, String type1, String type2, Ability ability) {
		try (Connection connection = JDBCUtility.getConnection()) {
			connection.setAutoCommit(false); 
			
			PokeData apokemon = getPokeData(pkmnName);
	
			String sqlQuery = "INSERT INTO pokedata " + "(name, type1, type2, ability_id) " + "VALUES " + "(?, ?, ?, ?)";

			PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, pkmnName);
			pstmt.setString(2, type1);
			pstmt.setString(3, type2);
			pstmt.setInt(4, ability.getId());

			if (pstmt.executeUpdate() != 1) {
				throw new SQLException("Inserting pokedata failed, no rows were affected");
			}

			int autoId = 0;
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				autoId = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Inserting PokeData failed, no ID generated");
			}

			connection.commit();

			return new PokeData(autoId, pkmnName, type1, type2, ability);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public PokeData updatePokeData(String pkmnName, String type1, String type2, Ability ability) {
		try (Connection connection = JDBCUtility.getConnection()) {
			connection.setAutoCommit(false); 
			
			PokeData apokemon = getPokeData(pkmnName);
			String sqlQuery = "UPDATE pokedata " + "SET type1 = ?, " + "type2 = ?, " + "ability_id = ?" + "WHERE pokedata.name = ?";
			
			PreparedStatement pstmt = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
		
			pstmt.setString(1, type1);
			pstmt.setString(2, type2);
			pstmt.setInt(3, ability.getId());
			pstmt.setString(4, pkmnName);
			
			if (pstmt.executeUpdate() != 1) {
				throw new SQLException("Updating Pokedata failed, no rows were affected");
			}
			
			int autoId = 0;
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				autoId = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Inserting PokeData failed, no ID generated");
			}
			
			connection.commit();
			
			return new PokeData(autoId, pkmnName, type1, type2, ability);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	public boolean deletePokemonData(String name) {
		try (Connection connection = JDBCUtility.getConnection()) {
			connection.setAutoCommit(false);
			String sqlQuery = "DELETE FROM pokedata WHERE name=?";

			PreparedStatement pstmt = connection.prepareStatement(sqlQuery);

			pstmt.setString(1, name);

			if (pstmt.executeUpdate() != 1) {
				throw new SQLException("Deleting Pokemon " + name + "failed, no rows were affected");
			}

			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
