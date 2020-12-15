package moody.model;

public class Location {
	protected int locationId;
	protected String city;
	protected String state;
	
	public Location(int locationId, String city, String state) {
		this.locationId = locationId;
		this.city = city;
		this.state = state;
	}
	
	public Location(String city, String state) {
		this.city = city;
		this.state = state;
	}
	
	/** Getters and setters. */

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
