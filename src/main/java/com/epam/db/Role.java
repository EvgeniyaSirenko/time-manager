package com.epam.db;

import com.epam.db.entity.Participant;

public enum Role {
	ADMIN, CLIENT;

	public static Role getRole(Participant participant) {
		int roleId = participant.getRoleId();
		return Role.values()[roleId];
	}

	public String getName() {
		return name().toLowerCase();
	}

}
