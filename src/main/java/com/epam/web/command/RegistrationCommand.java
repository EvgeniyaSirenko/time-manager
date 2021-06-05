package com.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

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

		HttpSession session = req.getSession();

		// obtain login from the request
		String login = req.getParameter("login");
		log.trace("Request parameter: loging --> " + login);

		String errorMessage = null;
		String forward = Path.PAGE__ERROR_PAGE;

		Participant participant = new ParticipantManager().getParticipantByLogin(login);
		log.trace("Found in DB: participant --> " + participant);

		if (participant != null) {
			errorMessage = "Such login already exists";
			req.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return forward;
		} else {

			
			//send data to DB
			Participant newParticipant = new Participant();
			newParticipant.setFirstName(req.getParameter("firstName"));
			newParticipant.setLastName(req.getParameter("lastName"));
			newParticipant.setLogin(req.getParameter("login"));
			newParticipant.setPassword(req.getParameter("password"));
			newParticipant.setLocaleName(null);
			newParticipant.setRoleId(1);

			new ParticipantManager().createParticipant(newParticipant);
			System.out.println("Created new " + new ParticipantManager().getParticipantByLogin(newParticipant.getLogin()));
			
			forward = Path.PAGE__LOGIN;
		}
		

		// work with i18n TODO
//		String participantLocaleName = participant.getLocaleName();
//		log.trace("participantLocalName --> " + participantLocaleName);
//
//			if (participantLocaleName != null && !participantLocaleName.isEmpty()) {
//				Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", participantLocaleName);
//		
//				session.setAttribute("defaultLocale", participantLocaleName);
//				log.trace("Set the session attribute: defaultLocaleName --> " + participantLocaleName);
//		
//				log.info("Locale for participant: defaultLocale --> " + participantLocaleName);
//			}

		log.debug("Command finished");
		return forward;
	}
}
