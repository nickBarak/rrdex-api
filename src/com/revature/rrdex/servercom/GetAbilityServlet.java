package com.revature.rrdex.servercom;

import java.io.BufferedReader;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
// import com.revature.rrdex.model.PokeData;
import com.revature.rrdex.model.Ability;
import com.revature.rrdex.service.PkmnService;
import com.revature.rrdex.template.InsertPkmnTemplate;
import com.revature.rrdex.template.InsertAbilityTemplate;
import com.revature.rrdex.model.User; 
//import com.revature.rrdex.model.Ability;
import com.revature.rrdex.dao.AbilityDAO;
import java.util.ArrayList;

public class GetAbilityServlet extends HttpServlet{
	private ObjectMapper objectMapper = new ObjectMapper();
	private PkmnService pkmnService = new PkmnService(); 
	static final Logger logger = Logger.getLogger(GetAbilityServlet.class);
	
	public GetAbilityServlet() {
		super();
	}
	
	public GetAbilityServlet(ObjectMapper objectMapper, PkmnService pkmnService) {
		this.objectMapper = objectMapper;
		this.pkmnService = pkmnService;
	}
	
	/**
	 * Get all abilities which are returned as a json string
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		resp.setContentType("application/json"); // This corresponds to MIME type standards

		if (name != null) {
			try {
				AbilityDAO abilityDAO = new AbilityDAO();
				Ability ability = abilityDAO.findAbilityByName(name);
				ArrayList<Ability> result = new ArrayList<>();
				result.add(ability);
				String jsonString = objectMapper.writeValueAsString(result.get(0) == null ? null : result);
				resp.getWriter().append(jsonString);
				resp.setStatus(200);
				return;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		String jsonString = objectMapper.writeValueAsString(pkmnService.getAllAbilityData());
		resp.getWriter().append(jsonString);
		logger.debug(jsonString);
		resp.setStatus(200);
	}
	
	/**
	 * Post request for abilities
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			BufferedReader reader = req.getReader();
			
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			
			String jsonString = sb.toString();
			JsonNode jsonNode = objectMapper.readTree(jsonString);

			String name = jsonNode.get("name").asText();
			String description = jsonNode.get("description").asText();

			HttpSession session = req.getSession();
			// User user = (User) session.getAttribute("user");
			// if ( user == null || ! (user.getUsername().equals("admin")) ) {
			// 	resp.getWriter().append("You do not have permission to insert");
			// 	resp.setStatus(400);
			// 	return;
			// };
			// Ability ability;
			// Jackson Databind parsing the jsonString and creating an InsertPirateTemplate object, with that data
			// InsertAbilityTemplate abilityData = objectMapper.readValue(jsonString, InsertAbilityTemplate.class);
//			if(abilityData.getUpdate() == 1) {
			// ability = pkmnService.insertAbilityData(abilityData);
//			}
//			else{
//				ability = pkmnService.insertAbilityData(abilityData);
//			}

			AbilityDAO abilityDAO = new AbilityDAO();

			Ability ability = abilityDAO.findAbilityByName(name);
			if (ability != null) {
				resp.getWriter().append("Ability " + name + " already exists");
				resp.setStatus(401);
				return;
			}

			abilityDAO.insertAbilityData(name, description);
			
			// String insertedPokemonJSON = objectMapper.writeValueAsString(ability);
			// resp.getWriter().append(insertedPokemonJSON);
			logger.debug("Inserted ability " + name);
			// resp.setContentType("application/json");
			resp.setStatus(201);
		} catch (JsonProcessingException e) {
			resp.setStatus(400);
		}
		
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader reader = req.getReader();
		
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		
		String jsonString = sb.toString();
		
		try {
			HttpSession session = req.getSession();
			User user = (User) session.getAttribute("user");
			if ( user == null || ! (user.getUsername().equals("admin")) ) {
				resp.getWriter().append("You do not have permission to insert");
				resp.setStatus(400);
				return;
			};
			Ability ability;
			// Jackson Databind parsing the jsonString and creating an InsertPirateTemplate object, with that data
			InsertAbilityTemplate abilityData = objectMapper.readValue(jsonString, InsertAbilityTemplate.class);
			ability = pkmnService.updateAbilityData(abilityData);
			
			String insertedPokemonJSON = objectMapper.writeValueAsString(ability);
			resp.getWriter().append(insertedPokemonJSON);
			logger.debug("Updated pokemon " + insertedPokemonJSON);
			resp.setContentType("application/json");
			resp.setStatus(201);
		} catch (JsonProcessingException e) {
			resp.setStatus(400);
		}
		
	}
	
	/**
	 * For delete requests, only given to admin perms 
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		// User user = (User) session.getAttribute("user");
		// if ( user == null ||!(user.getUsername().equals("admin")) ) {
		// 	resp.getWriter().append("You do not have permission to delete");
		// 	resp.setStatus(400);
		// 	return;
		// };
		
		try {
			BufferedReader reader = req.getReader();
			
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			
			String jsonString = sb.toString();
			JsonNode jsonNode = objectMapper.readTree(jsonString);

			String name = jsonNode.get("name").asText();
			// Jackson Databind parsing the jsonString and creating an InsertPirateTemplate object, with that data
			// InsertAbilityTemplate abilityData = objectMapper.readValue(jsonString, InsertAbilityTemplate.class);
			
			// boolean isDeleted = pkmnService.deleteAbilityData(abilityData);
			AbilityDAO abilityDAO = new AbilityDAO();

			Ability ability = abilityDAO.findAbilityByName(name);
			if (ability == null) {
				resp.getWriter().append("Ability " + name + " does not exist");
				resp.setStatus(200);
				return;
			}

			abilityDAO.deleteAbilityData(name);

			// if(isDeleted) {
				resp.getWriter().append("Deleted " + name +" succesfully");
				logger.debug("Deleted " + name +" succesfully");
			// }
			resp.setStatus(200);
		} catch (JsonProcessingException e) {
			resp.setStatus(400);
		}
		
		
	}
}
