package com.revature.rrdex.model;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class PokeData {
	private String name; 
	private String type1;
	private String type2;
	private int id; 
	private Ability abilityObj;
	
	public PokeData() {
		super();
	}
	
//	public PokeData(String name, String type1, String type2) {
//		this.name = name;
//		this.type1 = type1;
//		this.type2 = type2; 
//	}
//	
//	public PokeData(int id, String name, String type1, String type2) {
//		this.name = name;
//		this.type1 = type1;
//		this.type2 = type2; 
//	}
	
//	public PokeData(int id, String name, Ability abilityObj) {
//		this.id = id;
//		this.name = name;
//		this.abilityObj = abilityObj;
//	}
	
	public PokeData(int id, String name, String type1, String type2, Ability abilityObj) {
		this.id = id;
		this.type1 = type1;
		this.type2 = type2;
		this.name = name;
		this.abilityObj = abilityObj;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Ability getAbilityObj() {
		return abilityObj;
	}

	public void setAbilityObj(Ability abilityObj) {
		this.abilityObj = abilityObj;
	}

	@Override
	public String toString() {
		return "Pokemon: " + this.name + " is type " + this.type1 + "/" + this.type2 + "with ability " + this.abilityObj;
	}
	

}
