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
	private int participantId;
	
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
	public int getParticipantId() {
		return participantId;
	}
	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}
	@Override
	public String toString() {
		return "CategoryActivityParticipantBean [categoryName=" + categoryName + ", activityName=" + activityName
				+ ", participantId=" + participantId + "]";
	}	
}
