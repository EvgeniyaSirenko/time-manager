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

public class ActivitiesToDeleteCommand extends Command {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ActivitiesToDeleteCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		log.debug("Command starts");
		
		// get activities list
		List<Activity> activitiesList = new ActivityManager().getActivitiesToDelete();
		log.trace("Found in DB: activitiesList --> " + activitiesList);

		// put activities list to the request
		req.setAttribute("activitiesList", activitiesList);
		log.trace("Set the request attribute: activities --> " + activitiesList);

		log.debug("Command finished");
		return Path.PAGE__ACTIVITIES_TO_DELELE;
	}

}
