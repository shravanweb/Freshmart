import LocationResult from "./LocationResult";
import { getLocation } from "./LocationResult";

export default class LocationHelper {
  static async getCurrentLocation(): Promise<LocationResult> {
    try {
      const location = await getLocation();
      return location;
    } catch (error) {
      console.error(error);
      return error;
    }
  }
}
