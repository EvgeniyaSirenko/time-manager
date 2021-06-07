package com.epam.db.entity;

import java.io.Serializable;

public class ParticipantActivity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int participantId;
	private int activityId;
	private int statusId;
	private int duration;
	
	public int getParticipantId() {
		return participantId;
	}
	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	@Override
	public String toString() {
		return "ParticipantActivity [participantId=" + participantId + ", activityId=" + activityId + ", statusId="
				+ statusId + ", duration=" + duration + "]";
	}


}
