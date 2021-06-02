package com.epam.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.db.entity.Activity;

public class ActivityManager {

	private static final Logger log = LogManager.getLogger(ActivityManager.class);

	private final static String FIND_ALL_ACTIVITIES = "SELECT * FROM activity WHERE status_id=1";
			//"SELECT name, SUM(duration) FROM activity GROUP BY name";
/**
 * 
 * Extracts activity with approved status only
 * 
 **/
	public List<Activity> getActivities() {
		List<Activity> activitiesList = new ArrayList<Activity>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			ActivityMapper mapper = new ActivityMapper();
			stmt = con.createStatement();
			rs = stmt.executeQuery(FIND_ALL_ACTIVITIES);
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
            	activity.setStatusId(rs.getInt(Fields.ACTIVITY_STATUS_ID));
                return activity;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

}
