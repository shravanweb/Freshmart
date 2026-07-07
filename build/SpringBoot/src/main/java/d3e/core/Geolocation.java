package d3e.core;

public class Geolocation {

	private double latitude;
	private double longitude;

	public Geolocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double distanceTo(Geolocation other) {
		if (other != null) {
			final double earthRadius = 6371000; // meters
			final double dLat = (other.latitude - latitude) * (3.141592653589793 / 180);
			final double dLon = (other.longitude - longitude) * (3.141592653589793 / 180);

			final double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(latitude * (3.141592653589793 / 180))
					* Math.cos(other.latitude * (3.141592653589793 / 180)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
			final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			final double distance = earthRadius * c;
			return distance;
		}
		return 0.0;
	}
}
