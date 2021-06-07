package com.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.ParticipantManager;
import com.epam.db.entity.Participant;

public class ParticipantUpdateAccountCommand extends Command {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ParticipantUpdateAccountCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.debug("Command starts");
		
		Participant participant = (Participant)req.getSession().getAttribute("participant");
		boolean updateParticipant = false;
		
		// update first name
		String firstName = req.getParameter("firstName");
		if (firstName != null && !firstName.isEmpty()) {
			participant.setFirstName(firstName);
			updateParticipant = true;
			System.out.println("updatedParticipant ->" + updateParticipant);

		}

		// update last name
		String lastName = req.getParameter("lastName");
		if (lastName != null && !lastName.isEmpty()) {
			participant.setLastName(lastName);
			updateParticipant = true;
			System.out.println("updatedParticipant ->" + updateParticipant);
		}
		
		// update login name
		String login = req.getParameter("login");
		if (login != null && !login.isEmpty()) {
			participant.setLogin(login);
			updateParticipant = true;
			System.out.println("updatedParticipant ->" + updateParticipant);
		}
		
		// update password name
		String password = req.getParameter("password");
		if (password != null && !password.isEmpty()) {
			participant.setPassword(password);
			updateParticipant = true;
			System.out.println("updatedParticipant ->" + updateParticipant);
		}

//		String localeToSet = request.getParameter("localeToSet");
//		if (localeToSet != null && !localeToSet.isEmpty()) {
//			HttpSession session = request.getSession();
//			Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", localeToSet);			
//			session.setAttribute("defaultLocale", localeToSet);
//			participant.setLocaleName(localeToSet);
//			updateParticipant = true;
//		}
		
		if (updateParticipant == true)
			new ParticipantManager().updateParticipant(participant);
		System.out.println("updated participant ->" + new ParticipantManager().getParticipantByLogin(participant.getLogin()));


		log.debug("Command finished");
		return Path.PAGE__PARTICIPANT_ACCOUNT;
	}

}
