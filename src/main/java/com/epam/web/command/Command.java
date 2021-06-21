package com.epam.web.command;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.db.AppException;

public abstract class Command implements Serializable {

	private static final long serialVersionUID = 1L;

	public abstract String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException;

	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}
}
