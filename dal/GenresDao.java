package moody.dal;

import moody.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data access object (DAO) class to interact with the underlying Genres table
 * in your MySQL instance. This is used to store {@link Genres} into your MySQL
 * instance and retrieve {@link Genres} from MySQL instance.
 */
public class GenresDao {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.
	private static GenresDao instance = null;

	protected GenresDao() {
		connectionManager = new ConnectionManager();
	}

	public static GenresDao getInstance() {
		if (instance == null) {
			instance = new GenresDao();
		}
		return instance;
	}

	/**
	 * Save the Genres instance by storing it in your MySQL instance. This runs a
	 * INSERT statement.
	 */
	public Genres create(Genres genres) throws SQLException {
		String insertGenres = "INSERT INTO Genres(GenreName,Acousticness,Danceability,Duration,Energy,Instrumentalness,Liveness,Loudness,Speechiness,Tempo,Valence,Popularity,GenreKey,Mode) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertGenres);
			insertStmt.setString(1, genres.getGenreName());
			insertStmt.setDouble(2, genres.getAcousticness());
			insertStmt.setDouble(3, genres.getDanceability());
			insertStmt.setDouble(4, genres.getDuration());
			insertStmt.setDouble(5, genres.getEnergy());
			insertStmt.setDouble(6, genres.getInstrumentalness());
			insertStmt.setDouble(7, genres.getLiveness());
			insertStmt.setDouble(8, genres.getLoudness());
			insertStmt.setDouble(9, genres.getSpeechiness());
			insertStmt.setDouble(10, genres.getTempo());
			insertStmt.setDouble(11, genres.getValence());
			insertStmt.setDouble(12, genres.getPopularity());
			insertStmt.setInt(13, genres.getGenreKey());
			insertStmt.setBoolean(14, genres.getMode());
			insertStmt.executeUpdate();
			return genres;
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
	 * Get the Genres record by fetching it from your MySQL instance. This runs a
	 * SELECT statement and returns a single Genres instance.
	 */
	public Genres getGenresFromGenreName(String genreName) throws SQLException {
		String selectGenres = "SELECT GenreName,Acousticness,Danceability,Duration,Energy,Instrumentalness,Liveness,Loudness,Speechiness,Tempo,Valence,Popularity,GenreKey,Mode "
				+ "FROM Genres WHERE GenreName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGenres);
			selectStmt.setString(1, genreName);
			results = selectStmt.executeQuery();
			if (results.next()) {
				String retGenreName = results.getString("GenreName");
				double acousticness = results.getDouble("Acousticness");
				double danceability = results.getDouble("Danceability");
				double duration = results.getDouble("Duration");
				double energy = results.getDouble("Energy");
				double instrumentalness = results.getDouble("Instrumentalness");
				double liveness = results.getDouble("Liveness");
				double loudness = results.getDouble("Loudness");
				double speechiness = results.getDouble("Speechiness");
				double tempo = results.getDouble("Tempo");
				double valence = results.getDouble("Valence");
				double popularity = results.getDouble("Popularity");
				int genreKey = results.getInt("GenreKey");
				boolean mode = results.getBoolean("Mode");
				Genres genres = new Genres(retGenreName, acousticness, danceability, duration, energy, instrumentalness,
						liveness, loudness, speechiness, tempo, valence, popularity, genreKey, mode);
				return genres;
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
	 * Delete the Genres instance. This runs a DELETE statement.
	 */
	public Genres delete(Genres genres) throws SQLException {
		String deleteGenres = "DELETE FROM Genres WHERE GenreName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteGenres);
			deleteStmt.setString(1, genres.getGenreName());
			deleteStmt.executeUpdate();
			// Return null so the caller can no longer operate on the Genres instance.
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
