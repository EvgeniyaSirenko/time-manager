package com.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.CategoryManager;
import com.epam.db.ParticipantManager;
import com.epam.db.entity.Category;
import com.epam.db.entity.Participant;

public class SaveUpdatedCategoryCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(SaveUpdatedCategoryCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.debug("Command starts");

		Category categoryFromSession = (Category) req.getSession().getAttribute("category");
		System.out.println("categoryFromSession -> " + categoryFromSession);

		boolean update = false;

		// update category name
		String categoryName = req.getParameter("name");
		System.out.println("categeryName -> " + categoryName);

		if (categoryName != null && !categoryName.isEmpty()) {
			categoryFromSession.setName(categoryName);
			update = true;
			System.out.println("update ->" + update);
		}

		if (update == true)
			new CategoryManager().updateCategory(categoryFromSession);
		System.out.println(
				"updated category ->" + new CategoryManager().getCategoryByName(categoryFromSession.getName()));

		log.debug("Command finished");
		return Path.PAGE__ADMIN_MAIN_PAGE;
	}

}
