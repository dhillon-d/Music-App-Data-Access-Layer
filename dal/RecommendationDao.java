package moody.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import moody.model.*;


public class RecommendationDao {
	protected ConnectionManager connectionManager;
	
	private static RecommendationDao instance = null;
	protected RecommendationDao() {
		connectionManager = new ConnectionManager();
	}
	public static RecommendationDao getInstance() {
		if(instance == null) {
			instance = new RecommendationDao();
		}
		return instance;
	}

	public Recommendation create(Recommendation recommendation) throws SQLException {
		String insertRecommendation = "INSERT INTO Recommendations(UserName,SongID,Created,Temperature) VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRecommendation,
				Statement.RETURN_GENERATED_KEYS);
//			insertStmt = connection.prepareStatement(insertRecommendation);
			insertStmt.setString(1, recommendation.getUser().getUsername());
			insertStmt.setString(2, recommendation.getSong().getSongId());
			insertStmt.setTimestamp(3, recommendation.getCreated());
			insertStmt.setDouble(4, recommendation.getTemperature());
			insertStmt.executeUpdate();
			
			resultKey = insertStmt.getGeneratedKeys();
			int recommendationId = -1;
			if(resultKey.next()) {
				recommendationId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			recommendation.setRecommendationId(recommendationId);
			return recommendation;
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

	public Recommendation delete(Recommendation recommendation) throws SQLException {
		String deleteRecommendation = "DELETE FROM Recommendations WHERE RecommendationID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRecommendation);
			deleteStmt.setInt(1, recommendation.getRecommendationId());
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
	
	public Recommendation getRecommendationById(int recommendationId) throws SQLException {
		String selectRecommendation =
			"SELECT RecommendationID,UserName,SongID,Created,Temperature FROM Recommendations WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRecommendation);
			selectStmt.setInt(1, recommendationId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			SongsDao songsDao = SongsDao.getInstance();
			if(results.next()) {
				int resultRecommendationId = results.getInt("RecommendationId");
				String username = results.getString("UserName");
				String songID = results.getString("SongID");
				Timestamp created = results.getTimestamp("Created");
				int temperature = results.getInt("Temperature");
				
				Users user = usersDao.getUserFromUsername(username);
				Songs song = songsDao.getSongBySongId(songID);
				Recommendation recommendation = new Recommendation(resultRecommendationId, user, song,created,temperature);
				return recommendation;
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
