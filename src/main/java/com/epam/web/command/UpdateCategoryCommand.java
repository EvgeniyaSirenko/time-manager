package com.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;

public class UpdateCategoryCommand extends Command {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(UpdateCategoryCommand.class);
	
	@Override
	public String execute(HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		log.debug("Command starts");
		
		log.debug("Command finished");
		return Path.PAGE__UPDATE_CATEGORY;
	}

}
