package com.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.ActivityManager;
import com.epam.db.CategoryManager;
import com.epam.db.entity.Activity;
import com.epam.db.entity.Category;

public class AdminSaveCreatedActivityCommand extends Command {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(AdminSaveCreatedActivityCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		log.debug("Command starts");

		// obtain category name from the request
		String name = req.getParameter("name");
		log.trace("Request parameter: name --> " + name);
		System.out.println("Request parameter: name --> " + name);

		String errorMessage = null;
		String forward = Path.PAGE__ERROR_PAGE;
		
		Activity activity = new ActivityManager().getActivityByName(name);
		log.trace("Found in DB: activity --> " + activity);
		System.out.println("Found in DB: activity --> " + activity);
		
		String categoryId = req.getParameter("category");
		System.out.println("Found parametr: categoryId --> " + categoryId);
		
		int selectedCategoryId = 0;
		try {
			selectedCategoryId = Integer.parseInt(categoryId);
		} catch (NumberFormatException e){
			System.out.println("error" + e);		
		}

		if (activity != null) {
			errorMessage = "Such activity already exists";
			req.getSession().setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return forward;
		} else {		
			Activity newActivity = new Activity();
			newActivity.setName(req.getParameter("name"));
			newActivity.setCategoryId(selectedCategoryId);

			//send data to DB
			new ActivityManager().createActivity(newActivity);
			System.out.println("Created new " + new ActivityManager().getActivityByName(newActivity.getName()));
			
			forward = Path.PAGE__ADMIN_MAIN_PAGE;
		}
		
		log.debug("Command finished");
		return forward;
	}

}
