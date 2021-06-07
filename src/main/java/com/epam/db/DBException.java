package com.epam.db;

import java.sql.SQLException;

public class DBException extends Exception {

	public DBException(String string, SQLException ex) {
		super(string, ex);
	}

	public DBException(String string) {
		super(string);
	}

}
