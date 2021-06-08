package com.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.bean.ParticipantActivityBean;
import com.epam.db.ActivityManager;
import com.epam.db.ParticipantActivityManager;
import com.epam.db.ParticipantManager;
import com.epam.db.entity.Activity;
import com.epam.db.entity.Participant;

public class ApproveActivityCommand extends Command {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ApproveActivityCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.debug("Command starts");

		String activityName = req.getParameter("activityName");
		System.out.println("activityName -> " + activityName);
		
		String participantLogin = req.getParameter("participantLogin");
		System.out.println("participantLogin -> " + participantLogin);		
		
		Activity activity = new ActivityManager().getActivityByName(activityName);
		Participant participant = new ParticipantManager().getParticipantByLogin(participantLogin);
		
		// update status to approved (id = 1)
		new ParticipantActivityManager().updateParticipantActivityStatusToApproved(participant, activity);

		log.debug("Command finished");
		return Path.PAGE__ADMIN_MAIN_PAGE;
	}
}
