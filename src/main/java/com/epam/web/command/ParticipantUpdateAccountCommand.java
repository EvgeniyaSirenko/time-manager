package com.epam.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.AppException;
import com.epam.db.ParticipantManager;
import com.epam.db.entity.Participant;

public class ParticipantUpdateAccountCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ParticipantUpdateAccountCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		log.debug("Command starts");

		Participant participant = (Participant) req.getSession().getAttribute("participant");
		boolean updateParticipant = false;

		String firstName = req.getParameter("firstName");
		if (firstName != null && !firstName.isEmpty()) {
			participant.setFirstName(firstName);
			updateParticipant = true;
			log.trace("updatedParticipant ->" + updateParticipant);
		}

		String lastName = req.getParameter("lastName");
		if (lastName != null && !lastName.isEmpty()) {
			participant.setLastName(lastName);
			updateParticipant = true;
			log.trace("updatedParticipant ->" + updateParticipant);
		}

		String login = req.getParameter("login");
		if (login != null && !login.isEmpty()) {
			participant.setLogin(login);
			updateParticipant = true;
			log.trace("updatedParticipant ->" + updateParticipant);
		}

		String password = req.getParameter("password");
		if (password != null && !password.isEmpty()) {
			participant.setPassword(password);
			updateParticipant = true;
			log.trace("updatedParticipant ->" + updateParticipant);
		}

		String localeToSet = req.getParameter("localeToSet");
		if (localeToSet != null && !localeToSet.isEmpty()) {
			HttpSession session = req.getSession();
			session.setAttribute("defaultLocale", localeToSet);
			participant.setLocaleName(localeToSet);
			updateParticipant = true;
		}

		if (updateParticipant == true)
			new ParticipantManager().updateParticipant(participant);
		log.trace("updated participant ->" + new ParticipantManager().getParticipantByLogin(participant.getLogin()));

		log.debug("Command finished");
		return Path.PAGE__PARTICIPANT_ACCOUNT;
	}

}
