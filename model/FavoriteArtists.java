package moody.model;

public class FavoriteArtists {
	
	protected int favoriteArtistId;
	protected Artists artist;
	protected Users user;
	
	public FavoriteArtists(int favoriteArtistId, Artists artist, Users user) {
		this.favoriteArtistId = favoriteArtistId;
		this.artist = artist;
		this.user = user;
	}

	public FavoriteArtists(Artists artist, Users user) {
		this.artist = artist;
		this.user = user;
	}

	public int getFavoriteArtistId() {
		return favoriteArtistId;
	}

	public void setFavoriteArtistId(int favoriteArtistId) {
		this.favoriteArtistId = favoriteArtistId;
	}

	public Artists getArtist() {
		return artist;
	}

	public void setArtist(Artists artist) {
		this.artist = artist;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
}
