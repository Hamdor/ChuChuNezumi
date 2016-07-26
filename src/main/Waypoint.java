package main;
class Waypoint {

  private double latitude;
  private double longitude;

  /**
   */
  public Waypoint() {
    latitude  = 0;
    longitude = 0;
  }

  /**
   */
  public Waypoint(double latitude, double longitude) {
    this.latitude  = latitude;
    this.longitude = longitude;
  }

  /**
  */
  public double getLatitude() { return latitude; }

  /**
   */
  public double getLongitude() { return longitude; }
  
  public double distance(final Waypoint other) {
    // https://stackoverflow.com/questions/639695/how-to-convert-latitude-or-longitude-to-meters
    double R = 6378.137; // Radius of earth in KM
    double dLat = (other.getLatitude() - getLatitude()) * Math.PI / 180;
    double dLon = (other.getLongitude() - getLongitude()) * Math.PI / 180;
    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
        Math.cos(getLatitude() * Math.PI / 180) * Math.cos(other.getLatitude() * Math.PI / 180) *
        Math.sin(dLon/2) * Math.sin(dLon/2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    double d = R * c;
    return d * 1000;
  }
}
