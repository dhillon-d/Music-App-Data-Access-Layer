package moody.dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import moody.model.*;

public class ArtistsDao {
	
	protected ConnectionManager connectionManager;
	private static ArtistsDao instance = null;
	
	protected ArtistsDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static ArtistsDao getInstance() {
		if(instance == null) {
			instance = new ArtistsDao();
		}
		return instance;
	}

	public Artists create(Artists artist) throws SQLException {
		String insertArtist =
			"INSERT INTO Artists(ArtistName, Acousticness, Danceability, Duration, Energy,"
			+ " Instrumentalness, Liveness, Loudness, Speechiness, Tempo, Valence, Popularity,"
			+ " Mode, Count) " +
			"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertArtist,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, artist.getArtistName());
			insertStmt.setBigDecimal(2, artist.getAcousticness());
			insertStmt.setBigDecimal(3, artist.getDanceability());
			insertStmt.setBigDecimal(4, artist.getDuration());
			insertStmt.setBigDecimal(5, artist.getEnergy());
			insertStmt.setBigDecimal(6, artist.getInstrumentalness());
			insertStmt.setBigDecimal(7, artist.getLiveness());
			insertStmt.setBigDecimal(8, artist.getLoudness());
			insertStmt.setBigDecimal(9, artist.getSpeechiness());
			insertStmt.setBigDecimal(10, artist.getTempo());
			insertStmt.setBigDecimal(11, artist.getValence());
			insertStmt.setBigDecimal(12, artist.getPopularity());
			insertStmt.setBoolean(13, artist.isMode());
			insertStmt.setInt(14, artist.getCount());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int artistId = -1;
			if(resultKey.next()) {
				artistId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			artist.setArtistKey(artistId);;
			return artist;
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
	
	public List<Artists> getArtistBySongId(String songid) throws SQLException {
		List<Artists> artists = new ArrayList<>();
		String selectArtist =
				"SELECT Artists.ArtistKey, Artists.ArtistName, Artists.Acousticness,"
				+ " Artists.Danceability, Artists.Duration, Artists.Energy,"
				+ "	Artists.Instrumentalness, Artists.Liveness, Artists.Loudness, Artists.Speechiness,"
				+ "	Artists.Tempo, Artists.Valence, Artists.Popularity, Artists.Mode, Artists.Count "
				+ "FROM Songs "
				+ "INNER JOIN SongOwnership on Songs.SongID = SongOwnership.SongID "
				+ "INNER JOIN Artists on SongOwnership.ArtistKey = Artists.ArtistKey "
				+ "WHERE Songs.SongID = ?;";

		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectArtist);
			selectStmt.setString(1, songid);
			results = selectStmt.executeQuery();
			while (results.next()) {
				int artistKey = results.getInt("ArtistKey");
				String artistName = results.getString("ArtistName");
				BigDecimal acousticness = results.getBigDecimal("Acousticness");
				BigDecimal danceability = results.getBigDecimal("Danceability");
				BigDecimal duration = results.getBigDecimal("Duration");
				BigDecimal energy = results.getBigDecimal("Energy");
				BigDecimal instramentalness = results.getBigDecimal("Instrumentalness");
				BigDecimal liveness = results.getBigDecimal("Liveness");
				BigDecimal loudness = results.getBigDecimal("Loudness");
				BigDecimal speechiness = results.getBigDecimal("Speechiness");
				BigDecimal tempo = results.getBigDecimal("Tempo");
				BigDecimal valence = results.getBigDecimal("Valence");
				BigDecimal popularity = results.getBigDecimal("Popularity");
				boolean mode = results.getBoolean("Mode");
				int count = results.getInt("Count");
				
				Artists artist = new Artists(artistKey, artistName, acousticness, danceability,
						duration, energy, instramentalness, liveness, loudness, speechiness, tempo,
						valence, popularity, mode, count);
				artists.add(artist);
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
		return artists;
	}
	
	public Artists getArtistByArtistKey(int artistKey) throws SQLException {
		String selectArtist =
				"SELECT Artists.ArtistKey, Artists.ArtistName, Artists.Acousticness,"
				+ "Artists.Danceability, Artists.Duration, Artists.Energy,"
				+ "	Artists.Instrumentalness, Artists.Liveness, Artists.Loudness, Artists.Speechiness,"
				+ "	Artists.Tempo, Artists.Valence, Artists.Popularity, Artists.Mode, Artists.Count "
				+ "FROM Artists "
				+ "WHERE ArtistKey = ?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectArtist);
			selectStmt.setInt(1, artistKey);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int resultArtistKey = results.getInt("ArtistKey");
				String artistName = results.getString("ArtistName");
				BigDecimal acousticness = results.getBigDecimal("Acousticness");
				BigDecimal danceability = results.getBigDecimal("Danceability");
				BigDecimal duration = results.getBigDecimal("Duration");
				BigDecimal energy = results.getBigDecimal("Energy");
				BigDecimal instramentalness = results.getBigDecimal("Instrumentalness");
				BigDecimal liveness = results.getBigDecimal("Liveness");
				BigDecimal loudness = results.getBigDecimal("Loudness");
				BigDecimal speechiness = results.getBigDecimal("Speechiness");
				BigDecimal tempo = results.getBigDecimal("Tempo");
				BigDecimal valence = results.getBigDecimal("Valence");
				BigDecimal popularity = results.getBigDecimal("Popularity");
				boolean mode = results.getBoolean("Mode");
				int count = results.getInt("Count");
				
				Artists artist = new Artists(resultArtistKey, artistName, acousticness, danceability,
						duration, energy, instramentalness, liveness, loudness, speechiness, tempo,
						valence, popularity, mode, count);
				return artist;
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
	
	public List<Artists> getArtistsByYear(int year) throws SQLException {
		List<Artists> artists = new ArrayList<>();
		String selectArtist =
				"SELECT Artists.ArtistKey, Artists.ArtistName, Artists.Acousticness,"
				+ "Artists.Danceability, Artists.Duration, Artists.Energy,"
				+ "	Artists.Instrumentalness, Artists.Liveness, Artists.Loudness, Artists.Speechiness,"
				+ "	Artists.Tempo, Artists.Valence, Artists.Popularity, Artists.Mode, Artists.Count "
				+ "FROM Songs "
				+ "INNER JOIN SongOwnership on Songs.SongID = SongOwnership.SongID "
				+ "INNER JOIN Artists on SongOwnership.ArtistKey = Artists.ArtistKey "
				+ "WHERE Year = ? "
				+ "GROUP BY Artists.ArtistKey, Songs.Year "
				+ "ORDER BY SUM(Songs.Popularity) DESC;";
				//+ "ORDER BY SUM(Songs.Popularity) DESC "
				//+ "LIMIT 10;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectArtist);
			selectStmt.setInt(1, year);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int artistKey = results.getInt("ArtistKey");
				String artistName = results.getString("ArtistName");
				BigDecimal acousticness = results.getBigDecimal("Acousticness");
				BigDecimal danceability = results.getBigDecimal("Danceability");
				BigDecimal duration = results.getBigDecimal("Duration");
				BigDecimal energy = results.getBigDecimal("Energy");
				BigDecimal instramentalness = results.getBigDecimal("Instrumentalness");
				BigDecimal liveness = results.getBigDecimal("Liveness");
				BigDecimal loudness = results.getBigDecimal("Loudness");
				BigDecimal speechiness = results.getBigDecimal("Speechiness");
				BigDecimal tempo = results.getBigDecimal("Tempo");
				BigDecimal valence = results.getBigDecimal("Valence");
				BigDecimal popularity = results.getBigDecimal("Popularity");
				boolean mode = results.getBoolean("Mode");
				int count = results.getInt("Count");
				
				Artists artist = new Artists(artistKey, artistName, acousticness, danceability,
						duration, energy, instramentalness, liveness, loudness, speechiness, tempo,
						valence, popularity, mode, count);
				artists.add(artist);
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
		return artists;
	}
	
	public Artists updateValence(Artists artist, BigDecimal newValence)
		throws SQLException {
		String updateValence = "UPDATE Artists SET Valence=? WHERE ArtistKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateValence);
			updateStmt.setBigDecimal(1, newValence);
			updateStmt.setInt(2, artist.getArtistKey());
			updateStmt.executeUpdate();

			// Update the creditCard parameter before returning to the caller.
			artist.setValence(newValence);
			return artist;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
	
	public Artists delete(Artists artist) throws SQLException {
		String deleteArtist = "DELETE FROM Artists WHERE ArtistKey=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteArtist);
			deleteStmt.setInt(1, artist.getArtistKey());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the BlogComments instance.
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
