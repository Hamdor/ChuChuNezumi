package main;

import java.util.ArrayList;
import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.auth.GoogleLogin;
import okhttp3.OkHttpClient;

class Starter {

  public static ArrayList<Waypoint> generateWPs() {
    ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
    waypoints.add(new Waypoint(53.4599405, 9.9288308));
    waypoints.add(new Waypoint(53.45990217, 9.92915267));
    waypoints.add(new Waypoint(53.45990217, 9.92943161));
    waypoints.add(new Waypoint(53.4598383, 9.92975348));
    waypoints.add(new Waypoint(53.45978719, 9.93007534));
    waypoints.add(new Waypoint(53.45978719, 9.93007534));
    waypoints.add(new Waypoint(53.45976164, 9.93023091));
    waypoints.add(new Waypoint(53.45974567, 9.93037575));
    waypoints.add(new Waypoint(53.45973609, 9.93055278));
    waypoints.add(new Waypoint(53.45973609, 9.93078881));
    waypoints.add(new Waypoint(53.45973928, 9.93105167));
    waypoints.add(new Waypoint(53.45976164, 9.93126088));
    waypoints.add(new Waypoint(53.45980316, 9.93144327));
    waypoints.add(new Waypoint(53.45984788, 9.93165248));
    waypoints.add(new Waypoint(53.45991175, 9.93188315));
    waypoints.add(new Waypoint(53.45992134, 9.93208164));
    waypoints.add(new Waypoint(53.45996605, 9.93236059));
    waypoints.add(new Waypoint(53.46001396, 9.93255907));
    waypoints.add(new Waypoint(53.46006506, 9.93277901));
    waypoints.add(new Waypoint(53.46010339, 9.93296677));
    waypoints.add(new Waypoint(53.46012894, 9.93313843));
    waypoints.add(new Waypoint(53.46014491, 9.93330472));
    waypoints.add(new Waypoint(53.46017685, 9.93359977));
    waypoints.add(new Waypoint(53.46021517, 9.93383044));
    waypoints.add(new Waypoint(53.46021837, 9.93394845));
    waypoints.add(new Waypoint(53.46024072, 9.93414694));
    waypoints.add(new Waypoint(53.46025989, 9.93431324));
    waypoints.add(new Waypoint(53.46028863, 9.93445807));
    return waypoints;
  }
  
  public static ArrayList<Waypoint> generateWPs2() {
    ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
    waypoints.add(new Waypoint(53.46525012, 9.96259332));
    waypoints.add(new Waypoint(53.46522457, 9.9624002));
    waypoints.add(new Waypoint(53.4651607, 9.96214271));
    waypoints.add(new Waypoint(53.46509683, 9.96186376));
    waypoints.add(new Waypoint(53.46505851, 9.96162772));
    waypoints.add(new Waypoint(53.46502019, 9.9614346));
    waypoints.add(new Waypoint(53.46495632, 9.96119857));
    waypoints.add(new Waypoint(53.46490522, 9.9608767));
    waypoints.add(new Waypoint(53.46485413, 9.96061921));
    waypoints.add(new Waypoint(53.46479025, 9.96036172));
    waypoints.add(new Waypoint(53.46475193, 9.96010423));
    waypoints.add(new Waypoint(53.46471361, 9.95988965));
    waypoints.add(new Waypoint(53.46464974, 9.95954633));
    waypoints.add(new Waypoint(53.46462419, 9.95928884));
    waypoints.add(new Waypoint(53.46457309, 9.95903134));
    waypoints.add(new Waypoint(53.464522, 9.95883822));
    waypoints.add(new Waypoint(53.46448368, 9.95866656));
    waypoints.add(new Waypoint(53.4644198, 9.95836616));
    waypoints.add(new Waypoint(53.46439426, 9.95815158));
    waypoints.add(new Waypoint(53.46435593, 9.957937));
    waypoints.add(new Waypoint(53.46430484, 9.95770097));
    waypoints.add(new Waypoint(53.46426651, 9.95748639));
    waypoints.add(new Waypoint(53.46422819, 9.95716453));
    waypoints.add(new Waypoint(53.46417709, 9.95692849));
    waypoints.add(new Waypoint(53.46413877, 9.95673537));
    waypoints.add(new Waypoint(53.46411322, 9.95654225));
    waypoints.add(new Waypoint(53.46408767, 9.95634913));
    waypoints.add(new Waypoint(53.46404935, 9.95613456));
    waypoints.add(new Waypoint(53.46399825, 9.95579123));
    waypoints.add(new Waypoint(53.46392161, 9.95557666));
    return waypoints;
  }
  
 
  public static ArrayList<Waypoint> interpolate(Waypoint start, Waypoint end/*, double resolution*/) {
    double distance = start.distance(end);
    System.out.println("Distance: " + distance);
    double res = 100 / distance;
    ArrayList<Waypoint> wps = new ArrayList<Waypoint>();
    for (double d = 0; d < 1.0; d += res) {
      // V1 + d * v2
      double lon1 = start.getLongitude();
      double lon2 = end.getLongitude();
      double lat1 = start.getLatitude();
      double lat2 = end.getLatitude();
      wps.add(new Waypoint(lat1 + d * lat2, lon1 + d * lon2));
    }
    return wps; 
  }

  public static void main(String[] args) {
    // TODO: Wenn token kaputt, argument bei login raus, neues token generieren
    // und hier rein pasten :-)
    
    /*ArrayList<Waypoint> wps = interpolate(new Waypoint(53.46535026, 9.96309006), new Waypoint(53.46108355, 9.94079554));
    for (Waypoint w : wps)
      System.out.println(w.getLatitude() + " " + w.getLongitude());*/
    
    String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjBiZDEwY2JmMDM2OGQ2MWE0NDBiZjYxZjNiM2EyZDI0NGExODQ5NDcifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhdF9oYXNoIjoiVlNSRzNHN2ZZSWNDdm1UQnh5MFJPQSIsImF1ZCI6Ijg0ODIzMjUxMTI0MC03M3JpM3Q3cGx2azk2cGo0Zjg1dWo4b3RkYXQyYWxlbS5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjEwODMwNjQyMTg0MjI2OTY3OTY0MyIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhenAiOiI4NDgyMzI1MTEyNDAtNzNyaTN0N3Bsdms5NnBqNGY4NXVqOG90ZGF0MmFsZW0uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJlbWFpbCI6InNpbGxpYXRoaWFzQGdvb2dsZW1haWwuY29tIiwiaWF0IjoxNDY5NTI2NjU0LCJleHAiOjE0Njk1MzAyNTR9.gwuMTm4w0KcsRxQsF2rUa4mC1NDrqDdojAFSB-pBg47hhfTvIZk_DTgG-y2tg2Z6H73Fjxd82iTFqN1iU11fmqRMiEvs1btc_NpXZM9A40TnzswkmyW3xiwMiCSakbOlpnts4mrxl186s2G4M8GLC-mlkwXevquQIBAqjRDAcDyr_RHJT0VHTYsVxC9iFLWuGrvMuMYvS50pJq2tIwQhIbJxQR5B3Zqbi0ei5S9Hkm0tQIZsruDTwfi3glvbkCkYqOf9Agah0nUWwvm30DD53Le0Ip56a2FHDwwSp64RzeSK-u0lGU8R6BCO5QFWzaBpKanjhzAFK7KtErUuuVw4uw";
    OkHttpClient http = new OkHttpClient();
    try {
      PokemonGo go = new PokemonGo(new GoogleLogin(http).login(), http);
      Bot ash_ketchum = new Bot(go, new Walker(generateWPs2(), 3, 5, go));
      ash_ketchum.run();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }
}
