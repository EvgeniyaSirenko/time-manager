package com.epam.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBManager {
	
	private static final Logger log = LogManager.getLogger(DBManager.class);

	private static DBManager instance;

	public static synchronized DBManager getInstance() {
		if (instance == null)
			instance = new DBManager();
		return instance;
	}

	public Connection getConnection() throws SQLException {
		Connection con = null;
		try {
			Context initContext = new InitialContext();
			Context context = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) context.lookup("jdbc/TimeDB");
			con = ds.getConnection();
		} catch (NamingException ex) {
			log.error("Cannot obtain a connection from the pool", ex);
		}
		return con;
	}

	public void close(Connection con) {
		try {
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void rollback(Connection con) {
		try {
			con.rollback();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void closeRs(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void closeStmt(Statement stmt) {
		try {
			stmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
