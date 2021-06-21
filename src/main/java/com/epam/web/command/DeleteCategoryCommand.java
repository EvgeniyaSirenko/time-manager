package com.epam.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.db.AppException;
import com.epam.db.CategoryManager;

public class DeleteCategoryCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(DeleteCategoryCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		log.debug("Command starts");

		String categoryName = req.getParameter("categoryName");
		log.trace("categoryName -> " + categoryName);

		new CategoryManager().deleteCategory(categoryName);

		log.debug("Command finished");
		return Path.PAGE__ADMIN_MAIN_PAGE;
	}
}
