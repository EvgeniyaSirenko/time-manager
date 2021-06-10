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

import com.epam.bean.CategoryActivityBean;
import com.epam.bean.ParticipantActivityBean;
import com.epam.db.entity.Activity;
import com.epam.db.entity.Participant;

public class ActivityManager {

	private static final Logger log = LogManager.getLogger(ActivityManager.class);

	private static final String DELETE_ACTIVITY = "DELETE FROM activity WHERE name=?";
	private static final String UPDATE_ACTIVITY = "UPDATE activity SET name=?, category_id=? WHERE id =?";
	private static final String CREATE_ACTIVITY = "INSERT INTO activity (name, category_id) VALUES (?, ?)";
	private static final String FIND_ACTIVITY_BY_NAME = "SELECT * FROM activity WHERE name=?";
	private static final String UPDATE_CATEGORY_ACTIVITY_BEAN = "UPDATE activity a SET a.name=?, a.category_id=? "
			+ "WHERE a.name=? AND a.id>0";
	private final static String FIND_CATEGORY_ACTIVITY_BEAN_BY_ACTIVITY_NAME = "SELECT c.name AS category, a.name AS activity_name  "
			+ "FROM category c,activity a WHERE a.category_id = c.id AND a.name=?";
	private final static String FIND_ALL_CATEGORY_ACTIVITY_BEANS = "SELECT c.name AS category, a.name AS activity_name, a.category_id "
			+ "FROM category c,activity a WHERE a.category_id = c.id";
	private final static String FIND_ALL_REQUESTED_ACTIVITIES = "SELECT pa.participant_id, p.login, pa.activity_id, a.name, a.category_id, pa.activity_duration, pa.status_id "
			+ "FROM activity a, participant p, participant_activity pa "
			+ "WHERE p.id=pa.participant_id AND a.id=pa.activity_id AND pa.status_id=0";
	private final static String FIND_ALL_ACTIVITIES_TO_DELETE = "SELECT pa.participant_id, p.login, pa.activity_id, a.name, a.category_id, pa.activity_duration, pa.status_id "
			+ "FROM activity a, participant p, participant_activity pa "
			+ "WHERE p.id=pa.participant_id AND a.id=pa.activity_id AND pa.status_id=2";
	private final static String FIND_ALL_ACTIVITIES_OF_PARTICIPANT = "SELECT pa.participant_id, p.login, pa.activity_id, a.name, a.category_id, pa.activity_duration, pa.status_id "
			+ "FROM activity a, participant p, participant_activity pa "
			+ "WHERE p.id=pa.participant_id AND a.id=pa.activity_id AND p.id=? AND status_id!=0";
	private final static String FIND_ACTIVITY_OF_PARTICIPANT_BY_ACTIVITY_ID = "SELECT * FROM activity "
			+ "WHERE id IN (SELECT activity_id FROM participant_activity WHERE activity_id=? AND participant_id=?)";
	private final static String FIND_ALL_AVAILABLE_ACTIVITIES = "SELECT * FROM activity";

	/**
	 * Deletes activity by name.
	 */
	public void deleteActivity(String activityName) {
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(DELETE_ACTIVITY);
			pstmt.setString(1, activityName);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	public void updateActivity(Activity activity) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			updateActivity(con, activity);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	public void updateActivity(Connection con, Activity activity) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(UPDATE_ACTIVITY);
		pstmt.setString(1, activity.getName());
		pstmt.setInt(2, activity.getCategoryId());
		pstmt.setInt(3, activity.getId());
		pstmt.executeUpdate();
		pstmt.close();
	}

	/**
	 * Creates activity.
	 */
	public void createActivity(Activity activity) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			createActivity(con, activity);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	public Activity createActivity(Connection con, Activity activity) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(CREATE_ACTIVITY);
		pstmt.setString(1, activity.getName());
		pstmt.setInt(2, activity.getCategoryId());
		pstmt.executeUpdate();
		Activity savedActivity = new Activity();
		savedActivity.setName(activity.getName());
		savedActivity.setCategoryId(activity.getCategoryId());
		pstmt.close();
		return savedActivity;
	}

	/**
	 * Returns activity by given name.
	 */
	public Activity getActivityByName(String name) {
		Activity activity = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			ActivityMapper mapper = new ActivityMapper();
			pstmt = con.prepareStatement(FIND_ACTIVITY_BY_NAME);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next())
				activity = mapper.mapRow(rs);
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return activity;
	}

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
	 * Returns all beans with requested status only, for admin approvement
	 * 
	 **/
	public List<ParticipantActivityBean> getActivitiesToApprove() {
		List<ParticipantActivityBean> participantActivityBeansList = new ArrayList<ParticipantActivityBean>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			ParticipantActivityBeanMapper mapper = new ParticipantActivityBeanMapper();
			stmt = con.createStatement();
			rs = stmt.executeQuery(FIND_ALL_REQUESTED_ACTIVITIES);
			while (rs.next())
				participantActivityBeansList.add(mapper.mapRow(rs));
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		System.out.println("ParticipantActivityBeanList -> " + participantActivityBeansList.toString());
		return participantActivityBeansList;
	}

	/**
	 * 
	 * Returns all activities with status to delete only, for admin approvement
	 * 
	 **/
	public List<ParticipantActivityBean> getActivitiesToDelete() {
		List<ParticipantActivityBean> participantActivityBeansList = new ArrayList<ParticipantActivityBean>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			ParticipantActivityBeanMapper mapper = new ParticipantActivityBeanMapper();
			stmt = con.createStatement();
			rs = stmt.executeQuery(FIND_ALL_ACTIVITIES_TO_DELETE);
			while (rs.next())
				participantActivityBeansList.add(mapper.mapRow(rs));
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		System.out.println("activitiesList -> " + participantActivityBeansList.toString());
		return participantActivityBeansList;
	}

	/**
	 * 
	 * Returns activities of the given participant with approved status only
	 * 
	 **/
	public List<ParticipantActivityBean> getApprovedActivities(Participant participant) {
		List<ParticipantActivityBean> activitiesList = new ArrayList<ParticipantActivityBean>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			ParticipantActivityBeanMapper mapper = new ParticipantActivityBeanMapper();
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

//	/**
//	 * 
//	 * Returns all participantActivityBeans 
//	 * 
//	 **/
//	public List<ParticipantActivityBean> getAllParticipantActivityBeans() {
//		List<ParticipantActivityBean> participantActivityBeansList = new ArrayList<ParticipantActivityBean>();
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		Connection con = null;
//		try {
//			con = DBManager.getInstance().getConnection();
//			ParticipantActivityBeanMapper mapper = new ParticipantActivityBeanMapper();
//			pstmt = con.prepareStatement(FIND_ALL_PARTICIPANT_ACTIVITY_BEANS);
//			rs = pstmt.executeQuery();
//			while (rs.next())
//				participantActivityBeansList.add(mapper.mapRow(rs));
//		} catch (SQLException ex) {
//			DBManager.getInstance().rollbackAndClose(con);
//			ex.printStackTrace();
//		} finally {
//			DBManager.getInstance().commitAndClose(con);
//		}
//		System.out.println("participantActivityBeansList -> " + participantActivityBeansList.toString());
//		return participantActivityBeansList;
//	}

//	/**
//	 * 
//	 * Returns all CategoryActivityParticipantBeans 
//	 * 
//	 **/
//	public List<CategoryActivityParticipantBean> getAllCategoryActivityParticipantBeans() {
//		List<CategoryActivityParticipantBean> categoryActivityParticipantBeanList = new ArrayList<CategoryActivityParticipantBean>();
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		Connection con = null;
//		try {
//			con = DBManager.getInstance().getConnection();
//			CategoryActivityParticipantBeanMapper mapper = new CategoryActivityParticipantBeanMapper();
//			pstmt = con.prepareStatement(FIND_ALL_CATEGORY_ACTIVITY_PARTICIPANT_BEANS);
//			rs = pstmt.executeQuery();
//			while (rs.next())
//				categoryActivityParticipantBeanList.add(mapper.mapRow(rs));
//		} catch (SQLException ex) {
//			DBManager.getInstance().rollbackAndClose(con);
//			ex.printStackTrace();
//		} finally {
//			DBManager.getInstance().commitAndClose(con);
//		}
//		System.out.println("participantActivityBeansList -> " + categoryActivityParticipantBeanList.toString());
//		return categoryActivityParticipantBeanList;
//	}

	/**
	 * 
	 * Returns all CategoryActivityBeans
	 * 
	 **/
	public List<CategoryActivityBean> getAllCategoryActivityBeans() {
		List<CategoryActivityBean> categoryActivityBeanList = new ArrayList<CategoryActivityBean>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			CategoryActivityBeanMapper mapper = new CategoryActivityBeanMapper();
			pstmt = con.prepareStatement(FIND_ALL_CATEGORY_ACTIVITY_BEANS);
			rs = pstmt.executeQuery();
			while (rs.next())
				categoryActivityBeanList.add(mapper.mapRow(rs));
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		System.out.println("categoryActivityBeanList -> " + categoryActivityBeanList.toString());
		return categoryActivityBeanList;
	}

	/**
	 * 
	 * Returns category activity bean by the given activity name
	 * 
	 **/
	public CategoryActivityBean getCategoryActivityBeanByActivityName(String activityName) {
		CategoryActivityBean bean = new CategoryActivityBean();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			CategoryActivityBeanMapper mapper = new CategoryActivityBeanMapper();
			pstmt = con.prepareStatement(FIND_CATEGORY_ACTIVITY_BEAN_BY_ACTIVITY_NAME);
			pstmt.setString(1, activityName);
			rs = pstmt.executeQuery();
			if (rs.next())
				bean = mapper.mapRow(rs);
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		System.out.println("bean -> " + bean.toString());
		return bean;
	}

	public void updateCategoryActivityBean(CategoryActivityBean categoryActivityBean, String activityName) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			updateCategoryActivityBean(con, categoryActivityBean, activityName);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	public void updateCategoryActivityBean(Connection con, CategoryActivityBean categoryActivityBean,
			String activityName) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(UPDATE_CATEGORY_ACTIVITY_BEAN);
		pstmt.setInt(1, categoryActivityBean.getCategoryId());
		pstmt.setString(2, categoryActivityBean.getActivityName());
		pstmt.setString(3, activityName);
		pstmt.executeUpdate();
		pstmt.close();
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
	 * Extracts a participant activity bean from the result set row.
	 */
	private static class ParticipantActivityBeanMapper implements EntityMapper<ParticipantActivityBean> {

		@Override
		public ParticipantActivityBean mapRow(ResultSet rs) {
			try {
				ParticipantActivityBean bean = new ParticipantActivityBean();
				bean.setParticipantId(rs.getInt(Fields.PARTICIPANT_ACTIVITY_BEAN__PARTICIPANT_ID));
				bean.setParticipantLogin(rs.getString(Fields.PARTICIPANT_ACTIVITY_BEAN__PARTICIPANT_LOGIN));
				bean.setActivityId(rs.getInt(Fields.PARTICIPANT_ACTIVITY_BEAN__ACTIVIVTY_ID));
				bean.setActivityName(rs.getString(Fields.PARTICIPANT_ACTIVITY_BEAN__ACTIVIVTY_NAME));
				bean.setCategoryId(rs.getInt(Fields.PARTICIPANT_ACTIVITY_BEAN__CATEGORYY_ID));
				bean.setActivityDuration(rs.getInt(Fields.PARTICIPANT_ACTIVITY_BEAN__DURATION));
				bean.setStatusId(rs.getInt(Fields.PARTICIPANT_ACTIVITY_BEAN__STATUS_ID));
				return bean;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	/**
	 * Extracts a category activity participant bean from the result set row.
	 */
	private static class CategoryActivityBeanMapper implements EntityMapper<CategoryActivityBean> {

		@Override
		public CategoryActivityBean mapRow(ResultSet rs) {
			try {
				CategoryActivityBean bean = new CategoryActivityBean();
				bean.setCategoryId(rs.getInt(Fields.PARTICIPANT_ACTIVITY_BEAN__CATEGORYY_ID));
				bean.setCategoryName(rs.getString(Fields.PARTICIPANT_ACTIVITY_BEAN__CATEGORYY_NAME));
				bean.setActivityName(rs.getString(Fields.PARTICIPANT_ACTIVITY_BEAN__ACTIVIVTY_NAME));
				return bean;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
	}

//    /**
//     * Extracts a category activity participant bean from the result set row.
//     */
//    private static class CategoryActivityParticipantBeanMapper implements EntityMapper<CategoryActivityParticipantBean> {
//
//        @Override
//        public CategoryActivityParticipantBean mapRow(ResultSet rs) {
//            try {
//            	CategoryActivityParticipantBean bean = new CategoryActivityParticipantBean();
//                bean.setParticipantLogin(rs.getString(Fields.PARTICIPANT_ACTIVITY_BEAN__PARTICIPANT_LOGIN));
//                bean.setActivityName(rs.getString(Fields.PARTICIPANT_ACTIVITY_BEAN__ACTIVIVTY_NAME));
//                bean.setCategoryName(rs.getString(Fields.PARTICIPANT_ACTIVITY_BEAN__CATEGORYY_NAME));
//                return bean;
//            } catch (SQLException e) {
//                throw new IllegalStateException(e);
//            }
//        }
//    }

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
				activity.setCategoryId(rs.getInt(Fields.ACTIVITY_CATEGORY_ID));
				return activity;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
	}

}
