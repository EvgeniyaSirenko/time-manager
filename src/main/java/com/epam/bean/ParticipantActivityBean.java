package com.epam.bean;

import java.io.Serializable;

/**
 * Provide records for virtual table:
 *
 * |participant_id|participant.login|activity_id|activity.name|category.id|activity_duration|status.id|
 * 
 */
public class ParticipantActivityBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int participantId;
	private String participantLogin;
	private int activityId;
	private String activityName;
	private int categoryId;
	private int activityDuration;
	private int statusId;
	
	public int getParticipantId() {
		return participantId;
	}
	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}
	public String getParticipantLogin() {
		return participantLogin;
	}
	public void setParticipantLogin(String participantLogin) {
		this.participantLogin = participantLogin;
	}
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getActivityDuration() {
		return activityDuration;
	}
	public void setActivityDuration(int activity_duration) {
		this.activityDuration = activity_duration;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
	@Override
	public String toString() {
		return "ParticipantActivityBean [participantId=" + participantId + ", participantLogin=" + participantLogin
				+ ", activityId=" + activityId + ", activityName=" + activityName + ", categoryId=" + categoryId
				+ ", activityDuration=" + activityDuration + ", statusId=" + statusId + "]";
	}
	
	
}
