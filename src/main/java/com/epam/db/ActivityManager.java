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

	private final static String FIND_ALL_APPROVED_ACTIVITIES_BY_PARTICIPANT = "SELECT * FROM activity "
			+ "WHERE id IN (SELECT activity_id FROM participant_activity WHERE participant_id=? AND status_id=1)";

	private final static String PARTICIPANT_ADD_ACTIVITY = "INSERT INTO participant_activity VALUES"
			+ "((SELECT id FROM participant WHERE login=?), (SELECT id FROM activity WHERE name=?), 0)";

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
			pstmt = con.prepareStatement(FIND_ALL_APPROVED_ACTIVITIES_BY_PARTICIPANT);
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
     * Returns orders of the given user and status
     *
     * @param user
     *            User entity.
     * @param statusId
     *            Status identifier.
     * @return List of order entities.
     */
    public List<Activity> addActivity(Participant participant, int statusId) {
        List<Activity> activityList = new ArrayList<Activity>();
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        try {
//            con = DBManager.getInstance().getConnection();
//            OrderMapper mapper = new OrderMapper();
//            pstmt = con.prepareStatement(SQL__FIND_ORDERS_BY_STATUS_AND_USER);
//            pstmt.setInt(1, statusId);
//            pstmt.setLong(2, user.getId());
//            rs = pstmt.executeQuery();
//            while (rs.next())
//                ordersList.add(mapper.mapRow(rs));
//        } catch (SQLException ex) {
//            DBManager.getInstance().rollbackAndClose(con);
//            ex.printStackTrace();
//        } finally {
//            DBManager.getInstance().commitAndClose(con);
//        }
        return activityList;
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
