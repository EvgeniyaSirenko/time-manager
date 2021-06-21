package com.epam.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.ActivityManager;
import com.epam.db.AppException;

public class DeleteActivityCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(DeleteActivityCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		log.debug("Command starts");

		String activityName = req.getParameter("activityName");
		log.trace("activityName -> " + activityName);

		new ActivityManager().deleteActivity(activityName);

		log.debug("Command finished");
		return Path.PAGE__ADMIN_MAIN_PAGE;
	}
}
