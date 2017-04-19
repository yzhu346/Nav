package gatech.nav;

import java.lang.*;

/**
 * Created by xinyao on 4/4/17.
 */
public class BusStop {
    private String stopid;
    //private String stopname;
    private Location location;

    public BusStop(String id, Location loc) {
        stopid = id;
        //stopname = name;
        location = loc;
        //System.out.println("construction completed: " + name );
    }

    public String getStopid () {return stopid;};

    //public String getStopname () {
        //return stopname;
    //}

    public Location getLocation() {
        return location;
    }

}
