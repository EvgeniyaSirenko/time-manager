package com.epam.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.AppException;

public class LogoutCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(LogoutCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		log.debug("Command starts");

		HttpSession session = req.getSession(false);
		session.setAttribute("defaultLocale", "en");
		if (session != null)
			session.invalidate();
		
		HttpSession session1 = req.getSession();
		session1.setAttribute("defaultLocale", "en");
		
		log.debug("Command finished");
		return Path.PAGE__LOGIN;
	}

}
