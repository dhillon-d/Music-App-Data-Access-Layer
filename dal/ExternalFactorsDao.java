package moody.dal;

import moody.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data access object (DAO) class to interact with the underlying
 * ExternalFactors table in your MySQL instance. This is used to store
 * {@link ExternalFactors} into your MySQL instance and retrieve
 * {@link ExternalFactors} from MySQL instance.
 */
public class ExternalFactorsDao {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.
	private static ExternalFactorsDao instance = null;

	protected ExternalFactorsDao() {
		connectionManager = new ConnectionManager();
	}

	public static ExternalFactorsDao getInstance() {
		if (instance == null) {
			instance = new ExternalFactorsDao();
		}
		return instance;
	}

	/**
	 * Save the ExternalFactors instance by storing it in your MySQL instance. This
	 * runs a INSERT statement.
	 */
	public ExternalFactors create(ExternalFactors externalFactors) throws SQLException {
		String insertExternalFactors = "INSERT INTO ExternalFactors(Day,Temperature) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertExternalFactors);
			insertStmt.setString(1, externalFactors.getDay());
			insertStmt.setDouble(2, externalFactors.getTemperature());
			insertStmt.executeUpdate();
			return externalFactors;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (insertStmt != null) {
				insertStmt.close();
			}
		}
	}

	/**
	 * Get the ExternalFactors record by fetching it from your MySQL instance. This
	 * runs a SELECT statement and returns a single ExternalFactors instance.
	 */
	public ExternalFactors getExternalFactorsFromDay(String day) throws SQLException {
		String selectExternalFactors = "SELECT Day,Temperature FROM ExternalFactors WHERE Day=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectExternalFactors);
			selectStmt.setString(1, day);
			results = selectStmt.executeQuery();
			if (results.next()) {
				String retDay = results.getString("Day");
				int temperature = results.getInt("Temperature");
				ExternalFactors externalFactors = new ExternalFactors(retDay, temperature);
				return externalFactors;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (results != null) {
				results.close();
			}
		}
		return null;
	}

	/**
	 * Delete the ExternalFactors instance. This runs a DELETE statement.
	 */
	public ExternalFactors delete(ExternalFactors externalFactors) throws SQLException {
		String deleteExternalFactors = "DELETE FROM ExternalFactors WHERE Day=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteExternalFactors);
			deleteStmt.setString(1, externalFactors.getDay());
			deleteStmt.executeUpdate();
			// Return null so the caller can no longer operate on the ExternalFactors
			// instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
}
