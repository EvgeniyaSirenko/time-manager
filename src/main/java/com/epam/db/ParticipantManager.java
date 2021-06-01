package com.epam.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.db.entity.Participant;

public class ParticipantManager {

	private static final Logger log = LogManager.getLogger(ParticipantManager.class);

	private static final String FIND_PARTICIPANT_BY_LOGIN = "SELECT * FROM participant WHERE login=?";
	private static final String CREATE_PARTICIPANT = "INSERT INTO participant (first_name, last_name, login, password, locale_name, role_id) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";

	public Participant getParticipantByLogin(String login) {
		Participant participant = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			ParticipantMapper mapper = new ParticipantMapper();
			pstmt = con.prepareStatement(FIND_PARTICIPANT_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next())
				participant = mapper.mapRow(rs);
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return participant;
	}

	public void createParticipant(Participant participant) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			createParticipant(con, participant);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
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
		pstmt.close();
		return savedParticipant;
	}

	/**
	 * Extracts a user from the result set row.
	 */
	private static class ParticipantMapper implements EntityMapper<Participant> {

		@Override
		public Participant mapRow(ResultSet rs) {
			try {
				Participant participant = new Participant();
				participant.setId(rs.getInt(Fields.ENTITY__ID));
				participant.setFirstName(rs.getString(Fields.FIRST_NAME));
				participant.setLastName(rs.getString(Fields.LAST_NAME));
				participant.setLogin(rs.getString(Fields.LOGIN));
				participant.setPassword(rs.getString(Fields.PASSWORD));
				participant.setLocaleName(rs.getString(Fields.LOCALE_NAME));
				participant.setRoleId(rs.getInt(Fields.ROLE_ID));
				return participant;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	// private DBManager dbManager;

	////////////////////////////////

//	private static ParticipantManager instance;
//
//	public static synchronized ParticipantManager getInstance() {
//		if (instance == null) {
//			instance = new ParticipantManager();
//		}
//		return instance;
//	}
//
//	private ParticipantManager() {
//		DBManager dbManager = DBManager.getInstance();
//	}

	////////////////////////////////

}
