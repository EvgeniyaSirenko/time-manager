package com.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.bean.CategoryActivityBean;
import com.epam.db.ActivityManager;
import com.epam.db.entity.Activity;

public class SaveUpdatedActivityCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(SaveUpdatedActivityCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.debug("Command starts");
		
		Activity activityFromSession = (Activity) req.getSession().getAttribute("activity");
		System.out.println("activityFromSession -> " + activityFromSession);

		boolean update = false;

		// update activity name
		String activityName = req.getParameter("name");
		System.out.println("activityName -> " + activityName);

		if (activityName != null && !activityName.isEmpty()) {
			activityFromSession.setName(activityName);
			update = true;
			System.out.println("update ->" + update);
		}

		if (update == true)
			new ActivityManager().updateActivity(activityFromSession);
		System.out.println(
				"updated category ->" + new ActivityManager().getActivityByName(activityFromSession.getName()));

//		CategoryActivityBean beanFromSession = (CategoryActivityBean) req.getSession().getAttribute("categoryActivityBean");
//		System.out.println("beanFromSession -> " + beanFromSession);
//
//		boolean update = false;
//	
//		String categoryName = req.getParameter("category");
//		System.out.println("Found parametr: category --> " + categoryName);
//		beanFromSession.setCategoryName(categoryName);
//
//		String oldActivityName = req.getParameter("oldActivityName");
//		System.out.println("oldActivityName -> " + oldActivityName);
//		
//		String newActivityName = req.getParameter("activity");
//		System.out.println("newActivityName -> " + newActivityName);
//		if (newActivityName != null && !newActivityName.isEmpty()) {
//			beanFromSession.setActivityName(newActivityName);
//			update = true;
//			System.out.println("update ->" + update);
//		}
//		
//		if (update == true)
//			new ActivityManager().updateCategoryActivityBean(beanFromSession, oldActivityName);
//		System.out.println(
//				"updated beanFromSession ->" + new ActivityManager().getActivityByName(beanFromSession.getActivityName()));

		log.debug("Command finished");
		return Path.PAGE__ADMIN_MAIN_PAGE;
	}

}