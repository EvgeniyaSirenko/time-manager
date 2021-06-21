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

import com.epam.bean.ParticipantActivityDurationBean;
import com.epam.bean.CategoryActivityParticipantBean;
import com.epam.bean.ParticipantActivityBean;
import com.epam.db.entity.Activity;
import com.epam.db.entity.Participant;

public class ActivityManager {

	private static final Logger log = LogManager.getLogger(ActivityManager.class);

	private static final String DELETE_ACTIVITY = "DELETE FROM activity WHERE name=?";

	private static final String UPDATE_ACTIVITY = "UPDATE activity SET name=?, category_id=? WHERE id =?";

	private static final String CREATE_ACTIVITY = "INSERT INTO activity (name, category_id) VALUES (?, ?)";

	private static final String FIND_ACTIVITY_BY_NAME = "SELECT * FROM activity WHERE name=?";

	private final static String FIND_ALL_REQUESTED_ACTIVITIES = "SELECT p.login, pa.activity_id, a.name, pa.activity_duration "
			+ "FROM activity a, participant p, participant_activity pa "
			+ "WHERE p.id=pa.participant_id AND a.id=pa.activity_id AND pa.status_id=0";

	private final static String FIND_ALL_ACTIVITIES_TO_DELETE = "SELECT p.login, pa.activity_id, a.name, pa.activity_duration "
			+ "FROM activity a, participant p, participant_activity pa "
			+ "WHERE p.id=pa.participant_id AND a.id=pa.activity_id AND pa.status_id=2";

	private final static String FIND_ALL_ACTIVITIES_OF_PARTICIPANT = "SELECT p.login, pa.activity_id, a.name, pa.activity_duration "
			+ "FROM activity a, participant p, participant_activity pa "
			+ "WHERE p.id=pa.participant_id AND a.id=pa.activity_id AND p.id=? AND status_id!=0";

	private final static String FIND_ACTIVITY_OF_PARTICIPANT_BY_ACTIVITY_ID = "SELECT * FROM activity "
			+ "WHERE id IN (SELECT activity_id FROM participant_activity WHERE activity_id=? AND participant_id=?)";

	private final static String FIND_ALL_AVAILABLE_ACTIVITIES = "SELECT * FROM activity";

	private final static String FIND_PARTICIPANTS_TOTAL_ACTIVITY_AND_DURATION = "SELECT p.login, COUNT(pa.activity_id) AS activities, "
			+ "SUM(pa.activity_duration) AS duration FROM participant_activity pa JOIN participant p ON p.id=pa.participant_id "
			+ "GROUP BY p.login";

	private final static String FIND_ACTIVITY_CATEGORY_PARTICIPANT_QUANTITY_WITH_ACTIVITY_ORDER = "SELECT a.name AS activity_name, "
			+ "c.name AS category_name, temp.participant AS participants "
			+ "FROM activity a JOIN (SELECT activity_id, COUNT(participant_id) AS participant FROM participant_activity "
			+ "GROUP BY activity_id) temp ON temp.activity_id=a.id JOIN category c ON c.id=a.category_id ORDER BY a.name";

	private final static String FIND_ACTIVITY_CATEGORY_PARTICIPANT_QUANTITY_WITH_CATEGORY_ORDER = "SELECT a.name AS activity_name, "
			+ "c.name AS category_name, temp.participant AS participants "
			+ "FROM activity a JOIN (SELECT activity_id, COUNT(participant_id) AS participant FROM participant_activity "
			+ "GROUP BY activity_id) temp ON temp.activity_id=a.id JOIN category c ON c.id=a.category_id ORDER BY c.name";

	private final static String FIND_ACTIVITY_CATEGORY_PARTICIPANT_QUANTITY_WITH_PARTICIPANTS_QUANTITY_ORDER = "SELECT a.name "
			+ "AS activity_name, c.name AS category_name, temp.participant AS participants "
			+ "FROM activity a JOIN (SELECT activity_id, COUNT(participant_id) AS participant FROM participant_activity "
			+ "GROUP BY activity_id) temp ON temp.activity_id=a.id JOIN category c ON c.id=a.category_id ORDER BY participants";

	private final static String FIND_ACTIVITY_CATEGORY_PARTICIPANT_QUANTITY_WITH_CATEGORY_FILTER = "SELECT a.name AS activity_name, "
			+ "c.name AS category_name, temp.participant AS participants FROM activity a JOIN (SELECT activity_id, "
			+ "COUNT(participant_id) AS participant FROM participant_activity GROUP BY activity_id) temp ON temp.activity_id=a.id "
			+ "JOIN category c ON c.id=a.category_id WHERE c.name=? ORDER BY participants";

	/**
	 * 
	 * Deletes activity by name.
	 * 
	 * @throws DBException
	 * 
	 **/
	public void deleteActivity(String activityName) throws DBException {
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(DELETE_ACTIVITY);
			pstmt.setString(1, activityName);
			pstmt.executeUpdate();
			pstmt.close();
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot delete activity ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot delete activity", ex);
		} finally {
			DBManager.getInstance().closeStmt(pstmt);
			DBManager.getInstance().close(con);
		}
	}

	/**
	 * 
	 * Updates activity.
	 * 
	 * @throws DBException
	 * 
	 **/
	public void updateActivity(Activity activity) throws DBException {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			updateActivity(con, activity);
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot update activity ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot update activity", ex);
		} finally {
			DBManager.getInstance().close(con);
		}
	}

	public void updateActivity(Connection con, Activity activity) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(UPDATE_ACTIVITY);
		pstmt.setString(1, activity.getName());
		pstmt.setInt(2, activity.getCategoryId());
		pstmt.setInt(3, activity.getId());
		pstmt.executeUpdate();
		DBManager.getInstance().closeStmt(pstmt);
	}

	/**
	 * 
	 * Creates new activity.
	 * 
	 * @throws DBException
	 * 
	 **/
	public void createActivity(Activity activity) throws DBException {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			createActivity(con, activity);
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot create activity ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot create activity", ex);
		} finally {
			DBManager.getInstance().close(con);
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
		DBManager.getInstance().closeStmt(pstmt);
		return savedActivity;
	}

	/**
	 * 
	 * Returns activity by given name.
	 * 
	 * @throws DBException
	 * 
	 **/
	public Activity getActivityByName(String name) throws DBException {
		Activity activity = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(FIND_ACTIVITY_BY_NAME);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next())
				activity = extractActivity(rs);
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot get activities ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot get activities", ex);
		} finally {
			DBManager.getInstance().closeRs(rs);
			DBManager.getInstance().closeStmt(pstmt);
			DBManager.getInstance().close(con);
		}
		return activity;
	}

	/**
	 * 
	 * Returns all activities.
	 * 
	 * @throws DBException
	 * 
	 **/
	public List<Activity> getAvailableActivities() throws DBException {
		List<Activity> activitiesList = new ArrayList<Activity>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(FIND_ALL_AVAILABLE_ACTIVITIES);
			while (rs.next())
				activitiesList.add(extractActivity(rs));
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot get activities ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot get activities", ex);
		} finally {
			DBManager.getInstance().closeRs(rs);
			DBManager.getInstance().closeStmt(stmt);
			DBManager.getInstance().close(con);
		}
		return activitiesList;
	}

	/**
	 * 
	 * Returns all beans with requested status only, for admin approvement
	 * 
	 * @throws DBException
	 * 
	 **/
	public List<ParticipantActivityBean> getActivitiesToApprove() throws DBException {
		List<ParticipantActivityBean> participantActivityBeansList = new ArrayList<ParticipantActivityBean>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(FIND_ALL_REQUESTED_ACTIVITIES);
			while (rs.next())
				participantActivityBeansList.add(extractParticipantActivityBean(rs));
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot get activities ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot get activities", ex);
		} finally {
			DBManager.getInstance().closeRs(rs);
			DBManager.getInstance().closeStmt(stmt);
			DBManager.getInstance().close(con);
		}
		return participantActivityBeansList;
	}

	/**
	 * 
	 * Returns all activities with status to delete only, for admin approvement
	 * 
	 * @throws DBException
	 * 
	 **/
	public List<ParticipantActivityBean> getActivitiesToDelete() throws DBException {
		List<ParticipantActivityBean> participantActivityBeansList = new ArrayList<ParticipantActivityBean>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(FIND_ALL_ACTIVITIES_TO_DELETE);
			while (rs.next())
				participantActivityBeansList.add(extractParticipantActivityBean(rs));
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot get activities ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot get activities", ex);
		} finally {
			DBManager.getInstance().closeRs(rs);
			DBManager.getInstance().closeStmt(stmt);
			DBManager.getInstance().close(con);
		}
		return participantActivityBeansList;
	}

	/**
	 * 
	 * Returns activities of the given participant with approved status only
	 * 
	 * @throws DBException
	 * 
	 **/
	public List<ParticipantActivityBean> getApprovedActivities(Participant participant) throws DBException {
		List<ParticipantActivityBean> activitiesList = new ArrayList<ParticipantActivityBean>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(FIND_ALL_ACTIVITIES_OF_PARTICIPANT);
			pstmt.setInt(1, participant.getId());
			rs = pstmt.executeQuery();
			while (rs.next())
				activitiesList.add(extractParticipantActivityBean(rs));
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot get activities ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot get activities", ex);
		} finally {
			DBManager.getInstance().closeRs(rs);
			DBManager.getInstance().closeStmt(pstmt);
			DBManager.getInstance().close(con);
		}
		return activitiesList;
	}

	/**
	 * 
	 * Returns activities of the given participant with approved status only
	 * 
	 * @throws DBException
	 * 
	 **/
	public List<ParticipantActivityDurationBean> getParticipantsTotalActivitiesAndDuration() throws DBException {
		List<ParticipantActivityDurationBean> participantActivityBeansList = new ArrayList<ParticipantActivityDurationBean>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(FIND_PARTICIPANTS_TOTAL_ACTIVITY_AND_DURATION);
			rs = pstmt.executeQuery();
			while (rs.next())
				participantActivityBeansList.add(extractParticipantActivityDurationBean(rs));
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot get activities ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot get activities", ex);
		} finally {
			DBManager.getInstance().closeRs(rs);
			DBManager.getInstance().closeStmt(pstmt);
			DBManager.getInstance().close(con);
		}
		return participantActivityBeansList;
	}

	/**
	 * 
	 * Returns all CategoryActivityParticipantBeans ordered by activity name
	 * 
	 * @throws DBException
	 * 
	 **/
	public List<CategoryActivityParticipantBean> getAllCategoryActivityParticipantBeansActivityOrder()
			throws DBException {
		List<CategoryActivityParticipantBean> categoryActivityParticipantBeanList = new ArrayList<CategoryActivityParticipantBean>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(FIND_ACTIVITY_CATEGORY_PARTICIPANT_QUANTITY_WITH_ACTIVITY_ORDER);
			rs = pstmt.executeQuery();
			while (rs.next())
				categoryActivityParticipantBeanList.add(extractCategoryActivityParticipantBean(rs));
			con.commit();
		} catch (SQLException ex) {
			log.error("annot get activities ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot get activities", ex);
		} finally {
			DBManager.getInstance().closeRs(rs);
			DBManager.getInstance().closeStmt(pstmt);
			DBManager.getInstance().close(con);
		}
		return categoryActivityParticipantBeanList;
	}

	/**
	 * 
	 * Returns all CategoryActivityParticipantBeans ordered by category name
	 * 
	 * @throws DBException
	 * 
	 **/
	public List<CategoryActivityParticipantBean> getAllCategoryActivityParticipantBeansCategoryOrder()
			throws DBException {
		List<CategoryActivityParticipantBean> categoryActivityParticipantBeanList = new ArrayList<CategoryActivityParticipantBean>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(FIND_ACTIVITY_CATEGORY_PARTICIPANT_QUANTITY_WITH_CATEGORY_ORDER);
			rs = pstmt.executeQuery();
			while (rs.next())
				categoryActivityParticipantBeanList.add(extractCategoryActivityParticipantBean(rs));
		} catch (SQLException ex) {
			log.error("Cannot get activities ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot get activities", ex);
		} finally {
			DBManager.getInstance().closeRs(rs);
			DBManager.getInstance().closeStmt(pstmt);
			DBManager.getInstance().close(con);
		}
		return categoryActivityParticipantBeanList;
	}

	/**
	 * 
	 * Returns all CategoryActivityParticipantBeans ordered by participants quantity
	 * 
	 * @throws DBException
	 * 
	 **/
	public List<CategoryActivityParticipantBean> getAllCategoryActivityParticipantBeansParticipantsQuantityOrder()
			throws DBException {
		List<CategoryActivityParticipantBean> categoryActivityParticipantBeanList = new ArrayList<CategoryActivityParticipantBean>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(FIND_ACTIVITY_CATEGORY_PARTICIPANT_QUANTITY_WITH_PARTICIPANTS_QUANTITY_ORDER);
			rs = pstmt.executeQuery();
			while (rs.next())
				categoryActivityParticipantBeanList.add(extractCategoryActivityParticipantBean(rs));
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot get activities ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot get activities", ex);
		} finally {
			DBManager.getInstance().closeRs(rs);
			DBManager.getInstance().closeStmt(pstmt);
			DBManager.getInstance().close(con);
		}
		return categoryActivityParticipantBeanList;
	}

	/**
	 * 
	 * Returns all CategoryActivityParticipantBeans ordered by participants quantity
	 * 
	 * @throws DBException
	 * 
	 **/
	public List<CategoryActivityParticipantBean> getCategoryActivityParticipantBeansFilteredByCategoryName(
			String categoryName) throws DBException {
		List<CategoryActivityParticipantBean> categoryActivityParticipantBeanList = new ArrayList<CategoryActivityParticipantBean>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(FIND_ACTIVITY_CATEGORY_PARTICIPANT_QUANTITY_WITH_CATEGORY_FILTER);
			pstmt.setString(1, categoryName);
			rs = pstmt.executeQuery();
			while (rs.next())
				categoryActivityParticipantBeanList.add(extractCategoryActivityParticipantBean(rs));
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot get activities ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot get activities", ex);
		} finally {
			DBManager.getInstance().closeRs(rs);
			DBManager.getInstance().closeStmt(pstmt);
			DBManager.getInstance().close(con);
		}
		return categoryActivityParticipantBeanList;
	}

	/**
	 * 
	 * Returns activities of the given participant with approved status only.
	 * 
	 * @throws DBException
	 * 
	 **/
	public Activity getParticipantActivityByActivityId(Participant participant, int activityId) throws DBException {
		Activity activity = new Activity();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(FIND_ACTIVITY_OF_PARTICIPANT_BY_ACTIVITY_ID);
			pstmt.setInt(1, activityId);
			pstmt.setInt(2, participant.getId());
			rs = pstmt.executeQuery();
			if (rs.next())
				activity = extractActivity(rs);
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot get activities ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot get activities", ex);
		} finally {
			DBManager.getInstance().closeRs(rs);
			DBManager.getInstance().closeStmt(pstmt);
			DBManager.getInstance().close(con);
		}
		return activity;
	}

	/**
	 * 
	 * Extracts a ParticipantActivityBean from the result set row.
	 * 
	 * @throws SQLException
	 * 
	 **/
	public ParticipantActivityBean extractParticipantActivityBean(ResultSet rs) throws SQLException {
			ParticipantActivityBean bean = new ParticipantActivityBean();
			bean.setParticipantLogin(rs.getString(Fields.PARTICIPANT_ACTIVITY_BEAN__PARTICIPANT_LOGIN));
			bean.setActivityId(rs.getInt(Fields.PARTICIPANT_ACTIVITY_BEAN__ACTIVIVTY_ID));
			bean.setActivityName(rs.getString(Fields.PARTICIPANT_ACTIVITY_BEAN__ACTIVIVTY_NAME));
			bean.setActivityDuration(rs.getInt(Fields.PARTICIPANT_ACTIVITY_BEAN__DURATION));
			return bean;
	}

	/**
	 * 
	 * Extracts a CategoryActivityParticipantBean from the result set row.
	 * 
	 * @throws SQLException 
	 * 
	 **/
	public ParticipantActivityDurationBean extractParticipantActivityDurationBean(ResultSet rs) throws SQLException {
			ParticipantActivityDurationBean bean = new ParticipantActivityDurationBean();
			bean.setParticipantLogin(rs.getString(Fields.PARTICIPANT_ACTIVITY_DURATION_BEAN__PARTICIPANT_LOGIN));
			bean.setActivityDuration(rs.getInt(Fields.PARTICIPANT_ACTIVITY_DURATION_BEAN__ACTIVITY_DURATION));
			bean.setActivityId(rs.getInt(Fields.PARTICIPANT_ACTIVITY_DURATION_BEAN__ACTIVITY_ID));
			return bean;
	}

	/**
	 * 
	 * Extracts a CategoryActivityParticipantBeann from the result set row.
	 * 
	 * @throws SQLException
	 * 
	 **/
	public CategoryActivityParticipantBean extractCategoryActivityParticipantBean(ResultSet rs) throws SQLException {
		CategoryActivityParticipantBean bean = new CategoryActivityParticipantBean();
		bean.setCategoryName(rs.getString(Fields.CATEGORY_ACTIVITY_PARTICIPANT_BEAN__CATEGORY_NAME));
		bean.setActivityName(rs.getString(Fields.CATEGORY_ACTIVITY_PARTICIPANT_BEAN__ACTIVITY_NAME));
		bean.setParticipantId(rs.getInt(Fields.CATEGORY_ACTIVITY_PARTICIPANT_BEAN__PARTICIPANT_ID));
		return bean;
	}

	/**
	 * 
	 * Extracts activity from the result set row.
	 * 
	 * @throws SQLException
	 * 
	 **/
	public Activity extractActivity(ResultSet rs) throws SQLException {
		Activity activity = new Activity();
		activity.setId(rs.getInt(Fields.ENTITY__ID));
		activity.setName(rs.getString(Fields.ENTITY__NAME));
		activity.setCategoryId(rs.getInt(Fields.ACTIVITY_CATEGORY_ID));
		return activity;
	}
}
