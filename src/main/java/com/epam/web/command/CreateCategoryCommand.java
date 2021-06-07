package com.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.entity.Category;
import com.epam.db.CategoryManager;

public class CreateCategoryCommand extends Command {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(CreateCategoryCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.debug("Command starts");

		// obtain category name from the request
		String name = req.getParameter("name");
		log.trace("Request parameter: name --> " + name);
		System.out.println("Request parameter: name --> " + name);

		String errorMessage = null;
		String forward = Path.PAGE__ERROR_PAGE;
		
		Category category = new CategoryManager().getCategoryByName(name);
		log.trace("Found in DB: participant --> " + category);
		System.out.println("Found in DB: participant --> " + category);

		
		if (category != null) {
			errorMessage = "Such category already exists";
			req.getSession().setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return forward;
		} else {
			
			Category newCategory = new Category();
			newCategory.setName(req.getParameter("name"));

			//send data to DB
			new CategoryManager().createCategory(newCategory);
			System.out.println("Created new " + new CategoryManager().getCategoryByName(newCategory.getName()));
			
			forward = Path.PAGE__ADMIN_MAIN_PAGE;
		}
		
		log.debug("Command finished");
		return forward;
	}

}
