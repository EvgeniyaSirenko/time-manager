package com.epam.web.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.AppException;
import com.epam.db.ParticipantManager;
import com.epam.db.entity.Participant;

public class ParticipantsCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ParticipantsCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		log.debug("Command starts");

		String paramPage = req.getParameter("page");
		String paramPageSize = req.getParameter("recordsPerPage");
		
		int page = 1;
		int recordsPerPage = 7;
		try {
			page = Integer.parseInt(paramPage);
			recordsPerPage = Integer.parseInt(paramPageSize);
		} catch (NumberFormatException e) {
			log.debug(e);
		}		

		List<Participant> participantsList = new ParticipantManager().getParticipants(recordsPerPage * (page - 1), recordsPerPage);
		log.trace("Found in DB: participantsList --> " + participantsList);
		
		int nOfParticipants = new ParticipantManager().getNOfParticipants();

		int nOfPages = (int) Math.ceil(nOfParticipants / recordsPerPage);

		req.setAttribute("participantsList", participantsList);
		req.setAttribute("nOfPages", nOfPages);
		req.setAttribute("page", page);
		req.setAttribute("recordsPerPage", recordsPerPage);

		log.debug("Command finished");
		return Path.PAGE__PARTICIPANTS;
	}
}
