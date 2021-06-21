package com.epam.db;

import java.sql.SQLException;

public class AppException extends Exception {

	private static final long serialVersionUID = 1L;

	public AppException(String message, SQLException ex) {
		super(message, ex);
	}

	public AppException(String message) {
		super(message);
	}

}
