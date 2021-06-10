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
import com.epam.bean.CategoryActivityBean;
import com.epam.db.ActivityManager;
import com.epam.db.entity.Activity;

public class ActivitiesCommand extends Command {


	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ActivitiesCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		log.debug("Command starts");
		
		
		// get activities list
		List<Activity> activitiesList = new ActivityManager().getAvailableActivities();
		log.trace("Found in DB: activitiesList --> " + activitiesList);

		// put activities list to the request
		req.setAttribute("activitiesList", activitiesList);
		log.trace("Set the request attribute: activitiesList --> " + activitiesList);
				
//		// get activities list
//		List<CategoryActivityBean> categoryActivityBeansList = new ActivityManager().getAllCategoryActivityBeans();
//		log.trace("Found in DB: activitiesList --> " + categoryActivityBeansList);
//
//		// put activities list to the request
//		req.setAttribute("categoryActivityBeansList", categoryActivityBeansList);
//		log.trace("Set the request attribute: categoryActivityBeansList --> " + categoryActivityBeansList);

		log.debug("Command finished");
		return Path.PAGE__ACTIVITIES;
	}
}

