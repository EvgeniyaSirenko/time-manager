package com.epam.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.db.entity.Category;
import com.epam.db.entity.Participant;

public class CategoryManager {
	
	private static final Logger log = LogManager.getLogger(ParticipantManager.class);
	
	private static final String FIND_CATEGORY_BY_NAME = "SELECT * FROM category WHERE name=?";
	private static final String FIND_ALL_CATEGORIES = "SELECT * FROM category";
	private static final String CREATE_CATEGORY = "INSERT INTO category (name) VALUES (?)";
	private static final String DELETE_CATEGORY = "DELETE FROM category WHERE name=?";
	private static final String UPDATE_CATEGORY = "UPDATE category SET name=? WHERE id =?";

    /**
     * Returns category by given name.
     */
	public Category getCategoryByName(String name) {
		Category category = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			CategoryMapper mapper = new CategoryMapper();
			pstmt = con.prepareStatement(FIND_CATEGORY_BY_NAME);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next())
				category = mapper.mapRow(rs);
			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
		return category;
	}
	
    /**
     * Returns all categories.
     */
    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<Category>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            CategoryMapper mapper = new CategoryMapper();
            stmt = con.createStatement();
            rs = stmt.executeQuery(FIND_ALL_CATEGORIES);
            while (rs.next())
            	categoryList.add(mapper.mapRow(rs));
        } catch (SQLException ex) {
            DBManager.getInstance().rollbackAndClose(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return categoryList;
    }
    
    /**
     * Deletes category by name.
     */
	public void deleteCategory(String categoryName) {
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(DELETE_CATEGORY);
			pstmt.setString(1, categoryName);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	/**
	 * Creates category.
	 */	
	public void createCategory(Category participant) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			createCategory(con, participant);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	public Category createCategory(Connection con, Category category) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(CREATE_CATEGORY);
		pstmt.setString(1, category.getName());
		pstmt.executeUpdate();
		Category savedCategory = new Category();
		savedCategory.setName(category.getName());
		pstmt.close();
		return savedCategory;
	}
	
	public void updateCategory(Category category) {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			updateCategory(con, category);
		} catch (SQLException ex) {
			DBManager.getInstance().rollbackAndClose(con);
			ex.printStackTrace();
		} finally {
			DBManager.getInstance().commitAndClose(con);
		}
	}

	public void updateCategory(Connection con, Category category) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(UPDATE_CATEGORY);
		pstmt.setString(1, category.getName());
		pstmt.setInt(2, category.getId());
		pstmt.executeUpdate();
		pstmt.close();
		System.out.println("updatedCategory_Manager ->" + new CategoryManager().getCategoryByName(category.getName()));
	}
	
	/**
	 * Extracts category from the result set row.
	 */
	private static class CategoryMapper implements EntityMapper<Category> {

		@Override
		public Category mapRow(ResultSet rs) {
			try {
				Category category = new Category();
				category.setId(rs.getInt(Fields.ENTITY__ID));
				category.setName(rs.getString(Fields.ENTITY__NAME));
				return category;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
	}
}
