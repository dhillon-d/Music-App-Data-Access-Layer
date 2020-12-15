package moody.model;

public class SongOwnership {
	
	protected int songOwnershipId;
	protected Songs song;
	protected Artists artist;
	
	public SongOwnership(int songOwnershipId, Songs song, Artists artist) {
		this.songOwnershipId = songOwnershipId;
		this.song = song;
		this.artist = artist;
	}

	public SongOwnership(Songs song, Artists artist) {
		this.song = song;
		this.artist = artist;
	}

	public int getSongOwnershipId() {
		return songOwnershipId;
	}

	public void setSongOwnershipId(int songOwnershipId) {
		this.songOwnershipId = songOwnershipId;
	}

	public Songs getSong() {
		return song;
	}

	public void setSong(Songs song) {
		this.song = song;
	}

	public Artists getArtist() {
		return artist;
	}

	public void setArtist(Artists artist) {
		this.artist = artist;
	}

}
