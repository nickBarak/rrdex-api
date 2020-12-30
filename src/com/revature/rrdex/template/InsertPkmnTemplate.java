package com.revature.rrdex.template;

public class InsertPkmnTemplate {
	private String name;
	private String type1;
	private String type2;
	private String ability_name;
	
	public InsertPkmnTemplate() {
		super();
	}
	
	public InsertPkmnTemplate(String name, String type1, String type2, String ability_name) {
		this.name = name;
		this.type1 = type1;
		this.type2 = type2;
		this.ability_name = ability_name;
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

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}
	
	public String getAbility_name() {
		return this.ability_name;
	}
	
	public void setAbility_name(String ability_name) {
		this.ability_name = ability_name;
	}

	@Override
	public String toString() {
		return "InsertPkmnTemplate [name=" + name + ", type1=" + type1 + " type2=" + type2;
	}
}
