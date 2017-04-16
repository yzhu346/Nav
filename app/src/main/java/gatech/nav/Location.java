package gatech.nav; /**
 * Created by xinyao on 4/4/17.
 */
import java.lang.*;

public class Location {
    private String longitude;
    private String latitude;

    public Location (String lat, String lon) {
        latitude = lat;
        longitude = lon;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }
}
