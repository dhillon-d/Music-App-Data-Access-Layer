package moody.model;

public class FavoriteSongs {
	protected int favoriteSongId;
	protected Users user;
	protected Songs song;
	
	public FavoriteSongs(int favoriteSongId, Users user, Songs song) {
		this.favoriteSongId = favoriteSongId;
		this.user = user;
		this.song = song;
	}
	
	// Leave auto-generated favoriteSongId blank
	public FavoriteSongs(Users user, Songs song) {
		this.user = user;
		this.song = song;
	}
	
	public int getFavoriteSongId() {
		return favoriteSongId;
	}

	public void setFavoriteSongId(int favoriteSongId) {
		this.favoriteSongId = favoriteSongId;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Songs getSong() {
		return song;
	}

	public void setSong(Songs song) {
		this.song = song;
	}
	
	
	
	
}
