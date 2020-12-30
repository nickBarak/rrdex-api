package com.revature.rrdex.service;

import java.util.ArrayList;
import com.revature.rrdex.model.PokeData;
import com.revature.rrdex.model.Ability;
import com.revature.rrdex.dao.AbilityDAO;
import com.revature.rrdex.dao.PokeDataDAO;
import com.revature.rrdex.template.InsertPkmnTemplate;
import com.revature.rrdex.template.InsertAbilityTemplate;
//import com.revature.rrdex.exception.AbilityNotFoundException;

public class PkmnService {
	private PokeDataDAO pokeDataDAO;
	private AbilityDAO abilityDAO;
	
	public PkmnService() {
		this.pokeDataDAO = new PokeDataDAO();
		this.abilityDAO = new AbilityDAO();
	}
	
	public PkmnService(PokeDataDAO pokeDataDAO, AbilityDAO abilityDAO ) {
		this.pokeDataDAO = pokeDataDAO;
		this.abilityDAO = abilityDAO; 
	}
	
	public ArrayList<PokeData> getAllPokeData() {
		return pokeDataDAO.getAllPokeData();
	}
	
	public ArrayList<Ability> getAllAbilityData() {
		return abilityDAO.getAllAbilityData();
	}
	
	public PokeData getPokeData(String name) {
		return pokeDataDAO.getPokeData(name);
	}
	
	public ArrayList<PokeData> getPokeDataType(String type) {
		return pokeDataDAO.getPokeDataType(type);
	}
	
	public PokeData insertPokeData(InsertPkmnTemplate pkmnTemplate) {
		Ability ability;
		ability = abilityDAO.findAbilityByName(pkmnTemplate.getAbility_name());
		
//		if (ability == null) {
//			throw new AbilityNotFoundException("The user attempted to insert a pokemon for an ability that does not exist");
//		}
		
		return pokeDataDAO.insertPokeData(pkmnTemplate.getName(), pkmnTemplate.getType1(), pkmnTemplate.getType2(), ability);
	}
	
	public PokeData updatePokeData(InsertPkmnTemplate pkmnTemplate) {
		Ability ability;
		ability = abilityDAO.findAbilityByName(pkmnTemplate.getAbility_name());
		
		return pokeDataDAO.updatePokeData(pkmnTemplate.getName(), pkmnTemplate.getType1(), pkmnTemplate.getType2(), ability);
	}
	
	public Ability insertAbilityData(InsertAbilityTemplate abilityTemplate) {
		return abilityDAO.insertAbilityData(abilityTemplate.getName(), abilityTemplate.getDescription());
		
	}
	
	public Ability updateAbilityData(InsertAbilityTemplate abilityTemplate) {
		return abilityDAO.updateAbilityData(abilityTemplate.getName(), abilityTemplate.getDescription());
	}
	
	public boolean deleteAbilityData(InsertAbilityTemplate abilityTemplate) {
		return abilityDAO.deleteAbilityData(abilityTemplate.getName());
	}
	
	public boolean deletePokemonData(InsertPkmnTemplate pkmnTemplate) {
		return pokeDataDAO.deletePokemonData(pkmnTemplate.getName());
	}
	
}
