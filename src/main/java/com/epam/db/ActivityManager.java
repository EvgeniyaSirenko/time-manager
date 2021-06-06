package com.epam.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.db.entity.Activity;
import com.epam.db.entity.Participant;

public class ActivityManager {

	private static final Logger log = LogManager.getLogger(ActivityManager.class);

	private final static String FIND_ALL_REQUESTED_ACTIVITIES = "SELECT * FROM activity "
			+ "WHERE id IN (SELECT activity_id FROM participant_activity WHERE status_id=0)";

	private final static String FIND_ALL_ACTIVITIES_TO_DELETE = "SELECT * FROM activity "
			+ "WHERE id IN (SELECT activity_id FROM participant_activity WHERE status_id=2)";

	private final static String FIND_ALL_ACTIVITIES_OF_PARTICIPANT = "SELECT * FROM activity "
			+ "WHERE id IN (SELECT activity_id FROM participant_activity WHERE participant_id=? AND status_id!=0)";

	private final static String FIND_ACTIVITY_OF_PARTICIPANT_BY_ACTIVITY_ID = "SELECT * FROM activity "
			+ "WHERE id IN (SELECT activity_id FROM participant_activity WHERE activity_id=? AND participant_id=?)";

	private final static String FIND_ALL_AVAILABLE_ACTIVITIES = "SELECT * FROM activity";

	/**
	 * 
	 * Returns all activities.
	 * 
	 **/
	public List<Activity> getAvailableActivities() {
		List<Activity> activitiesList = new ArrayList<Activity>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			ActivityMapper mapper = new ActivityMapper();
			stmt = con.createStatement();
			rs = stmt.executeQuery(FIND_ALL_AVAILABLE_ACTIVITIES);
			while (rs.next())
				activitiesList.add(mapper.mapRow(rs));
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return activitiesList;
	}

	/**
	 * 
	 * Returns all activities with requested status only, for admin approvement
	 * 
	 **/
	public List<Activity> getRequestedActivities() {
		List<Activity> activitiesList = new ArrayList<Activity>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			ActivityMapper mapper = new ActivityMapper();
			stmt = con.createStatement();
			rs = stmt.executeQuery(FIND_ALL_REQUESTED_ACTIVITIES);
			while (rs.next())
				activitiesList.add(mapper.mapRow(rs));
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		System.out.println("activitiesList -> " + activitiesList.toString());
		return activitiesList;
	}

	/**
	 * 
	 * Returns all activities with status to delete only, for admin approvement
	 * 
	 **/
	public List<Activity> getActivitiesToDelete() {
		List<Activity> activitiesList = new ArrayList<Activity>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			ActivityMapper mapper = new ActivityMapper();
			stmt = con.createStatement();
			rs = stmt.executeQuery(FIND_ALL_ACTIVITIES_TO_DELETE);
			while (rs.next())
				activitiesList.add(mapper.mapRow(rs));
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		System.out.println("activitiesList -> " + activitiesList.toString());
		return activitiesList;
	}

	/**
	 * 
	 * Returns activities of the given participant with approved status only
	 * 
	 **/
	public List<Activity> getApprovedActivities(Participant participant) {
		List<Activity> activitiesList = new ArrayList<Activity>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			ActivityMapper mapper = new ActivityMapper();
			pstmt = con.prepareStatement(FIND_ALL_ACTIVITIES_OF_PARTICIPANT);
			pstmt.setInt(1, participant.getId());
			rs = pstmt.executeQuery();
			while (rs.next())
				activitiesList.add(mapper.mapRow(rs));
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		System.out.println("activitiesList -> " + activitiesList.toString());
		return activitiesList;
	}

	/**
	 * 
	 * Returns activities of the given participant with approved status only
	 * 
	 **/
	public Activity getParticipantActivityByActivityId(Participant participant, int activityId) {
		Activity activity = new Activity();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			ActivityMapper mapper = new ActivityMapper();
			pstmt = con.prepareStatement(FIND_ACTIVITY_OF_PARTICIPANT_BY_ACTIVITY_ID);
			pstmt.setInt(1, activityId);
			pstmt.setInt(2, participant.getId());
			rs = pstmt.executeQuery();
			if (rs.next())
				activity = mapper.mapRow(rs);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		System.out.println("activityManager found activity --> " + activity);
		return activity;
	}

	/**
	 * Extracts activity from the result set row.
	 */
	private static class ActivityMapper implements EntityMapper<Activity> {

		@Override
		public Activity mapRow(ResultSet rs) {
			try {
				Activity activity = new Activity();
				activity.setId(rs.getInt(Fields.ENTITY__ID));
				activity.setName(rs.getString(Fields.ENTITY__NAME));
				activity.setDuration(rs.getInt(Fields.ACTIVITY_DURATION));
				activity.setCategoryId(rs.getInt(Fields.ACTIVITY_CATEGORY_ID));
				return activity;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
	}

}
