package moody.model;

/**
 * Model that represents the Genres table in the database.
 *
 */
public class Genres {

	private String genreName;
	private double acousticness;
	private double danceability;
	private double duration;
	private double energy;
	private double instrumentalness;
	private double liveness;
	private double loudness;
	private double speechiness;
	private double tempo;
	private double valence;
	private double popularity;
	private int genreKey;
	private boolean mode;

	public Genres(String genreName, double acousticness, double danceability, double duration, double energy,
			double instrumentalness, double liveness, double loudness, double speechiness, double tempo, double valence,
			double popularity, int genreKey, boolean mode) {
		this.genreName = genreName;
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
		this.genreKey = genreKey;
		this.mode = mode;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
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

	public double getInstrumentalness() {
		return instrumentalness;
	}

	public void setInstrumentalness(double instrumentalness) {
		this.instrumentalness = instrumentalness;
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

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public int getGenreKey() {
		return genreKey;
	}

	public void setGenreKey(int genreKey) {
		this.genreKey = genreKey;
	}

	public boolean getMode() {
		return mode;
	}

	public void setMode(boolean mode) {
		this.mode = mode;
	}

	@Override
	public String toString() {
		return "Genres [genreName=" + genreName + ", acousticness=" + acousticness + ", danceability=" + danceability
				+ ", duration=" + duration + ", energy=" + energy + ", instrumentalness=" + instrumentalness
				+ ", liveness=" + liveness + ", loudness=" + loudness + ", speechiness=" + speechiness + ", tempo="
				+ tempo + ", valence=" + valence + ", popularity=" + popularity + ", genreKey=" + genreKey + ", mode="
				+ mode + "]";
	}

}
