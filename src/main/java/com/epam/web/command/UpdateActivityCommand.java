package com.epam.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.ActivityManager;
import com.epam.db.AppException;
import com.epam.db.entity.Activity;

public class UpdateActivityCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(UpdateActivityCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		log.debug("Command starts");

		HttpSession session = req.getSession();

		String activityName = req.getParameter("activityName");
		log.trace("Request parameter: activityName --> " + activityName);

		Activity activity = new ActivityManager().getActivityByName(activityName);

		session.setAttribute("activity", activity);
		log.trace("Set the session attribute: activity --> " + activity);

		log.debug("Command finished");
		return Path.PAGE__UPDATE_ACTIVITY;
	}
}