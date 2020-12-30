package com.revature.rrdex.template;

public class InsertAbilityTemplate {
	private String name;
	private String description;
	private int update;

	public InsertAbilityTemplate() {
		super();
	}
	
	public InsertAbilityTemplate(String name, String description)
	{
		this.name = name;
		this.description = description;
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
	
	public int getUpdate() {
		return update;
	}

	public void setUpdate(int update) {
		this.update = update;
	}
	
	@Override
	public String toString() {
		return "InsertAbilityTemplate [name=" + name + ", description=" + description; 
	}
}
