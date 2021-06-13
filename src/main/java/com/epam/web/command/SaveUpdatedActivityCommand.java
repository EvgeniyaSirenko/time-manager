package com.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.ActivityManager;
import com.epam.db.entity.Activity;

public class SaveUpdatedActivityCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(SaveUpdatedActivityCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.debug("Command starts");

		Activity activityFromSession = (Activity) req.getSession().getAttribute("activity");
		log.debug("activityFromSession -> " + activityFromSession);

		boolean update = false;

		String activityName = req.getParameter("name");
		log.debug("activityName -> " + activityName);

		if (activityName != null && !activityName.isEmpty()) {
			activityFromSession.setName(activityName);
			update = true;
			log.debug("update ->" + update);
		}

		if (update == true)
			new ActivityManager().updateActivity(activityFromSession);
		log.debug("updated category ->" + new ActivityManager().getActivityByName(activityFromSession.getName()));

		log.debug("Command finished");
		return Path.PAGE__ADMIN_MAIN_PAGE;
	}

}