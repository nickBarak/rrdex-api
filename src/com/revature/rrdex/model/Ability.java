package com.revature.rrdex.model;

public class Ability {
	private String name;
	private String description;
	private int id;

	public Ability() {
		super();
	}
	
	public Ability(String name) {
		this.name = name;
	}
	
	public Ability(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public Ability(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public Ability(int id, String name){
		this.id = id;
		this.name = name; 
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Ability: " + this.name + " Description: " + this.description;
	}
	
	
}	
