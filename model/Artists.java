package moody.model;

import java.math.BigDecimal;

public class Artists {
	
	protected int artistKey;
	protected String artistName;
	protected BigDecimal acousticness;
	protected BigDecimal danceability;
	protected BigDecimal duration;
	protected BigDecimal energy;
	protected BigDecimal instrumentalness;
	protected BigDecimal liveness;
	protected BigDecimal loudness;
	protected BigDecimal speechiness;
	protected BigDecimal tempo;
	protected BigDecimal valence;
	protected BigDecimal popularity;
	protected boolean mode;
	protected int count;

	public Artists(int artistKey, String artistName, BigDecimal acousticness, BigDecimal danceability,
			BigDecimal duration, BigDecimal energy, BigDecimal instrumentalness, BigDecimal liveness,
			BigDecimal loudness, BigDecimal speechiness, BigDecimal tempo, BigDecimal valence, BigDecimal popularity,
			boolean mode, int count) {
		this.artistKey = artistKey;
		this.artistName = artistName;
		this.acousticness = acousticness;
		this.danceability = danceability;
		this.duration = duration;
		this.energy = energy;
		this.instrumentalness = instrumentalness;
		this.liveness = liveness;
		this.loudness = loudness;
		this.speechiness = speechiness;
		this.tempo = tempo;
		this.valence = valence;
		this.popularity = popularity;
		this.mode = mode;
		this.count = count;
	}

	public Artists(String artistName, BigDecimal acousticness, BigDecimal danceability, BigDecimal duration,
			BigDecimal energy, BigDecimal instrumentalness, BigDecimal liveness, BigDecimal loudness,
			BigDecimal speechiness, BigDecimal tempo, BigDecimal valence, BigDecimal popularity, boolean mode,
			int count) {
		this.artistName = artistName;
		this.acousticness = acousticness;
		this.danceability = danceability;
		this.duration = duration;
		this.energy = energy;
		this.instrumentalness = instrumentalness;
		this.liveness = liveness;
		this.loudness = loudness;
		this.speechiness = speechiness;
		this.tempo = tempo;
		this.valence = valence;
		this.popularity = popularity;
		this.mode = mode;
		this.count = count;
	}

	public int getArtistKey() {
		return artistKey;
	}

	public void setArtistKey(int artistKey) {
		this.artistKey = artistKey;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public BigDecimal getAcousticness() {
		return acousticness;
	}

	public void setAcousticness(BigDecimal acousticness) {
		this.acousticness = acousticness;
	}

	public BigDecimal getDanceability() {
		return danceability;
	}

	public void setDanceability(BigDecimal danceability) {
		this.danceability = danceability;
	}

	public BigDecimal getDuration() {
		return duration;
	}

	public void setDuration(BigDecimal duration) {
		this.duration = duration;
	}

	public BigDecimal getEnergy() {
		return energy;
	}

	public void setEnergy(BigDecimal energy) {
		this.energy = energy;
	}

	public BigDecimal getInstrumentalness() {
		return instrumentalness;
	}

	public void setInstrumentalness(BigDecimal instrumentalness) {
		this.instrumentalness = instrumentalness;
	}

	public BigDecimal getLiveness() {
		return liveness;
	}

	public void setLiveness(BigDecimal liveness) {
		this.liveness = liveness;
	}

	public BigDecimal getLoudness() {
		return loudness;
	}

	public void setLoudness(BigDecimal loudness) {
		this.loudness = loudness;
	}

	public BigDecimal getSpeechiness() {
		return speechiness;
	}

	public void setSpeechiness(BigDecimal speechiness) {
		this.speechiness = speechiness;
	}

	public BigDecimal getTempo() {
		return tempo;
	}

	public void setTempo(BigDecimal tempo) {
		this.tempo = tempo;
	}

	public BigDecimal getValence() {
		return valence;
	}

	public void setValence(BigDecimal valence) {
		this.valence = valence;
	}

	public BigDecimal getPopularity() {
		return popularity;
	}

	public void setPopularity(BigDecimal popularity) {
		this.popularity = popularity;
	}

	public boolean isMode() {
		return mode;
	}

	public void setMode(boolean mode) {
		this.mode = mode;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
