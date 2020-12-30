package com.revature.rrdex.servercom;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;


/**
 * Servlet implementation class UserLogoutServlet, simply removes the attribute
 * from the session.
 */
public class UserLogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static final Logger logger = Logger.getLogger(UserLogoutServlet.class);
    
    public UserLogoutServlet() {
        super();
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        HttpSession session = request.getSession();
        session.invalidate();
        logger.debug("Logged out!");
    }
}
