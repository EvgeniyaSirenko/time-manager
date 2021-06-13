package com.epam.bean;

import java.io.Serializable;

/**
 * Provide records for virtual table:
 *
 * |participant.login|activity_id|activity.name|activity_duration|
 * 
 */
public class ParticipantActivityBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String participantLogin;
	private int activityId;
	private String activityName;
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

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getActivityDuration() {
		return activityDuration;
	}

	public void setActivityDuration(int activity_duration) {
		this.activityDuration = activity_duration;
	}

	@Override
	public String toString() {
		return "ParticipantActivityBean [participantLogin=" + participantLogin + ", activityId=" + activityId
				+ ", activityName=" + activityName + ", activityDuration=" + activityDuration + "]";
	}

}
