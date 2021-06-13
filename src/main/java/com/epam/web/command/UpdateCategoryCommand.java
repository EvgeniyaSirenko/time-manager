package com.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.CategoryManager;
import com.epam.db.entity.Category;

public class UpdateCategoryCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(UpdateCategoryCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = req.getSession();

		String categoryName = req.getParameter("categoryName");
		log.trace("Request parameter: categoryName --> " + categoryName);

		Category category = new CategoryManager().getCategoryByName(categoryName);

		session.setAttribute("category", category);
		log.trace("Set the session attribute: category --> " + category);

		log.debug("Command finished");
		return Path.PAGE__UPDATE_CATEGORY;
	}
}