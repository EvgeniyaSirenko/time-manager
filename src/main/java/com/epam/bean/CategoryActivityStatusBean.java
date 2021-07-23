package com.epam.bean;

import java.io.Serializable;

/**
 * Provide records for virtual table:
 *
 * |category.name|activity.name|status.name|participant.id|
 * 
 */
public class CategoryActivityStatusBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String categoryName;
	private String activityName;
	private String statusName;
	private int participantId;
	
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public int getParticipantId() {
		return participantId;
	}
	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}
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
	@Override
	public String toString() {
		return "CategoryActivityStatusBean [categoryName=" + categoryName + ", activityName=" + activityName
				+ ", statusName=" + statusName + ", participantId=" + participantId + "]";
	}
}
