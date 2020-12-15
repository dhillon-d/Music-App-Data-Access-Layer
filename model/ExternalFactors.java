package moody.model;

/**
 * Model that maps a day of a week to its temperature.
 * 
 * TODO: This table is currently for testing purpose. Instead of using some
 * dummy temperature we could store some external endpoint in this table and get
 * the temperature by querying that endpoint.
 */
public class ExternalFactors {

	/**
	 * Day of a week, could be: MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
	 * SATURDAY, SUNDAY
	 * 
	 * TODO maybe use an enum instead?
	 */
	private String day;
	private int temperature;

	public ExternalFactors(String day, int temperature) {
		this.day = day;
		this.temperature = temperature;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		return "ExternalFactors [day=" + day + ", temperature=" + temperature + "]";
	}

}
