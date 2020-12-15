package moody.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import moody.model.*;


public class LocationDao {
	protected ConnectionManager connectionManager;
	
	private static LocationDao instance = null;
	protected LocationDao() {
		connectionManager = new ConnectionManager();
	}
	public static LocationDao getInstance() {
		if(instance == null) {
			instance = new LocationDao();
		}
		return instance;
	}

	public Location create(Location location) throws SQLException {
		String insertLocation = "INSERT INTO Location(City,State) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertLocation,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, location.getCity());
			insertStmt.setString(2, location.getState());
			insertStmt.executeUpdate();
			
			resultKey = insertStmt.getGeneratedKeys();
			int locationId = -1;
			if(resultKey.next()) {
				locationId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			location.setLocationId(locationId);
			return location;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
		}
	}

	public Location delete(Location location) throws SQLException {
		String deleteLocation = "DELETE FROM Location WHERE LocationID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteLocation);
			deleteStmt.setInt(1, location.getLocationId());
			deleteStmt.executeUpdate();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	
	public Location getLocationById(int locationId) throws SQLException {
		String selectLocation =
			"SELECT LocationID,City,State FROM Location WHERE LocationId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLocation);
			selectStmt.setInt(1, locationId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int resultLocationId = results.getInt("LocationId");
				String city = results.getString("City");
				String state = results.getString("State");
				Location location = new Location(resultLocationId, city, state);
				return location;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
}
