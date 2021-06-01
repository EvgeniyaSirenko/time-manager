package com.epam.db.entity;

import java.io.Serializable;

public class Activity implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int categoryId;

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

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "Activity [id=" + id + ", name=" + name + ", categoryId=" + categoryId + "]";
	}
	

}
