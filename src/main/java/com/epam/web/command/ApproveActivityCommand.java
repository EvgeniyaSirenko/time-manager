package com.epam.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.Path;
import com.epam.bean.ParticipantActivityBean;
import com.epam.db.ParticipantActivityManager;

public class ApproveActivityCommand extends Command {
	
	private static final long serialVersionUID = 1L;

	private static final Logger log = LogManager.getLogger(ApproveActivityCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.debug("Command starts");
		
		ParticipantActivityBean participantActivityBean = (ParticipantActivityBean)req.getSession().getAttribute("participantActivityBeansList");
//		int participantActivityAId = participantActivityBean.getActivityId();
//		int participantActivityPId = participantActivityBean.getParticipantId();
		System.out.println("list of beans -> " + participantActivityBean);

		
		String bean = req.getParameter("participantActivity");
		System.out.println("what is it? -> " + bean);
		String bean1 = req.getParameter("bean");
		System.out.println("what is it? -> " + bean1);
		

//		System.out.println("participantActivityBean ->" + participantActivityBean); //
//		System.out.println("what is it? ->" + participantActivityAId + " " + participantActivityPId); //
//		
//		// update status to approved (id = 1)
//		new ParticipantActivityManager().updateParticipantActivityStatusToApproved(participantActivityBean);
//		System.out.println("statusId after update ->" + participantActivityBean.getStatusId());

		log.debug("Command finished");
		return Path.PAGE__ACTIVITIES_TO_APPROVE;
	}
}
