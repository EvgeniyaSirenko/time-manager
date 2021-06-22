package com.epam.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.epam.db.entity.Category;

public class CategoryManagerTest {

	CategoryManager categoryManager = new CategoryManager();

	@Test
	void createCategory() {
		Category testCategory = new Category();
		testCategory.setName("testName");
		// Category createdCategory = CategoryManager.createCategory(testCategory);
		assertNotNull(testCategory.getId());
		// assertEquals(testCategory.getName(), createdCategory.getName());
	}

}
