package com.revature.rrdex.unittest;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.rrdex.dao.AbilityDAO;
import com.revature.rrdex.model.Ability;

public class AbilityTest {

	private AbilityDAO abilityDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		AbilityDAO abilityDAO = new AbilityDAO(); 
	}
	
	// This runs after all tests have run. You might close your resources here that were being used
	// For example, FileReader
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	// This will run before every test
	@Before
	public void setUp() throws Exception {
	}

	// This will run after every test
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testFindAbility() {
		Ability blaze = abilityDAO.findAbilityByName("Blaze");
		assertEquals(blaze, not(null));
		assertEquals(blaze.getName(), "Blaze");
	}

}
