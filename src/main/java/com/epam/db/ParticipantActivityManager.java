package com.epam.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.bean.ParticipantActivityBean;
import com.epam.db.entity.Activity;
import com.epam.db.entity.Participant;
import com.epam.db.entity.ParticipantActivity;

public class ParticipantActivityManager {

	private static final Logger log = LogManager.getLogger(ParticipantActivityManager.class);

	private final static String PARTICIPANT_ADD_ACTIVITY = "INSERT INTO participant_activity VALUES(?, ?, 0, NULL)";
	
	private final static String UPDATE_ACTIVITY_STATUS = "UPDATE participant_activity SET status_id=1 WHERE participant_id=? AND activity_id=?";

	private final static String UPDATE_ACTIVITY_DURATION = "UPDATE participant_activity SET activity_duration = IFNULL(?, activity_duration+?) WHERE activity_id=? AND participant_id=?";

	/**
	 * 
	 * Adds activity to the given participant in participant_activity table.
	 * 
	 **/
	public void addActivity(ParticipantActivity participantActivity) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			addActivity(con, participantActivity);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	public ParticipantActivity addActivity(Connection con, ParticipantActivity participantActivity)
			throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(PARTICIPANT_ADD_ACTIVITY);
		pstmt.setInt(1, participantActivity.getParticipantId());
		pstmt.setInt(2, participantActivity.getActivityId());
		pstmt.executeUpdate();
		ParticipantActivity savedParticipantActivity = new ParticipantActivity();
		savedParticipantActivity.setParticipantId(participantActivity.getParticipantId());
		savedParticipantActivity.setActivityId(participantActivity.getActivityId());
		savedParticipantActivity.setStatusId(participantActivity.getStatusId());
		pstmt.close();
		return savedParticipantActivity;
	}
	
	/**
	 * 
	 * Updates status_id of the given participant_activity to 1 (approved).
	 * 
	 **/
	public void updateParticipantActivityStatusToApproved(ParticipantActivityBean participantActivityBean) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			updateParticipantActivityBean(con, participantActivityBean);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	public void updateParticipantActivityBean(Connection con, ParticipantActivityBean participantActivityBean) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(UPDATE_ACTIVITY_STATUS);
		pstmt.setInt(1, participantActivityBean.getParticipantId());
		pstmt.setInt(2, participantActivityBean.getActivityId());
		pstmt.executeUpdate();
		pstmt.close();
	}
	
	/**
	 * 
	 * Updates duration of the given participant and activity.
	 * 
	 **/
	public void updateActivityDuration(int duration, Activity activity, Participant participant) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			updateActivityDuration(con, duration, activity, participant);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	public void updateActivityDuration(Connection con, int duration, Activity activity, Participant participant) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(UPDATE_ACTIVITY_DURATION);
		pstmt.setInt(1, duration);
		pstmt.setInt(2, duration);
		pstmt.setInt(3, activity.getId());
		pstmt.setInt(4, participant.getId());
		pstmt.executeUpdate();
		pstmt.close();
		System.out.println("updatedActivity_Manager ->" + new ActivityManager().getParticipantActivityByActivityId(participant, activity.getId()));
	}

}
