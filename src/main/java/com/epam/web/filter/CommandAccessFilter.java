package com.epam.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.Role;


/**
 * Security filter. 
 */
public class CommandAccessFilter implements Filter {
	
	private static final Logger log = LogManager.getLogger(CommandAccessFilter.class);

	// commands access	
	private static Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();
	private static List<String> commons = new ArrayList<String>();	
	private static List<String> outOfControl = new ArrayList<String>();
	
	public void destroy() {
		log.debug("Filter destruction starts");
		// do nothing
		log.debug("Filter destruction finished");
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		log.debug("Filter starts");
		
		if (accessAllowed(req)) {
			log.debug("Filter finished");
			chain.doFilter(req, resp);
		} else {
			String errorMessasge = "You do not have permission to access the requested resource";
			
			req.setAttribute("errorMessage", errorMessasge);
			log.trace("Set the request attribute: errorMessage --> " + errorMessasge);
			
			req.getRequestDispatcher(Path.PAGE__ERROR_PAGE)
					.forward(req, resp);
		}
	}
	
	private boolean accessAllowed(ServletRequest request) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;

		String commandName = request.getParameter("command");

		if (commandName == null || commandName.isEmpty())
			return false;
		
		if (outOfControl.contains(commandName))
			return true;
		
		HttpSession session = httpRequest.getSession(false);
		if (session == null) 
			return false;
		
		Role participantRole = (Role)session.getAttribute("participantRole");
		if (participantRole == null)
			return false;
		return accessMap.get(participantRole).contains(commandName)
				|| commons.contains(commandName);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		log.debug("Filter initialization starts");
		
		// roles
		accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("admin")));
		accessMap.put(Role.CLIENT, asList(fConfig.getInitParameter("client")));
		log.trace("Access map --> " + accessMap);

		// commons
		commons = asList(fConfig.getInitParameter("common"));
		log.trace("Common commands --> " + commons);

		// out of control
		outOfControl = asList(fConfig.getInitParameter("out-of-control"));
		log.trace("Out of control commands --> " + outOfControl);
		
		log.debug("Filter initialization finished");
	}
	
	/**
	 * Extracts parameter values from string.
	 * 
	 * @param str
	 *            parameter values string.
	 * @return list of parameter values.
	 */
	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);		
		while (st.hasMoreTokens()) list.add(st.nextToken());
		return list;		
	}
		
}
