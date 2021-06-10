package com.epam.bean;

import java.io.Serializable;

/**
 * Provide records for virtual table:
 *
 * |category.name|activity.name|
 * 
 */
public class CategoryActivityBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private int categoryId;
	private String categoryName;
	private String activityName;

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
		return "CategoryActivityBean [categoryId=" + categoryId + ", categoryName=" + categoryName + ", activityName="
				+ activityName + "]";
	}


}
