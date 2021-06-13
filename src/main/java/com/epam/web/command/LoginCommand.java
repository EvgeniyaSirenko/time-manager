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
import com.epam.db.Role;
import com.epam.db.ParticipantManager;
import com.epam.db.entity.Participant;

public class LoginCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = req.getSession();

		String login = req.getParameter("login");
		log.trace("Request parameter: loging --> " + login);

		String password = req.getParameter("password");

		String errorMessage = null;
		String forward = Path.PAGE__ERROR_PAGE;

		if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
			errorMessage = "Login/password cannot be empty";
			req.getSession().setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return forward;
		}

		Participant participant = new ParticipantManager().getParticipantByLogin(login);
		log.trace("Found in DB: participant --> " + participant);

		if (participant == null || !password.equals(participant.getPassword())) {
			errorMessage = "Cannot find participant with such login/password";
			req.getSession().setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return forward;
		} else {
			Role participantRole = Role.getRole(participant);
			log.trace("participantRole --> " + participantRole);

			if (participantRole == Role.ADMIN)
				forward = Path.PAGE__ADMIN_MAIN_PAGE;

			if (participantRole == Role.CLIENT)
				forward = Path.PAGE__PARTICIPANT_MAIN_PAGE;

			session.setAttribute("participant", participant);
			log.trace("Set the session attribute: participant --> " + participant);

			session.setAttribute("participantRole", participantRole);
			log.trace("Set the session attribute: participantRole --> " + participantRole);

			log.info("participant " + participant + " logged as " + participantRole.toString().toLowerCase());

			String participantLocaleName = participant.getLocaleName();
			log.trace("participantLocalName --> " + participantLocaleName);

			if (participantLocaleName != null && !participantLocaleName.isEmpty()) {
				Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", participantLocaleName);

				session.setAttribute("defaultLocale", participantLocaleName);
				log.trace("Set the session attribute: defaultLocaleName --> " + participantLocaleName);

				log.info("Locale for participant: defaultLocale --> " + participantLocaleName);
			}
		}

		log.debug("Command finished");
		return forward;
	}

}