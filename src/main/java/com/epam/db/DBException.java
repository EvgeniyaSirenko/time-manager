package com.epam.db;

import java.sql.SQLException;

public class DBException extends Exception {

	private static final long serialVersionUID = 1L;

	public DBException(String string, SQLException ex) {
		super(string, ex);
	}

	public DBException(String string) {
		super(string);
	}

}
