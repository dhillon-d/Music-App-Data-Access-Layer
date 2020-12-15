package moody.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import moody.model.*;

public class FavoriteArtistsDao {
	
	protected ConnectionManager connectionManager;
	private static FavoriteArtistsDao instance = null;
	
	protected FavoriteArtistsDao() {
		connectionManager = new ConnectionManager();
	}
	public static FavoriteArtistsDao getInstance() {
		if(instance == null) {
			instance = new FavoriteArtistsDao();
		}
		return instance;
	}
	
	public FavoriteArtists create(FavoriteArtists favoriteArtist) throws SQLException {
		String insertFavoriteArtist =
				"INSERT INTO FavoriteArtists(ArtistKey, UserName) " +
				"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertFavoriteArtist,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, favoriteArtist.getArtist().getArtistKey());
			insertStmt.setString(2, favoriteArtist.getUser().getUsername());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int favoriteArtistId = -1;
			if(resultKey.next()) {
				favoriteArtistId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			favoriteArtist.setFavoriteArtistId(favoriteArtistId);;
			return favoriteArtist;
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
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}
	
	public FavoriteArtists getFavoriteArtistById(int favoriteArtistId)
		throws SQLException {
		String selectFavoriteArtist =
			"SELECT FavoriteArtistId, ArtistKey, UserName " +
			"FROM FavoriteArtists " +
			"WHERE FavoriteArtistId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFavoriteArtist);
			selectStmt.setInt(1, favoriteArtistId);
			results = selectStmt.executeQuery();
			ArtistsDao artistsDao = ArtistsDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				int resultFavoriteArtistId = results.getInt("FavoriteArtistId");
				int artistKey = results.getInt("ArtistKey");
				String userName = results.getString("UserName");
				
				Artists artist = artistsDao.getArtistByArtistKey(artistKey);
				Users user = usersDao.getUserFromUsername(userName);
				FavoriteArtists favoriteArtist = new FavoriteArtists(resultFavoriteArtistId,
						artist, user);
				return favoriteArtist;
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
	
	public FavoriteArtists delete(FavoriteArtists favoriteArtist) throws SQLException {
		String deleteFavoriteArtist = "DELETE FROM FavoriteArtists WHERE FavoriteArtistId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteFavoriteArtist);
			deleteStmt.setInt(1, favoriteArtist.getFavoriteArtistId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Persons instance.
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

}
