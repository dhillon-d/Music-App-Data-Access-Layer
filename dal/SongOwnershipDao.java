package moody.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import moody.model.*;

public class SongOwnershipDao {
	
	protected ConnectionManager connectionManager;
	private static SongOwnershipDao instance = null;
	
	protected SongOwnershipDao() {
		connectionManager = new ConnectionManager();
	}
	public static SongOwnershipDao getInstance() {
		if(instance == null) {
			instance = new SongOwnershipDao();
		}
		return instance;
	}
	
	public SongOwnership create(SongOwnership songOwnership) throws SQLException {
		String insertSongOwnership =
				"INSERT INTO SongOwnership(SongId, ArtistKey) " +
				"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertSongOwnership,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, songOwnership.getSong().getSongId());
			insertStmt.setInt(2, songOwnership.getArtist().getArtistKey());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int songOwnershipId = -1;
			if(resultKey.next()) {
				songOwnershipId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			songOwnership.setSongOwnershipId(songOwnershipId);
			return songOwnership;
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

	public SongOwnership getSongOwnershipById(int songOwnershipId)
			throws SQLException {
			String selectSongOwnership =
				"SELECT SongOwnershipId, SongId, ArtistKey " +
				"FROM SongOwnership " +
				"WHERE SongOwnershipId=?;";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectSongOwnership);
				selectStmt.setInt(1, songOwnershipId);
				results = selectStmt.executeQuery();
				SongsDao songsDao = SongsDao.getInstance();
				ArtistsDao artistsDao = ArtistsDao.getInstance();
				if(results.next()) {
					int resultSongOwnershipId = results.getInt("SongOwnershipId");
					String songId = results.getString("SongId");
					int artistKey = results.getInt("ArtistKey");
					
					Songs song = songsDao.getSongBySongId(songId);
					Artists artist = artistsDao.getArtistByArtistKey(artistKey);
					SongOwnership songOwnership = new SongOwnership(resultSongOwnershipId,
							song, artist);
					return songOwnership;
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
	
	public SongOwnership delete(SongOwnership songOwnership) throws SQLException {
		String deleteSongOwnership = "DELETE FROM SongOwnership WHERE SongOwnershipId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteSongOwnership);
			deleteStmt.setInt(1, songOwnership.getSongOwnershipId());
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
