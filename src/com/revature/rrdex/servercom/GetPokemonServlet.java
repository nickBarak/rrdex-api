package com.revature.rrdex.servercom;

import com.revature.rrdex.service.PkmnService;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.revature.rrdex.model.PokeData;
import com.revature.rrdex.model.User;
import com.revature.rrdex.model.Ability;
import com.revature.rrdex.template.InsertAbilityTemplate;
import com.revature.rrdex.template.InsertPkmnTemplate;
import java.util.ArrayList;

import com.revature.rrdex.dao.AbilityDAO;
import com.revature.rrdex.dao.PokeDataDAO;

public class GetPokemonServlet extends HttpServlet {
	private ObjectMapper objectMapper = new ObjectMapper();
	private PkmnService pkmnService = new PkmnService(); 
	static final Logger logger = Logger.getLogger(GetPokemonServlet.class);
	public GetPokemonServlet() {
		super();
	}
	
	public GetPokemonServlet(ObjectMapper objectMapper, PkmnService pkmnService) {
		this.objectMapper = objectMapper;
		this.pkmnService = pkmnService;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pkmnName = req.getParameter("name");
		String type1 = req.getParameter("type");
		String jsonString;
		if(pkmnName != null) {
			ArrayList<PokeData> results = new ArrayList();
			results.add(pkmnService.getPokeData(pkmnName));
			jsonString = objectMapper.writeValueAsString(results.get(0) == null ? null : results);
		}
		else if(type1 != null) {
			jsonString = objectMapper.writeValueAsString(pkmnService.getPokeDataType(type1));
		}
		else {
			jsonString = objectMapper.writeValueAsString(pkmnService.getAllPokeData());
		}
		resp.getWriter().append(jsonString);
		logger.debug("Got these Pokemon:  " + jsonString);
		
		resp.setContentType("application/json"); // This corresponds to MIME type standards
		resp.setStatus(200);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			logger.debug("adding pokemon...");

			BufferedReader reader = req.getReader();
			
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			
			String jsonString = sb.toString();
			JsonNode jsonNode = objectMapper.readTree(jsonString);

			String name = jsonNode.get("name").asText();
			String type1 = jsonNode.get("type1").asText();
			String type2 = jsonNode.get("type2").asText();
			String abilityName = jsonNode.get("abilityName").asText();
			if (type2.equals("")) type2 = type1;

			logger.debug("request parsed:\n" + name + "\n" + type1 + "\n" + type2 + "\n" +abilityName);
	
			AbilityDAO abilityDAO = new AbilityDAO();
			PokeDataDAO pokedataDAO = new PokeDataDAO();

			HttpSession session = req.getSession();
			User user = null;
			if (session.getAttribute("user") != null) {
				user = (User) session.getAttribute("user");
			}

			// if ( user == null || !(user.getUsername().equals("admin")) ) {
			// 	logger.debug("User is not admin or unauthenticated");
			// 	resp.getWriter().append("You do not have permission to insert");
			// 	resp.setStatus(400);
			// 	return;
			// };

			logger.debug("user is admin");

			Ability ability = abilityDAO.findAbilityByName(abilityName);

			
			if (ability == null) {
				resp.getWriter().append("Ability " + abilityName + " does not exist");
				resp.setStatus(200);
				return;
			}

			logger.debug("ability found and is not null");
			logger.debug("inserting pokemon to database...");

			pokedataDAO.insertPokeData(name, type1, type2, ability);
			
			// Jackson Databind parsing the jsonString and creating an InsertPirateTemplate object, with that data
			// InsertPkmnTemplate pokemonData = objectMapper.readValue(jsonString, InsertPkmnTemplate.class);
			
			// PokeData pokedata = pkmnService.insertPokeData(pokemonData);
			
			// String insertedPirateJSON = objectMapper.writeValueAsString(pokedata);
			// resp.getWriter().append(insertedPirateJSON);
			
			logger.debug("Inserted pokemon:  " + name);
			// resp.setContentType("application/json");
			resp.setStatus(201);
		} catch (Exception e) {
			resp.setStatus(400);
			logger.debug(e);
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
			if ( user == null || !(user.getUsername().equals("admin")) ) {
				resp.getWriter().append("You do not have permission to insert");
				resp.setStatus(400);
				return;
			};
			
			// Jackson Databind parsing the jsonString and creating an InsertPirateTemplate object, with that data
			InsertPkmnTemplate pokemonData = objectMapper.readValue(jsonString, InsertPkmnTemplate.class);
			
			PokeData pokedata = pkmnService.updatePokeData(pokemonData);
			
			String insertedPirateJSON = objectMapper.writeValueAsString(pokedata);
			resp.getWriter().append(insertedPirateJSON);
			
			logger.debug("Updated these Pokemon:  " + insertedPirateJSON);
			resp.setContentType("application/json");
			resp.setStatus(201);
		} catch (JsonProcessingException e) {
			resp.setStatus(400);
		}
		
	}
	
	//Todo: Do exceptions?
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
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
			// InsertPkmnTemplate pkmnData = objectMapper.readValue(jsonString, InsertPkmnTemplate.class);
			
			// boolean isDeleted = pkmnService.deletePokemonData(pkmnData);
			PokeDataDAO pokedataDAO = new PokeDataDAO();
			pokedataDAO.deletePokemonData(name);

			// if(isDeleted) {
				resp.getWriter().append("Deleted " + name +" succesfully");
				logger.debug("Deleted " + name + "succesfully");
			// }
			resp.setStatus(200);
		} catch (Exception e) {
			logger.debug(e);
			resp.setStatus(400);
		}
		
		
	}
	
}
