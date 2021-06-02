package com.epam.db.entity;

import java.io.Serializable;

public class Activity implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int duration;
	private int categoryId;
	private int statusId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	@Override
	public String toString() {
		return "Activity [id=" + id + ", name=" + name + ", duration=" + duration + ", categoryId=" + categoryId
				+ ", statusId=" + statusId + "]";
	}

}
