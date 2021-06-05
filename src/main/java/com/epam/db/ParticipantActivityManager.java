package com.epam.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.db.entity.ParticipantActivity;

public class ParticipantActivityManager {

	private static final Logger log = LogManager.getLogger(ParticipantActivityManager.class);

	private final static String PARTICIPANT_ADD_ACTIVITY = "INSERT INTO participant_activity VALUES(?, ?, 0)";

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

}
