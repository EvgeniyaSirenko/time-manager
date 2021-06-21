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

public class CategoryManager {

	private static final Logger log = LogManager.getLogger(ParticipantManager.class);

	private static final String FIND_CATEGORY_BY_NAME = "SELECT * FROM category WHERE name=?";

	private static final String FIND_ALL_CATEGORIES = "SELECT * FROM category";

	private static final String CREATE_CATEGORY = "INSERT INTO category (name) VALUES (?)";

	private static final String DELETE_CATEGORY = "DELETE FROM category WHERE name=?";

	private static final String UPDATE_CATEGORY = "UPDATE category SET name=? WHERE id =?";

	/**
	 * 
	 * Returns category by given name.
	 * 
	 * @throws DBException
	 * 
	 */
	public Category getCategoryByName(String name) throws DBException {
		Category category = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(FIND_CATEGORY_BY_NAME);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if (rs.next())
				category = extractCategory(rs);
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot get category ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot get category", ex);
		} finally {
			DBManager.getInstance().closeRs(rs);
			DBManager.getInstance().closeStmt(pstmt);
			DBManager.getInstance().close(con);
		}
		return category;
	}

	/**
	 * 
	 * Returns all categories.
	 * 
	 * @throws DBException
	 * 
	 **/
	public List<Category> getAllCategories() throws DBException {
		List<Category> categoryList = new ArrayList<Category>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(FIND_ALL_CATEGORIES);
			while (rs.next())
				categoryList.add(extractCategory(rs));
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot get categories ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot get categories", ex);
		} finally {
			DBManager.getInstance().closeRs(rs);
			DBManager.getInstance().closeStmt(stmt);
			DBManager.getInstance().close(con);
		}
		return categoryList;
	}

	/**
	 * 
	 * Deletes category by name.
	 * 
	 * @throws DBException
	 * 
	 **/
	public void deleteCategory(String categoryName) throws DBException {
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			pstmt = con.prepareStatement(DELETE_CATEGORY);
			pstmt.setString(1, categoryName);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot delete category ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot delete category", ex);
		} finally {
			DBManager.getInstance().closeStmt(pstmt);
			DBManager.getInstance().close(con);
		}
	}

	/**
	 * 
	 * Creates category.
	 * 
	 * @throws DBException
	 * 
	 **/
	public void createCategory(Category participant) throws DBException {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			createCategory(con, participant);
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot create category ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot create category", ex);
		} finally {
			DBManager.getInstance().close(con);
		}
	}

	public Category createCategory(Connection con, Category category) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(CREATE_CATEGORY);
		pstmt.setString(1, category.getName());
		pstmt.executeUpdate();
		Category savedCategory = new Category();
		savedCategory.setName(category.getName());
		DBManager.getInstance().closeStmt(pstmt);
		return savedCategory;
	}

	/**
	 * 
	 * Updates category.
	 * 
	 * @throws DBException
	 * 
	 **/
	public void updateCategory(Category category) throws DBException {
		Connection con = null;
		try {
			con = DBManager.getInstance().getConnection();
			updateCategory(con, category);
			con.commit();
		} catch (SQLException ex) {
			log.error("Cannot update category ", ex);
			ex.printStackTrace();
			DBManager.getInstance().rollback(con);
			throw new DBException("Cannot update category", ex);
		} finally {
			DBManager.getInstance().close(con);
		}
	}

	public void updateCategory(Connection con, Category category) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement(UPDATE_CATEGORY);
		pstmt.setString(1, category.getName());
		pstmt.setInt(2, category.getId());
		pstmt.executeUpdate();
		DBManager.getInstance().closeStmt(pstmt);
	}

	/**
	 * 
	 * Extracts category from the result set row.
	 * 
	 * @throws SQLException
	 * 
	 **/
	public Category extractCategory(ResultSet rs) throws SQLException {
		Category category = new Category();
		category.setId(rs.getInt(Fields.ENTITY__ID));
		category.setName(rs.getString(Fields.ENTITY__NAME));
		return category;
	}

}
