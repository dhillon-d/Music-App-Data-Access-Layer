package moody.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moody.model.*;

//Notes - ArtistsDao: getArtistsBySongID
//GetSongsBySongsTitle(search function)
//GetSongByTemperature(String userName) - temporary hashmap, create recommendation(insert)

public class SongsDao {
	protected ConnectionManager connectionManager;

	private static SongsDao instance = null;
	protected SongsDao() {
		connectionManager = new ConnectionManager();
	}
	public static SongsDao getInstance() {
		if(instance == null) {
			instance = new SongsDao();
		}
		return instance;
	}
	public Songs create(Songs song) throws SQLException {
		String insertSong = 
				"INSERT INTO Songs(Acousticness,Danceability,Duration,Energy,Explicit,SongID,Instrumentalness,"
				+ "SongKey,Liveness,Loudness,Mode,Title,Polularity,Speechiness,Tempo,Valence,Year) "
				+ "Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertSong);
			insertStmt.setDouble(1, song.getAcousticness());
			insertStmt.setDouble(2, song.getDanceability());
			insertStmt.setDouble(3, song.getDuration());
			insertStmt.setDouble(4, song.getEnergy());
			insertStmt.setBoolean(5, song.isExplicit());
			insertStmt.setString(6, song.getSongId());
			insertStmt.setDouble(7, song.getInstrumentalness());
			insertStmt.setInt(8, song.getSongKey());
			insertStmt.setDouble(9, song.getLiveness());
			insertStmt.setDouble(10, song.getLoudness());
			insertStmt.setBoolean(11, song.isMode());
			insertStmt.setString(12, song.getTitle());
			insertStmt.setInt(13, song.getPopularity());
			insertStmt.setDouble(14, song.getSpeechiness());
			insertStmt.setDouble(15, song.getTempo());
			insertStmt.setDouble(16, song.getValence());
			insertStmt.setInt(17, song.getYear());
			
			insertStmt.executeUpdate();
			return song;
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
	
	public Songs getSongBySongId(String songId) throws SQLException{
		String selectSong = "SELECT * FROM Songs WHERE SongID = ?";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectSong);
			selectStmt.setString(1, songId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				double acousticness = results.getDouble("Acousticness");
				double danceability = results.getDouble("Danceability");
				double duration = results.getDouble("Duration");
				double energy = results.getDouble("Energy");
				boolean explicit = results.getBoolean("Explicit");
				String resultSongId = results.getString("SongID");
				double instrumentalness = results.getDouble("Instrumentalness");
				int songKey = results.getInt("SongKey");
				double liveness = results.getDouble("Liveness");
				double loudness = results.getDouble("Loudness");
				boolean mode = results.getBoolean("Mode");
				String title = results.getString("Title");
				int popularity = results.getInt("Popularity");
				double speechiness = results.getDouble("Speechiness");
				double tempo = results.getDouble("Tempo");
				double valence = results.getDouble("Valence");
				int year = results.getInt("Year");
				
				Songs song = new Songs(acousticness, danceability, duration, energy, explicit, resultSongId, instrumentalness,
						songKey, liveness, loudness, mode, title, popularity, speechiness, tempo, valence, year);
				return song;
			}
				
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
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
	
	//Might have artists with same artistName, use a hashmap to store the songs of each artist(key: artistKey, value: List of songs)
	public Map<Integer, List<Songs>> getSongsByArtistName(String artistName) throws SQLException {
		artistName = "%" + artistName + "%";
		String selectSongs = 
				"SELECT SELECTED_ARTISTS.*, Songs.* "
				+ "FROM "
				+ "(SELECT ArtistName, ArtistKey "
				+ "FROM Artists WHERE ArtistName LIKE ?) AS SELECTED_ARTISTS "
				+ "INNER JOIN SongOwnership ON SongOwnership.ArtistKey = SELECTED_ARTISTS.ArtistKey "
				+ "INNER JOIN Songs ON SongOwnership.SongID = Songs.SongID;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		Map<Integer, List<Songs>> songsByArtist = new HashMap<>();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectSongs);
			selectStmt.setString(1, artistName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				// Get the artist
				int artistKey = results.getInt("ArtistKey");
//				String resultArtistName = results.getString("ArtistName");
				//Get a song
				double acousticness = results.getDouble("Acousticness");
				double danceability = results.getDouble("Danceability");
				double duration = results.getDouble("Duration");
				double energy = results.getDouble("Energy");
				boolean explicit = results.getBoolean("Explicit");
				String songId = results.getString("SongID");
				double instrumentalness = results.getDouble("Instrumentalness");
				int songKey = results.getInt("SongKey");
				double liveness = results.getDouble("Liveness");
				double loudness = results.getDouble("Loudness");
				boolean mode = results.getBoolean("Mode");
				String title = results.getString("Title");
				int popularity = results.getInt("Popularity");
				double speechiness = results.getDouble("Speechiness");
				double tempo = results.getDouble("Tempo");
				double valence = results.getDouble("Valence");
				int year = results.getInt("Year");
				Songs song = new Songs(acousticness, danceability, duration, energy, explicit, songId, instrumentalness,
						songKey, liveness, loudness, mode, title, popularity, speechiness, tempo, valence, year);
				
				if(!songsByArtist.containsKey(artistKey)) {
					songsByArtist.put(artistKey, new ArrayList<Songs>());
				}
				songsByArtist.get(artistKey).add(song);
			}
			return songsByArtist;
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
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
	}

	public List<Songs> getTop100Songs() throws SQLException {
		String selectSongs = 
				"SELECT * FROM Songs "
				+ "ORDER BY Popularity DESC "
				+ "LIMIT 100;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Songs> topSongs = new ArrayList<>();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectSongs);
			results = selectStmt.executeQuery();
			while(results.next()) {
				double acousticness = results.getDouble("Acousticness");
				double danceability = results.getDouble("Danceability");
				double duration = results.getDouble("Duration");
				double energy = results.getDouble("Energy");
				boolean explicit = results.getBoolean("Explicit");
				String songId = results.getString("SongID");
				double instrumentalness = results.getDouble("Instrumentalness");
				int songKey = results.getInt("SongKey");
				double liveness = results.getDouble("Liveness");
				double loudness = results.getDouble("Loudness");
				boolean mode = results.getBoolean("Mode");
				String title = results.getString("Title");
				int popularity = results.getInt("Popularity");
				double speechiness = results.getDouble("Speechiness");
				double tempo = results.getDouble("Tempo");
				double valence = results.getDouble("Valence");
				int year = results.getInt("Year");
				Songs song = new Songs(acousticness, danceability, duration, energy, explicit, songId, instrumentalness,
						songKey, liveness, loudness, mode, title, popularity, speechiness, tempo, valence, year);
				topSongs.add(song);
			}
			return topSongs;
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
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
	}
	public List<Songs> getTop100SongsOfYear(int year) throws SQLException {
		String selectSongs = 
				"SELECT * FROM Songs "
				+ "WHERE Year=? "
				+ "ORDER BY Popularity DESC "
				+ "LIMIT 100;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Songs> topSongs = new ArrayList<>();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectSongs);
			selectStmt.setInt(1, year);
			results = selectStmt.executeQuery();
			while(results.next()) {
				double acousticness = results.getDouble("Acousticness");
				double danceability = results.getDouble("Danceability");
				double duration = results.getDouble("Duration");
				double energy = results.getDouble("Energy");
				boolean explicit = results.getBoolean("Explicit");
				String songId = results.getString("SongID");
				double instrumentalness = results.getDouble("Instrumentalness");
				int songKey = results.getInt("SongKey");
				double liveness = results.getDouble("Liveness");
				double loudness = results.getDouble("Loudness");
				boolean mode = results.getBoolean("Mode");
				String title = results.getString("Title");
				int popularity = results.getInt("Popularity");
				double speechiness = results.getDouble("Speechiness");
				double tempo = results.getDouble("Tempo");
				double valence = results.getDouble("Valence");
				int resultYear = results.getInt("Year");
				Songs song = new Songs(acousticness, danceability, duration, energy, explicit, songId, instrumentalness,
						songKey, liveness, loudness, mode, title, popularity, speechiness, tempo, valence, resultYear);
				topSongs.add(song);
			}
			return topSongs;
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
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
	}
	public List<Songs> getSongsByTitle(String title) throws SQLException{
		title = "%" + title + "%";
		String selectSongs = "SELECT * FROM Songs WHERE Title LIKE ?";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		List<Songs> songs = new ArrayList<>();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectSongs);
			selectStmt.setString(1, title);
			results = selectStmt.executeQuery();
			while(results.next()) {
				double acousticness = results.getDouble("Acousticness");
				double danceability = results.getDouble("Danceability");
				double duration = results.getDouble("Duration");
				double energy = results.getDouble("Energy");
				boolean explicit = results.getBoolean("Explicit");
				String songId = results.getString("SongID");
				double instrumentalness = results.getDouble("Instrumentalness");
				int songKey = results.getInt("SongKey");
				double liveness = results.getDouble("Liveness");
				double loudness = results.getDouble("Loudness");
				boolean mode = results.getBoolean("Mode");
				String resultTitle = results.getString("Title");
				int popularity = results.getInt("Popularity");
				double speechiness = results.getDouble("Speechiness");
				double tempo = results.getDouble("Tempo");
				double valence = results.getDouble("Valence");
				int resultYear = results.getInt("Year");
				Songs song = new Songs(acousticness, danceability, duration, energy, explicit, songId, instrumentalness,
						songKey, liveness, loudness, mode, resultTitle, popularity, speechiness, tempo, valence, resultYear);
				songs.add(song);
			}
			return songs;	
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
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
		
	}
	public Songs getSongsByWeather(String userName) throws SQLException{
		//Replace with actual hashmap from another class
		Map<Integer, Integer> weeks = new HashMap<>();
		int day = LocalDate.now().getDayOfWeek().getValue();
		
		Date now = new Date();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
        String today = simpleDateformat.format(now);
		// 0:Sunday, 1:Monday, 2:Tuesday etc.
		weeks.put(0, 70);
		weeks.put(1, 60);
		weeks.put(2, 90);
		weeks.put(3, 40);
		weeks.put(4, 30);
		weeks.put(5, 20);
		weeks.put(6, 50);
		
		String selectSong = 
					" SELECT *" 
					+ " FROM Songs"
					+ " WHERE CEILING(? / 25.0) = CEILING(Songs.Valence/0.2)"
					+ " LIMIT 1;";

		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		//Replace with actual UsersDao & RecommendationsDao
		RecommendationDao recommendationDao = RecommendationDao.getInstance();
		UsersDao userDao = UsersDao.getInstance();
		ExternalFactorsDao externalFactorsDao = ExternalFactorsDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectSong);
			ExternalFactors resultExternalFactor = externalFactorsDao.getExternalFactorsFromDay(today);
			int temperature = resultExternalFactor.getTemperature();
			
			selectStmt.setInt(1, temperature);
			results = selectStmt.executeQuery();
			if(results.next()) {
				double acousticness = results.getDouble("Acousticness");
				double danceability = results.getDouble("Danceability");
				double duration = results.getDouble("Duration");
				double energy = results.getDouble("Energy");
				boolean explicit = results.getBoolean("Explicit");
				String songId = results.getString("SongID");
				double instrumentalness = results.getDouble("Instrumentalness");
				int songKey = results.getInt("SongKey");
				double liveness = results.getDouble("Liveness");
				double loudness = results.getDouble("Loudness");
				boolean mode = results.getBoolean("Mode");
				String title = results.getString("Title");
				int popularity = results.getInt("Popularity");
				double speechiness = results.getDouble("Speechiness");
				double tempo = results.getDouble("Tempo");
				double valence = results.getDouble("Valence");
				int year = results.getInt("Year");
				Songs song = new Songs(acousticness, danceability, duration, energy, explicit, songId, instrumentalness,
						songKey, liveness, loudness, mode, title, popularity, speechiness, tempo, valence, year);
				
				//Replace with actual Recommendations
				try {
					Recommendation recommendation = new Recommendation(userDao.getUserFromUsername(userName), song, new Timestamp(System.currentTimeMillis()), temperature);
					recommendation = recommendationDao.create(recommendation);
				} catch (SQLException e) {
					e.printStackTrace();
					return song;
				}
				return song;
			}
			

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
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
	public Songs delete(Songs song) throws SQLException {
		String deleteSong = "DELETE FROM Songs WHERE SongID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteSong);
			deleteStmt.setString(1, song.getSongId());
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
