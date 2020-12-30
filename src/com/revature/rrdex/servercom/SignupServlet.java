package com.revature.rrdex.servercom;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import com.revature.rrdex.dao.UserDAO;
import com.revature.rrdex.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static final Logger logger = Logger.getLogger(UserLoginServlet.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    private void SignupServlet(){}

    private void SignupServlet(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
        try {
            BufferedReader reader = request.getReader();
			
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			
			String jsonString = sb.toString();
			JsonNode jsonNode = objectMapper.readTree(jsonString);

			String username = jsonNode.get("username").asText();
			String password = jsonNode.get("password").asText();
             
            UserDAO userDao = new UserDAO();
            boolean userExists = userDao.checkExists(username);
             
            if (!userExists) {
                userDao.insertUser(username, password);
                User user = userDao.checkLogin(username, password);
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                String message = "Logged in as " + username;
                response.getWriter().append(message);
                logger.debug(message);
            } else {
                String message = "Username " + username + " not available";
                logger.debug(message);
                response.getWriter().append(message);
                response.setStatus(422);
            }
             
        } catch (SQLException | ClassNotFoundException ex) {
            response.setStatus(400);
            throw new ServletException(ex);
        }
    }
	
}
