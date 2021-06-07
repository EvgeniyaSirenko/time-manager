package com.epam.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.CategoryManager;
import com.epam.db.entity.Category;

public class AdminCreateNewActivityCommand extends Command {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ParticipantChooseNewActivityCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		log.debug("Command starts");
				
		// get category list
		List<Category> categoriesList = new CategoryManager().getAllCategories();
		log.trace("Found in DB: categoriesList --> " + categoriesList);
		System.out.println("Found in DB: categoriesList --> " + categoriesList);

		// put category list to the request
		req.setAttribute("categoriesList", categoriesList);
		log.trace("Set the request attribute: categoriesList --> " + categoriesList);

		log.debug("Command finished");
		return Path.PAGE__CREATE_ACTIVITY;
	}

}
