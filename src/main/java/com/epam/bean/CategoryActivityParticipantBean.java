package com.epam.bean;

import java.io.Serializable;

/**
 * Provide records for virtual table:
 *
 * |category.name|activity.name|participant.login|
 * 
 */
public class CategoryActivityParticipantBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String categoryName;
	private String activityName;
	private String participantLogin;
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getParticipantLogin() {
		return participantLogin;
	}
	public void setParticipantLogin(String participantLogin) {
		this.participantLogin = participantLogin;
	}
	@Override
	public String toString() {
		return "CategoryActivityParticipantBean [categoryName=" + categoryName + ", activityName=" + activityName
				+ ", participantLogin=" + participantLogin + "]";
	}
}
