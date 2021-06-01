package com.epam.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.web.command.Command;
import com.epam.web.command.CommandContainer;


@WebServlet("/controller")
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LogManager.getLogger(Controller.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Controller#doGet");

		String address = "error.jsp";

		String commandName = req.getParameter("command");
		System.out.println("commandName ==> " + commandName);
		
		Command command = CommandContainer.getCommand(commandName);
		System.out.println("command ==> " + command);
		
		try {
			address = command.execute(req, resp);
		} catch (IOException ex) {
			req.setAttribute("error", ex);
		}
		
		System.out.println("address == > " + address);
		req.getRequestDispatcher(address).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Controller#doPost");

		String address = "error.jsp";
		String commandName = req.getParameter("command");
		Command command = CommandContainer.getCommand(commandName);
		
		try {
			address = command.execute(req, resp);
		} catch (IOException ex) {
			req.getSession().setAttribute("error", ex);
		}
		
		resp.sendRedirect(address);
	}

//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		process(req, resp);
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		process(req, resp);
//	}
//
//	private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//		
//		log.debug("Controller starts");
//
//		// extract command name from the request
//		String commandName = req.getParameter("command");
//		log.trace("Request parameter: command --> " + commandName);
//
//		// obtain command object by its name
//		Command command = CommandContainer.get(commandName);
//		log.trace("Obtained command --> " + command);
//
//		// execute command and get forward address
//		String forward = command.execute(req, resp);
//		log.trace("Forward address --> " + forward);
//
//		log.debug("Controller finished, now go to forward address --> " + forward);
//
//		// if the forward address is not null go to the address
//		if (forward != null) {
//			RequestDispatcher disp = req.getRequestDispatcher(forward);
//			disp.forward(req, resp);
//		}
//	}

}