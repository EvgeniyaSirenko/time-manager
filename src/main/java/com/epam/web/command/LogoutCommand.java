package com.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;

public class LogoutCommand extends Command {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(LogoutCommand.class);
	
	@Override
	public String execute(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		log.debug("Command starts");
		
		HttpSession session = req.getSession(false);
		if (session != null)
			session.invalidate();

		log.debug("Command finished");
		return Path.PAGE__LOGIN;
	}

}
