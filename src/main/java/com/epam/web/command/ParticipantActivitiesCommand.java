package com.epam.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.bean.ParticipantActivityBean;
import com.epam.db.ActivityManager;
import com.epam.db.entity.Activity;
import com.epam.db.entity.Participant;

public class ParticipantActivitiesCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ParticipantActivitiesCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		log.debug("Command starts");

		Participant participant = (Participant)req.getSession().getAttribute("participant");
		
		// get activities list
		List<ParticipantActivityBean> participantActivityBeansList = new ActivityManager().getApprovedActivities(participant);
		log.trace("Found in DB: activitiesList --> " + participantActivityBeansList);
		System.out.println("participantActivityBeansList -> " + participantActivityBeansList);


		// put activities list to the request
		req.setAttribute("participantActivityBeansList", participantActivityBeansList);
		log.trace("Set the request attribute: participantActivityBeansList --> " + participantActivityBeansList);
		System.out.println("Set the request attribute: participantActivityBeansList --> " + participantActivityBeansList);

		log.debug("Command finished");
		return Path.PAGE__PARTICIPANT_ACTIVITIES;
	}
}
