package com.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.ParticipantManager;
import com.epam.db.entity.Participant;

public class RegistrationCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(RegistrationCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.debug("Command starts");

		String login = req.getParameter("login");
		log.trace("Request parameter: loging --> " + login);

		String errorMessage = null;
		String forward = Path.PAGE__ERROR_PAGE;

		Participant participant = new ParticipantManager().getParticipantByLogin(login);
		log.trace("Found in DB: participant --> " + participant);

		if (participant != null) {
			errorMessage = "Such login already exists";
			req.getSession().setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return forward;
		} else {

			Participant newParticipant = new Participant();
			newParticipant.setFirstName(req.getParameter("firstName"));
			newParticipant.setLastName(req.getParameter("lastName"));
			newParticipant.setLogin(req.getParameter("login"));
			newParticipant.setPassword(req.getParameter("password"));
			newParticipant.setLocaleName(req.getParameter("localeName"));
			newParticipant.setRoleId(1);

			new ParticipantManager().createParticipant(newParticipant);
			System.out.println(
					"Created new " + new ParticipantManager().getParticipantByLogin(newParticipant.getLogin()));

			forward = Path.PAGE__LOGIN;
		}

		log.debug("Command finished");
		return forward;
	}
}
