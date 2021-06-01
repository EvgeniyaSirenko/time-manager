package com.epam;

/**
 * 
 * Path holder (jsp pages, controller commands).
 * 
 */
public final class Path {
	
	// pages
	public static final String PAGE__LOGIN = "/login.jsp";
	public static final String PAGE__REGISTRATION = "/registration.jsp";
	public static final String PAGE__ERROR_PAGE = "/WEB-INF/jsp/error.jsp";
	public static final String PAGE__PARTICIPANT_PAGE = "/WEB-INF/jsp/client/participantInfo.jsp";
	public static final String PAGE__ADMIN_PAGE = "/WEB-INF/jsp/admin/adminInfo.jsp";
	
	
	
	public static final String PAGE__LIST_ORDERS = "/WEB-INF/jsp/admin/list_orders.jsp";
	public static final String PAGE__SETTINGS = "/WEB-INF/jsp/settings.jsp";

	// commands
	public static final String COMMAND__LIST_ORDERS = "/controller?command=listOrders";
	public static final String COMMAND__LIST_MENU = "/controller?command=listMenu";

}