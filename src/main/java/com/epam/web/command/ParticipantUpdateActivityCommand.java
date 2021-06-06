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
import com.epam.db.ParticipantActivityManager;
import com.epam.db.entity.Activity;
import com.epam.db.entity.Participant;
import com.epam.db.entity.ParticipantActivity;

public class ParticipantUpdateActivityCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ParticipantUpdateActivityCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		log.debug("Command starts");

		Participant participant = (Participant)req.getSession().getAttribute("participant");
		System.out.println("Found in session: participant --> " + participant.toString());
		
		String errorMessage = null;
		String forward = Path.PAGE__ERROR_PAGE;
		
		String activityId = req.getParameter("activity");
		System.out.println("Found parametr: activityId --> " + activityId);
		
		int selectedActivityId = 0;
		try {
			selectedActivityId = Integer.parseInt(activityId);
		} catch (NumberFormatException e){
			System.out.println("error" + e);
			
		}
		
		Activity activity = new ActivityManager().getParticipantActivityByActivityId(participant, selectedActivityId);
		log.trace("Found in DB: participant --> " + activity);
		System.out.println("Found in DB: activity --> " + activity);
		
		if (activity.getId() != 0) {
			
			errorMessage = "You have such activity, choose another one";
			req.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return forward;
			
		} else {
			
			//send data to DB
			ParticipantActivity newParticipantActivity = new ParticipantActivity();
			newParticipantActivity.setParticipantId(participant.getId());
			newParticipantActivity.setActivityId(selectedActivityId);
			newParticipantActivity.setStatusId(0);
			
			new ParticipantActivityManager().addActivity(newParticipantActivity);
			System.out.println("Created new " + newParticipantActivity.toString());
			
			forward = Path.PAGE__PARTICIPANT_MAIN_PAGE;

	}

		log.debug("Command finished");
		return forward;
	}

}
