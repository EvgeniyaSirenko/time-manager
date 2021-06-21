package com.epam.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.db.entity.Activity;
import com.epam.db.entity.Participant;
import com.epam.db.entity.ParticipantActivity;

public class ParticipantActivityManager {

	private static final Logger log = LogManager.getLogger(ParticipantActivityManager.class);

	private final static String PARTICIPANT_ADD_ACTIVITY = "INSERT INTO participant_activity VALUES(?, ?, 0, NULL)";

	private final static String DELETE_PARTICIPANT_ACTIVITY = "DELETE FROM participant_activity WHERE participant_id=? AND activity_id=?";

	private final static String UPDATE_ACTIVITY_STATUS_TO_APPROVE = "UPDATE participant_activity SET status_id=1 WHERE participant_id=? AND activity_id=?";

	private final static String UPDATE_ACTIVITY_STATUS_TO_DELETE = "UPDATE participant_activity SET status_id=2 WHERE participant_id=? AND activity_id=?";

	private final static String UPDATE_ACTIVITY_DURATION = "UPDATE participant_activity SET activity_duration = CASE WHEN activity_duration IS NOT NULL THEN activity_duration+? ELSE ? END WHERE activity_id=? AND participant_id=?";

	/**
	 * 
	 * Adds activity to the given participant in participant_activity table.
	 * 
	 * @throws DBException
	 * 
	 **/
	public void addActivity(ParticipantActivity participantActivity) throws DBException {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			addActivity(con, participantActivity);
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot add activity ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot add activity", ex);
		} finally {
			DBManager.getInstance().close(con);
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
		DBManager.getInstance().closeStmt(pstmt);
		return savedParticipantActivity;
	}

	/**
	 * 
	 * Updates status_id of the given participant_activity to 1 (approved).
	 * 
	 * @throws DBException
	 * 
	 **/
	public void deleteParticipantActivity(Participant participant, Activity activity) throws DBException {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			deleteParticipantActivity(con, participant, activity);
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot delete activity ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot delete activity", ex);
		} finally {
			DBManager.getInstance().close(con);
		}
	}

	public void deleteParticipantActivity(Connection con, Participant participant, Activity activity)
			throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(DELETE_PARTICIPANT_ACTIVITY);
		pstmt.setInt(1, participant.getId());
		pstmt.setInt(2, activity.getId());
		pstmt.executeUpdate();
		DBManager.getInstance().closeStmt(pstmt);
	}

	/**
	 * 
	 * Updates status_id of the given participant_activity to 1 (approved).
	 * 
	 * @throws DBException
	 * 
	 **/
	public void updateParticipantActivityStatusToApproved(Participant participant, Activity activity)
			throws DBException {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			updateParticipantActivityToApprove(con, participant, activity);
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot update activity status ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot update activity status", ex);
		} finally {
			DBManager.getInstance().close(con);
		}
	}

	public void updateParticipantActivityToApprove(Connection con, Participant participant, Activity activity)
			throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(UPDATE_ACTIVITY_STATUS_TO_APPROVE);
		pstmt.setInt(1, participant.getId());
		pstmt.setInt(2, activity.getId());
		pstmt.executeUpdate();
		DBManager.getInstance().closeStmt(pstmt);
	}

	/**
	 * 
	 * Updates status_id of the given participant_activity to 2 (delete).
	 * 
	 * @throws DBException
	 * 
	 **/
	public void updateParticipantActivityStatusToDelete(Participant participant, Activity activity) throws DBException {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			updateParticipantActivityToDelete(con, participant, activity);
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot update activity status ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot update activity status", ex);
		} finally {
			DBManager.getInstance().close(con);
		}
	}

	public void updateParticipantActivityToDelete(Connection con, Participant participant, Activity activity)
			throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(UPDATE_ACTIVITY_STATUS_TO_DELETE);
		pstmt.setInt(1, participant.getId());
		pstmt.setInt(2, activity.getId());
		pstmt.executeUpdate();
		DBManager.getInstance().closeStmt(pstmt);
	}

	/**
	 * 
	 * Updates duration of the given participant and activity.
	 * 
	 * @throws DBException
	 * 
	 **/
	public void updateActivityDuration(int duration, Activity activity, Participant participant) throws DBException {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			updateActivityDuration(con, duration, activity, participant);
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot update duration ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot update duration", ex);
		} finally {
			DBManager.getInstance().close(con);
		}
	}

	public void updateActivityDuration(Connection con, int duration, Activity activity, Participant participant)
			throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(UPDATE_ACTIVITY_DURATION);
		pstmt.setInt(1, duration);
		pstmt.setInt(2, duration);
		pstmt.setInt(3, activity.getId());
		pstmt.setInt(4, participant.getId());
		pstmt.executeUpdate();
		DBManager.getInstance().closeStmt(pstmt);
	}

}
