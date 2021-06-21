package com.epam.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.db.AppException;
import com.epam.web.command.Command;
import com.epam.web.command.CommandContainer;

@WebServlet("/controller")
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(Controller.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.debug("Controller#doGet");

		String address = "error.jsp";

		String commandName = req.getParameter("command");
		log.trace("commandName ==> " + commandName);

		Command command = CommandContainer.getCommand(commandName);
		log.trace("command ==> " + command);

		try {
			address = command.execute(req, resp);
		} catch (AppException ex) {
			req.setAttribute("error", ex);
		}

		log.trace("address == > " + address);
		req.getRequestDispatcher(address).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("Controller#doPost");

		String address = "error.jsp";

		String commandName = req.getParameter("command");
		log.trace("commandName ==> " + commandName);

		Command command = CommandContainer.getCommand(commandName);
		log.trace("command ==> " + command);

		try {
			address = command.execute(req, resp);
		} catch (AppException ex) {
			req.getSession().setAttribute("error", ex);
			log.trace("error ==> " + ex);
		}
		log.trace("address == > " + address);
		resp.sendRedirect(address);

	}
}