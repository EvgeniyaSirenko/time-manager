package com.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.ActivityManager;
import com.epam.db.ParticipantActivityManager;
import com.epam.db.entity.Activity;
import com.epam.db.entity.Participant;


public class ParticipantAddActivityDurationCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ParticipantAddActivityDurationCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.debug("Command starts");

		Participant participant = (Participant)req.getSession().getAttribute("participant");
		System.out.println("participant -> " + participant);
		
		String activityId = req.getParameter("activityId"); 
		System.out.println("activityId --> " + activityId); 
		int selectedActivityId = 0;
		try {
			selectedActivityId = Integer.parseInt(activityId);
		} catch (NumberFormatException e){
			System.out.println("error" + e);
			
		}
		
		Activity activity = new ActivityManager().getParticipantActivityByActivityId(participant, selectedActivityId);
		log.trace("Found in DB: participant --> " + activity);
		System.out.println("Found in DB: activity --> " + activity);
		
		String inDuration = req.getParameter("duration");
		System.out.println("Found parametr: inDuration --> " + inDuration);
		
		int duration = 0;
		try {
			duration = Integer.parseInt(inDuration);
		} catch (NumberFormatException e) {
			log.debug(e);
		}
		System.out.println("inDuration parse --> " + duration); 

		
		// update duration
		new ParticipantActivityManager().updateActivityDuration(duration, activity, participant);

		log.debug("Command finished");
		return Path.PAGE__PARTICIPANT_MAIN_PAGE;
	}

}