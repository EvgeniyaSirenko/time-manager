package com.epam.db;

import java.sql.SQLException;

public class DBException extends AppException {

	private static final long serialVersionUID = 1L;

	public DBException(String message, SQLException ex) {
		super(message, ex);
	}
	public DBException(String message) {
		super(message);
	}

}
