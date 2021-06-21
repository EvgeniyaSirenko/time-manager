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

import com.epam.db.entity.Participant;

public class ParticipantManager {

	private static final Logger log = LogManager.getLogger(ParticipantManager.class);

	private static final String DELETE_PARTICIPANT = "DELETE FROM participant WHERE login=?";

	private static final String FIND_PARTICIPANT_BY_LOGIN = "SELECT * FROM participant WHERE login=?";

	private static final String FIND_ALL_PARTICIPANTS = "SELECT * FROM participant";

	private static final String CREATE_PARTICIPANT = "INSERT INTO participant "
			+ "(first_name, last_name, login, password, locale_name, role_id) VALUES (?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_PARTICIPANT = "UPDATE participant SET first_name=?, last_name=?, "
			+ "login=?, password=?, locale_name=? WHERE id =?";

	/**
	 * 
	 * Deletes participant by name.
	 * 
	 * @throws DBException
	 * 
	 */
	public void deleteParticipant(String participantLogin) throws DBException {
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(DELETE_PARTICIPANT);
			pstmt.setString(1, participantLogin);
			pstmt.executeUpdate();
			pstmt.close();
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot delete participant ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot delete participant", ex);
		} finally {
			DBManager.getInstance().closeStmt(pstmt);
			DBManager.getInstance().close(con);
		}
	}

	/**
	 * 
	 * Returns all participants.
	 * 
	 *@throws DBException
	 *  
	 **/
	public List<Participant> getAllParticipants() throws DBException {
		List<Participant> participantList = new ArrayList<Participant>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(FIND_ALL_PARTICIPANTS);
			while (rs.next())
				participantList.add(extractParticipant(rs));
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot get participants list ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot get participants list", ex);
		} finally {
			DBManager.getInstance().closeRs(rs);
			DBManager.getInstance().closeStmt(stmt);
			DBManager.getInstance().close(con);
		}
		return participantList;
	}

	/**
	 * 
	 * Returns participant of the given login.
	 * 
	 * @throws DBException
	 * 
	 */
	public Participant getParticipantByLogin(String login) throws DBException {
		Participant participant = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(FIND_PARTICIPANT_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next())
				participant = extractParticipant(rs);
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot get participant ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot get participant", ex);
		} finally {
			DBManager.getInstance().closeRs(rs);
			DBManager.getInstance().closeStmt(pstmt);
			DBManager.getInstance().close(con);
		}
		return participant;
	}

	/**
	 * 
	 * Creates new participant.
	 * 
	 * @throws DBException
	 * 
	 */
	public void createParticipant(Participant participant) throws DBException {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			createParticipant(con, participant);
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot create participant ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot create participant", ex);
		} finally {
			DBManager.getInstance().close(con);
		}
	}

	public Participant createParticipant(Connection con, Participant participant) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(CREATE_PARTICIPANT);
		int k = 1;
		pstmt.setString(k++, participant.getFirstName());
		pstmt.setString(k++, participant.getLastName());
		pstmt.setString(k++, participant.getLogin());
		pstmt.setString(k++, participant.getPassword());
		pstmt.setString(k++, participant.getLocaleName());
		pstmt.setInt(k++, participant.getRoleId());
		pstmt.executeUpdate();
		Participant savedParticipant = new Participant();
		savedParticipant.setFirstName(participant.getFirstName());
		savedParticipant.setLastName(participant.getLastName());
		savedParticipant.setLogin(participant.getLogin());
		savedParticipant.setPassword(participant.getPassword());
		savedParticipant.setLocaleName(participant.getLocaleName());
		savedParticipant.setRoleId(participant.getRoleId());
		DBManager.getInstance().closeStmt(pstmt);
		return savedParticipant;
	}

	/**
	 * 
	 * Updates participant.
	 * 
	 * @throws DBException
	 * 
	 */
	public void updateParticipant(Participant participant) throws DBException {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			updateParticipant(con, participant);
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot update participant ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot update participant", ex);
		} finally {
			DBManager.getInstance().close(con);
		}
	}

	public void updateParticipant(Connection con, Participant participant) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(UPDATE_PARTICIPANT);
		int k = 1;
		pstmt.setString(k++, participant.getFirstName());
		pstmt.setString(k++, participant.getLastName());
		pstmt.setString(k++, participant.getLogin());
		pstmt.setString(k++, participant.getPassword());
		pstmt.setString(k++, participant.getLocaleName());
		pstmt.setInt(k, participant.getId());
		pstmt.executeUpdate();
		DBManager.getInstance().closeStmt(pstmt);
	}

	/**
	 * 
	 * Extracts participant from the result set row.
	 * 
	 * @throws SQLException
	 * 
	 */
	public Participant extractParticipant(ResultSet rs) throws SQLException {
		Participant participant = new Participant();
		participant.setId(rs.getInt(Fields.ENTITY__ID));
		participant.setFirstName(rs.getString(Fields.PARTICIPANT_FIRST_NAME));
		participant.setLastName(rs.getString(Fields.PARTICIPANT_LAST_NAME));
		participant.setLogin(rs.getString(Fields.PARTICIPANT_LOGIN));
		participant.setPassword(rs.getString(Fields.PARTICIPANT_PASSWORD));
		participant.setLocaleName(rs.getString(Fields.PARTICIPANT_LOCALE_NAME));
		participant.setRoleId(rs.getInt(Fields.PARTICIPANT_ROLE_ID));
		return participant;
	}
}
