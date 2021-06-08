package com.epam.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.ActivityManager;
import com.epam.db.CategoryManager;
import com.epam.db.entity.Activity;
import com.epam.db.entity.Category;

public class UpdateActivityCommand extends Command {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(UpdateActivityCommand.class);
	
	@Override
	public String execute(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		log.debug("Command starts");
		
		HttpSession session = req.getSession();
		
		// get categories list TODO
		List<Category> categoriesList = new CategoryManager().getAllCategories();
		log.trace("Found in DB: categoriesList --> " + categoriesList);
		System.out.println("Found in DB: categoriesList --> " + categoriesList);
		req.setAttribute("categoriesList", categoriesList);
		log.trace("Set the request attribute: categoriesList --> " + categoriesList);

		// obtain not updated activity name from the request
		String activityName = req.getParameter("activityName");
		log.trace("Request parameter: activityName --> " + activityName);
		
		Activity activity = new ActivityManager().getActivityByName(activityName);
		
		session.setAttribute("activity", activity);
		log.trace("Set the session attribute: activity --> " + activity);
		
		log.debug("Command finished");
		return Path.PAGE__UPDATE_ACTIVITY;
	}
}