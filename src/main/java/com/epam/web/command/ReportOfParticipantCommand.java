package com.epam.web.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.bean.CategoryActivityStatusBean;
import com.epam.db.ActivityManager;
import com.epam.db.AppException;
import com.epam.db.ParticipantManager;
import com.epam.db.entity.Participant;

public class ReportOfParticipantCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ReportOfParticipantCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {

		log.debug("Command starts");

		String participantLogin = req.getParameter("participantLogin");
		
		Participant participant = new ParticipantManager().getParticipantByLogin(participantLogin);
		
		int participantId = participant.getId();
		
		List<CategoryActivityStatusBean> categoryActivityStatusBeansList = new ActivityManager()
				.getCategoryActivityStatusBeanOfParticipant(participantId);
		log.trace("Found in DB: categoryActivityStatusBeansList --> " + categoryActivityStatusBeansList);

		req.setAttribute("categoryActivityStatusBeansList", categoryActivityStatusBeansList);
		log.trace("Set the request attribute: categoryActivityStatusBeansList --> "
				+ categoryActivityStatusBeansList);

		log.debug("Command finished");
		return Path.PAGE__REPORT_OF_PARTICIPANT;
	}
}
