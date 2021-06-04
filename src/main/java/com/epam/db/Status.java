package com.epam.db;

import com.epam.db.entity.ParticipantActivity;

public enum Status {
	REQUESTED, APPROVED;
	
	public static Status getStatus(ParticipantActivity participantActivity) {
		int statusId = participantActivity.getStatusId();
		return Status.values()[statusId];
	}

	public String getName() {
		return name().toLowerCase();
	}

}
