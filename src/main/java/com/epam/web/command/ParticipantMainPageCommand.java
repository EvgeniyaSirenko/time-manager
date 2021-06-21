package com.epam.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.AppException;

public class ParticipantMainPageCommand extends Command {
	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ParticipantMainPageCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		log.debug("Command starts");

		log.debug("Command finished");
		return Path.PAGE__PARTICIPANT_MAIN_PAGE;
	}

}
