package com.epam.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.taglibs.standard.lang.jstl.test.PageContextImpl;

import com.epam.Path;
import com.epam.db.ActivityManager;
import com.epam.db.ParticipantActivityManager;
import com.epam.db.ParticipantManager;
import com.epam.db.entity.Activity;
import com.epam.db.entity.Participant;
import com.epam.db.entity.ParticipantActivity;

public class ParticipantAddActivityCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ParticipantAddActivityCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		log.debug("Command starts");

		Participant participant = (Participant)req.getSession().getAttribute("participant");
		System.out.println("Found in session: participant --> " + participant.toString());

				
		// get activities list
		List<Activity> activitiesList = new ActivityManager().getAvailableActivities();
		log.trace("Found in DB: activitiesList --> " + activitiesList);
		System.out.println("Found in DB: activitiesList --> " + activitiesList);

		// put activities list to the request
		req.setAttribute("activitiesList", activitiesList);
		log.trace("Set the request attribute: activities --> " + activitiesList);
		
		String activity = req.getParameter("activity");
		System.out.println("Found parametr: activityId --> " + activity);
		
		int selectedActivity = 0;
		try {
			selectedActivity = Integer.parseInt(activity);
		} catch (NumberFormatException e){
			System.out.println("error" + e);
			
		}

		//send data to DB
		ParticipantActivity newParticipantActivity = new ParticipantActivity();
		newParticipantActivity.setParticipantId(participant.getId());
		newParticipantActivity.setActivityId(selectedActivity);
		newParticipantActivity.setStatusId(0);
		
		new ParticipantActivityManager().addActivity(newParticipantActivity);
		System.out.println("Created new " + newParticipantActivity.toString());

		log.debug("Command finished");
		return Path.COMMAND__PARTICIPANT_ADD_ACTIVITIES;
	}

}
