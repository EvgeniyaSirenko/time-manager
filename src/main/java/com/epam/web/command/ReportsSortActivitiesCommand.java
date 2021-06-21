package com.epam.web.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.bean.CategoryActivityParticipantBean;
import com.epam.db.ActivityManager;
import com.epam.db.AppException;

public class ReportsSortActivitiesCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ReportsSortActivitiesCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {

		log.debug("Command starts");

		List<CategoryActivityParticipantBean> categoryActivityParticipantBeansList = new ActivityManager()
				.getAllCategoryActivityParticipantBeansActivityOrder();
		log.trace("Found in DB: categoryActivityParticipantBeansList --> " + categoryActivityParticipantBeansList);

		req.setAttribute("categoryActivityParticipantBeansList", categoryActivityParticipantBeansList);
		log.trace("Set the request attribute: categoryActivityParticipantBeansList --> "
				+ categoryActivityParticipantBeansList);

		log.debug("Command finished");
		return Path.PAGE__ACTIVITIES_TO_SORT;
	}
}
