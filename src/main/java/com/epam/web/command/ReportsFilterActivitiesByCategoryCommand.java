package com.epam.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.bean.CategoryActivityParticipantBean;
import com.epam.db.ActivityManager;
import com.epam.db.CategoryManager;
import com.epam.db.entity.Category;

public class ReportsFilterActivitiesByCategoryCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ReportsFilterActivitiesByCategoryCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		log.debug("Command starts");

		String categoryName = req.getParameter("category");
		log.trace("categoryName " + categoryName);

		List<Category> categoriesList = new CategoryManager().getAllCategories();
		log.trace("Found in DB: categoriesList --> " + categoriesList);

		req.setAttribute("categoriesList", categoriesList);
		log.trace("Set the request attribute: categoriesList --> " + categoriesList);

		// get beans list
		List<CategoryActivityParticipantBean> categoryActivityParticipantBeansList = new ActivityManager()
				.getCategoryActivityParticipantBeansFilteredByCategoryName(categoryName);
		log.trace("Found in DB: categoryActivityParticipantBeansList --> " + categoryActivityParticipantBeansList);

		// put beans list to the request
		req.setAttribute("categoryActivityParticipantBeansList", categoryActivityParticipantBeansList);
		log.trace("Set the request attribute: categoryActivityParticipantBeansList --> "
				+ categoryActivityParticipantBeansList);

		log.debug("Command finished");
		return Path.PAGE__ACTIVITIES_TO_FILTER;
	}
}
