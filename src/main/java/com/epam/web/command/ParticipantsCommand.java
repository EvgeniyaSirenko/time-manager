package com.epam.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.ParticipantManager;
import com.epam.db.entity.Participant;

public class ParticipantsCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ParticipantsCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		log.debug("Command starts");
		
		// get categories list
		List<Participant> participantsList = new ParticipantManager().getAllParticipants();
		log.trace("Found in DB: participantsList --> " + participantsList);
		System.out.println("participantsList" + participantsList);

		// put categories list to the request
		req.setAttribute("participantsList", participantsList);
		log.trace("Set the request attribute: participantsList --> " + participantsList);

		log.debug("Command finished");
		return Path.PAGE__PARTICIPANTS;
	}
}
