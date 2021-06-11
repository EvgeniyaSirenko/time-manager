package com.epam.bean;

import java.io.Serializable;

/**
 * Provide records for virtual table:
 *
 * |category.name|activity.name|
 * 
 */
public class ParticipantActivityDurationBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String participantLogin;
	private int activityId;
	private int activityDuration;
	
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
	public int getActivityDuration() {
		return activityDuration;
	}
	public void setActivityDuration(int activityDuration) {
		this.activityDuration = activityDuration;
	}
	@Override
	public String toString() {
		return "CategoryActivityBean [participantLogin=" + participantLogin + ", activityId=" + activityId
				+ ", activityDuration=" + activityDuration + "]";
	}
}
