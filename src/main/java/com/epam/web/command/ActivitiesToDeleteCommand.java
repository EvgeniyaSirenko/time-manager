package com.epam.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.bean.ParticipantActivityBean;
import com.epam.db.ActivityManager;

public class ActivitiesToDeleteCommand extends Command {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ActivitiesToDeleteCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		log.debug("Command starts");

		List<ParticipantActivityBean> participantActivityBeansList = new ActivityManager().getActivitiesToDelete();
		log.trace("Found in DB: participantActivityBeansList --> " + participantActivityBeansList);

		req.setAttribute("participantActivityBeansList", participantActivityBeansList);
		log.trace("Set attribute: participantActivityBeansList --> " + participantActivityBeansList);

		log.debug("Command finished");
		return Path.PAGE__ACTIVITIES_TO_DELELE;
	}

}
