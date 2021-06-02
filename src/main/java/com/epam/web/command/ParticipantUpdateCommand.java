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

public class ParticipantUpdateCommand extends Command {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ParticipantUpdateCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.debug("Command starts");
		
		User user = (User)request.getSession().getAttribute("user");
		boolean updateUser = false;
		
		// update first name
		String firstName = request.getParameter("firstName");
		if (firstName != null && !firstName.isEmpty()) {
			user.setFirstName(firstName);
			updateUser = true;
		}

		// update last name
		String lastName = request.getParameter("lastName");
		if (lastName != null && !lastName.isEmpty()) {
			user.setLastName(lastName);
			updateUser = true;
		}

//		String localeToSet = request.getParameter("localeToSet");
//		if (localeToSet != null && !localeToSet.isEmpty()) {
//			HttpSession session = request.getSession();
//			Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", localeToSet);			
//			session.setAttribute("defaultLocale", localeToSet);
//			user.setLocaleName(localeToSet);
//			updateUser = true;
//		}
		
		if (updateUser == true)
			new ParticipantManager().;


		log.debug("Command finished");
		return Path.PAGE__PARTICIPANT_ACCOUNT;
	}

}
