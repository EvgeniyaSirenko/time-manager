package com.epam.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.ActivityManager;
import com.epam.db.AppException;
import com.epam.db.ParticipantActivityManager;
import com.epam.db.ParticipantManager;
import com.epam.db.entity.Activity;
import com.epam.db.entity.Participant;

public class AdminRejectDeleteParticipantsActivityCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(AdminRejectDeleteParticipantsActivityCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		log.debug("Command starts");

		String activityName = req.getParameter("activityName");
		log.trace("activityName -> " + activityName);

		String participantLogin = req.getParameter("participantLogin");
		log.trace("participantLogin -> " + participantLogin);

		Activity activity = new ActivityManager().getActivityByName(activityName);
		Participant participant = new ParticipantManager().getParticipantByLogin(participantLogin);

		new ParticipantActivityManager().updateParticipantActivityStatusToApproved(participant, activity);

		log.debug("Command finished");
		return Path.PAGE__ADMIN_MAIN_PAGE;
	}
}
