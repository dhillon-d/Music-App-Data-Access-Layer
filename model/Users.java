package moody.model;

public class Users {
	protected String username;
	protected String phone;
	protected String email;
	protected Location location;
	
	public Users(String username, String phone, String email, Location location) {
		this.username = username;
		this.phone = phone;
		this.email = email;
		this.location = location;
	}
	
	/** Getters and setters. */

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}

	