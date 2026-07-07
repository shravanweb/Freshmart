class Geolocation {
  latitude: number;
  longitude: number;

  constructor(latitude: number, longitude: number) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  distanceTo(other: Geolocation): number {
    if (
      this.latitude != null &&
      this.longitude != null &&
      other.latitude != null &&
      other.longitude != null
    ) {
      const earthRadius = 6371000; // meters
      const dLat = (other.latitude - this.latitude) * (Math.PI / 180);
      const dLon = (other.longitude - this.longitude) * (Math.PI / 180);

      const a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(this.latitude * (Math.PI / 180)) *
          Math.cos(other.latitude * (Math.PI / 180)) *
          Math.sin(dLon / 2) *
          Math.sin(dLon / 2);
      const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
      const distance = earthRadius * c;
      return distance;
    }
    return 0.0;
  }

  static async fromPincode(pincode: string): Promise<Geolocation | null> {
    const apiUrl = `https://nominatim.openstreetmap.org/search?q=${pincode}&format=json`;
    try {
      const response = await fetch(apiUrl);
      if (response.ok) {
        const data = await response.json();
        const lat = parseFloat(data[0].lat);
        const lon = parseFloat(data[0].lon);
        return new Geolocation(lat, lon);
      } else {
        throw new Error("Failed to load data");
      }
    } catch (error) {
      console.error("Error:", error);
      return null;
    }
  }
}
export default Geolocation;
