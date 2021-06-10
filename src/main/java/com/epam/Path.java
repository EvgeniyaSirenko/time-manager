package com.epam;

/**
 * 
 * Path holder (jsp pages, controller commands).
 * 
 */
public final class Path {
	
	// pages
	public static final String PAGE__LOGIN = "login.jsp";
	public static final String PAGE__REGISTRATION = "registration.jsp";
	public static final String PAGE__ERROR_PAGE = "error.jsp";
	public static final String PAGE__PARTICIPANT_MAIN_PAGE = "participantMainPage.jsp";
	public static final String PAGE__PARTICIPANT_ACTIVITIES = "participantActivities.jsp";
	public static final String PAGE__PARTICIPANT_ADD_ACTIVITY = "participantAddActivity.jsp";
	public static final String PAGE__PARTICIPANT_ACCOUNT = "participantAccount.jsp";
	public static final String PAGE__PARTICIPANT_UPDATE = "participantAccount.jsp";
	public static final String PAGE__ADMIN_MAIN_PAGE = "adminMainPage.jsp";
	public static final String PAGE__ACTIVITIES_TO_APPROVE = "activitiesToApprove.jsp";
	public static final String PAGE__ACTIVITIES_TO_DELELE = "activitiesToDelete.jsp";
	public static final String PAGE__CREATE_CATEGORY = "createCategory.jsp";
	public static final String PAGE__CREATE_ACTIVITY = "createActivity.jsp";
	public static final String PAGE__CATEGORIES = "categories.jsp";
	public static final String PAGE__UPDATE_CATEGORY = "updateCategory.jsp";
	public static final String PAGE__ACTIVITIES = "activities.jsp";
	public static final String PAGE__PARTICIPANTS = "participants.jsp";
	public static final String PAGE__UPDATE_ACTIVITY = "updateActivity.jsp";

	// commands
	public static final String COMMAND__PARTICIPANT_ADD_ACTIVITY_PAGE = "/controller?command=participantAddActivity";
	public static final String COMMAND__PARTICIPANT_ACTIVITY_PAGE = "/controller?command=participantActivityPage";


}