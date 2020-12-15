package moody.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import moody.model.*;

public class FavoriteSongsDao {
	protected ConnectionManager connectionManager;

	private static FavoriteSongsDao instance = null;
	protected FavoriteSongsDao() {
		connectionManager = new ConnectionManager();
	}
	public static FavoriteSongsDao getInstance() {
		if(instance == null) {
			instance = new FavoriteSongsDao();
		}
		return instance;
	}
	
	public FavoriteSongs create(FavoriteSongs favoriteSong) throws SQLException {
		String insertFavoriteSong = "INSERT INTO FavoriteSongs(UserName, SongID) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertFavoriteSong, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, favoriteSong.getUser().getUsername());
			insertStmt.setString(2, favoriteSong.getSong().getSongId());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int favoriteSongId = -1;
			if(resultKey.next()) {
				favoriteSongId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			favoriteSong.setFavoriteSongId(favoriteSongId);
			return favoriteSong;
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}	
		}
	}
	
	public List<FavoriteSongs> getFavoriteSongsByUserName(String userName) throws SQLException {
		String selectFavoriteSongs = "SELECT * FROM FavoriteSongs WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<FavoriteSongs> favoriteSongs = new ArrayList<>();
		try {
			connection = connectionManager.getConnection();
			UsersDao usersDao = UsersDao.getInstance();
			SongsDao songsDao = SongsDao.getInstance();
			selectStmt = connection.prepareStatement(selectFavoriteSongs);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int favoriteSongId = results.getInt("FavoriteSongID");
				Users user = usersDao.getUserFromUsername(results.getString("UserName"));
				Songs song = songsDao.getSongBySongId(results.getString("SongId"));
				FavoriteSongs favoriteSong = new FavoriteSongs(favoriteSongId, user, song);
				favoriteSongs.add(favoriteSong);
			}
			return favoriteSongs;
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results!= null) {
				results.close();
			}
		}
	}
	
	//UpdateSongId
	public FavoriteSongs updateSong(FavoriteSongs favoriteSong, Songs song) throws SQLException{
		String updateFavoriteSong = "UPDATE FavoriteSongs SET SongID=? WHERE FavoriteSongID=?";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateFavoriteSong);
			updateStmt.setString(1, song.getSongId());
			updateStmt.setInt(2, favoriteSong.getFavoriteSongId());
			updateStmt.executeUpdate();
			
			favoriteSong.setSong(song);
			return favoriteSong;
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
	public FavoriteSongs delete(FavoriteSongs favoriteSong) throws SQLException{
		String deleteFavoriteSong = "DELETE FROM FavoriteSongs WHERE FavoriteSongId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteFavoriteSong);
			deleteStmt.setInt(1, favoriteSong.getFavoriteSongId());
			deleteStmt.executeUpdate();
			return null;
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
}
