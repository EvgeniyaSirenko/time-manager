package com.epam.db;

import com.epam.db.entity.Activity;

public enum Status {
	NEW, REQUESTED, APPROVED;
	
	public static Status getStatus(Activity activity) {
		int statusId = activity.getStatusId();
		return Status.values()[statusId];
	}

	public String getName() {
		return name().toLowerCase();
	}

}
