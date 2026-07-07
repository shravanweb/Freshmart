export default class LocationResult {
  success?: boolean;
  error?: string;
  latitude?: number;
  longitude?: number;
}

export function getLocation(): Promise<LocationResult> {
  return new Promise((resolve, reject) => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          resolve({
            success: true,
            latitude: position.coords.latitude,
            longitude: position.coords.longitude,
          });
        },
        (error) => {
          reject({
            success: false,
            error: error.message,
          });
        }
      );
    } else {
      reject({
        error: "Geolocation is not supported by this browser.",
      });
    }
  });
}
