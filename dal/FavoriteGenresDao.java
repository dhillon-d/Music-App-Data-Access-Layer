package moody.dal;

import moody.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Data access object (DAO) class to interact with the underlying Genres table
 * in your MySQL instance. This is used to store {@link FavoriteGenres} into
 * your MySQL instance and retrieve {@link FavoriteGenres} from MySQL instance.
 */
public class FavoriteGenresDao {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.
	private static FavoriteGenresDao instance = null;

	protected FavoriteGenresDao() {
		connectionManager = new ConnectionManager();
	}

	public static FavoriteGenresDao getInstance() {
		if (instance == null) {
			instance = new FavoriteGenresDao();
		}
		return instance;
	}

	/**
	 * Save the FavoriteGenres instance by storing it in your MySQL instance. This
	 * runs a INSERT statement.
	 */
	public FavoriteGenres create(FavoriteGenres favGenres) throws SQLException {
		String insertGenres = "INSERT INTO FavoriteGenres(UserName,GenreName) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertGenres, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, favGenres.getUserName());
			insertStmt.setString(2, favGenres.getGenreName());
			insertStmt.executeUpdate();

			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int favoriteGenreID = -1;
			if (resultKey.next()) {
				favoriteGenreID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			favGenres.setFavoriteGenreID(favoriteGenreID);
			return favGenres;
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
	 * Get the FavoriteGenres record by fetching it from your MySQL instance. This
	 * runs a SELECT statement and returns a single FavoriteGenres instance.
	 */
	public FavoriteGenres getFavoriteGenresFromId(int id) throws SQLException {
		String selectFavGenres = "SELECT FavoriteGenreID,UserName,GenreName "
				+ "FROM FavoriteGenres WHERE FavoriteGenreID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFavGenres);
			selectStmt.setInt(1, id);
			results = selectStmt.executeQuery();
			if (results.next()) {
				int favGenreId = results.getInt("FavoriteGenreID");
				String userName = results.getString("UserName");
				String genreName = results.getString("GenreName");
				FavoriteGenres favGenres = new FavoriteGenres(favGenreId, userName, genreName);
				return favGenres;
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
	 * Delete the FavoriteGenres instance. This runs a DELETE statement.
	 */
	public FavoriteGenres delete(FavoriteGenres genres) throws SQLException {
		String deleteFavGenres = "DELETE FROM FavoriteGenres WHERE FavoriteGenreID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteFavGenres);
			deleteStmt.setInt(1, genres.getFavoriteGenreID());
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
