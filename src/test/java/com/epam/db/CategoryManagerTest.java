package com.epam.db;

import org.junit.jupiter.api.Test;
import com.epam.db.entity.Category;

public class CategoryManagerTest {

	CategoryManager categoryManager = new CategoryManager();

	@Test
	void createCategory() {
		Category testCategory = new Category();
		testCategory.setName("testName");
	}

}
