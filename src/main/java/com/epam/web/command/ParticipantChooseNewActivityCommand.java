package com.epam.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.ActivityManager;
import com.epam.db.entity.Activity;
import com.epam.db.entity.Participant;

public class ParticipantChooseNewActivityCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ParticipantChooseNewActivityCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		log.debug("Command starts");

		Participant participant = (Participant) req.getSession().getAttribute("participant");
		log.trace("Found in session: participant --> " + participant.toString());

		List<Activity> activitiesList = new ActivityManager().getAvailableActivities();
		log.trace("Found in DB: activitiesList --> " + activitiesList);

		req.setAttribute("activitiesList", activitiesList);
		log.trace("Set the request attribute: activities --> " + activitiesList);

		log.debug("Command finished");
		return Path.PAGE__PARTICIPANT_ADD_ACTIVITY;
	}

}
