package moody.model;

import java.sql.Timestamp;

public class Recommendation {
	protected int recommendationId;
	protected Users user;
	protected Songs song;
	protected Timestamp created;
	protected double temperature;
	
	public Recommendation(int recommendationId, Users user, Songs song, Timestamp created, double temperature) {
		this.recommendationId = recommendationId;
		this.user = user;
		this.song = song;
		this.created = created;
		this.temperature = temperature;
	}
	
	public Recommendation(Users user, Songs song, Timestamp created, double temperature) {
		this.user = user;
		this.song = song;
		this.created = created;
		this.temperature = temperature;
	}
	
	/** Getters and setters. */

	public int getRecommendationId() {
		return recommendationId;
	}

	public void setRecommendationId(int recommendationId) {
		this.recommendationId = recommendationId;
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

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
}