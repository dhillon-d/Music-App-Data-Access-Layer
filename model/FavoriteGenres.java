package moody.model;

/**
 * Model that represents the FavoriteGenres table in the database.
 *
 */
public class FavoriteGenres {

	private int favoriteGenreID;
	private String userName;
	private String genreName;

	public FavoriteGenres(int favoriteGenreID, String userName, String genreName) {
		this.favoriteGenreID = favoriteGenreID;
		this.userName = userName;
		this.genreName = genreName;
	}

	public FavoriteGenres(String userName, String genreName) {
		this.userName = userName;
		this.genreName = genreName;
	}

	public int getFavoriteGenreID() {
		return favoriteGenreID;
	}

	public void setFavoriteGenreID(int favoriteGenreID) {
		this.favoriteGenreID = favoriteGenreID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	@Override
	public String toString() {
		return "FavoriteGenres [favoriteGenreID=" + favoriteGenreID + ", userName=" + userName + ", genreName="
				+ genreName + "]";
	}

}
