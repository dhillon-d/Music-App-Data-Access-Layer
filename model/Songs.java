package moody.model;

public class Songs {
	protected double acousticness;
	protected double danceability;
	protected double duration;
	protected double energy;
	protected boolean explicit;
	protected String songId;
	protected double instrumentalness;
	protected int songKey;
	protected double liveness;
	protected double loudness;
	protected boolean mode;
	protected String title;
	protected int popularity;
	protected double speechiness;
	protected double tempo;
	protected double valence;
	protected int year;
	public Songs(double acousticness, double danceability, double duration, double energy, boolean explicit,
			String songId, double instrumentalness, int songKey, double liveness, double loudness, boolean mode,
			String title, int popularity, double speechiness, double tempo, double valence, int year) {
		this.acousticness = acousticness;
		this.danceability = danceability;
		this.duration = duration;
		this.energy = energy;
		this.explicit = explicit;
		this.songId = songId;
		this.instrumentalness = instrumentalness;
		this.songKey = songKey;
		this.liveness = liveness;
		this.loudness = loudness;
		this.mode = mode;
		this.title = title;
		this.popularity = popularity;
		this.speechiness = speechiness;
		this.tempo = tempo;
		this.valence = valence;
		this.year = year;
	}
	public double getAcousticness() {
		return acousticness;
	}
	public void setAcousticness(double acousticness) {
		this.acousticness = acousticness;
	}
	public double getDanceability() {
		return danceability;
	}
	public void setDanceability(double danceability) {
		this.danceability = danceability;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public double getEnergy() {
		return energy;
	}
	public void setEnergy(double energy) {
		this.energy = energy;
	}
	public boolean isExplicit() {
		return explicit;
	}
	public void setExplicit(boolean explicit) {
		this.explicit = explicit;
	}
	public String getSongId() {
		return songId;
	}
	public void setSongId(String songId) {
		this.songId = songId;
	}
	public double getInstrumentalness() {
		return instrumentalness;
	}
	public void setInstrumentalness(double instrumentalness) {
		this.instrumentalness = instrumentalness;
	}
	public int getSongKey() {
		return songKey;
	}
	public void setSongKey(int songKey) {
		this.songKey = songKey;
	}
	public double getLiveness() {
		return liveness;
	}
	public void setLiveness(double liveness) {
		this.liveness = liveness;
	}
	public double getLoudness() {
		return loudness;
	}
	public void setLoudness(double loudness) {
		this.loudness = loudness;
	}
	public boolean isMode() {
		return mode;
	}
	public void setMode(boolean mode) {
		this.mode = mode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPopularity() {
		return popularity;
	}
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	public double getSpeechiness() {
		return speechiness;
	}
	public void setSpeechiness(double speechiness) {
		this.speechiness = speechiness;
	}
	public double getTempo() {
		return tempo;
	}
	public void setTempo(double tempo) {
		this.tempo = tempo;
	}
	public double getValence() {
		return valence;
	}
	public void setValence(double valence) {
		this.valence = valence;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	
	
}
