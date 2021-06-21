package com.epam.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.AppException;
import com.epam.db.ParticipantManager;

public class DeleteParticipantCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(DeleteParticipantCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		log.debug("Command starts");

		String participantLogin = req.getParameter("participantLogin");
		log.trace("participantLogin -> " + participantLogin);

		new ParticipantManager().deleteParticipant(participantLogin);

		log.debug("Command finished");
		return Path.PAGE__ADMIN_MAIN_PAGE;
	}
}
