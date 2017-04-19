package gatech.nav; /**
 * Created by xinyao on 4/16/17.
 */
import java.lang.*;

public class Path {
    private String geton_stop;
    private String getoff_stop;
    private String Route;
    private int total_time;

    public Path(String getonstop, String getoffstop, String route, int time) {
        geton_stop = getonstop;
        getoff_stop = getoffstop;
        Route = route;
        total_time = time;
    }

    public String getGeton_stop () {
        return geton_stop;
    }

    public String getGetoff_stop () {
        return getoff_stop;
    }

    public String getRoute () {
        return Route;
    }

    public int getTotal_time () {
        return total_time;
    }

}
