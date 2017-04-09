package gatech.nav;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import java.util.LinkedList;


/**
 * Created by yzhu3 on 4/8/2017.
 */

public class Route {
    private final int mExpress_Stop_Number = 4;
    private final int mTrolley_Stop_Number = 21;

    private PolylineOptions[] Express = new PolylineOptions[mExpress_Stop_Number];
    private Polyline[] Express_Polyline = new Polyline[mExpress_Stop_Number];
    private Circle[] Express_Circle = new Circle[mExpress_Stop_Number];
    private LinkedList<String> Express_stop = new LinkedList<>();
    private LinkedList<LatLng> Express_stop_position = new LinkedList<>();

    private PolylineOptions[] Trolley = new PolylineOptions[mTrolley_Stop_Number];
    private Polyline[] Trolley_Polyline = new Polyline[mTrolley_Stop_Number];
    private Circle[] Trolley_Circle = new Circle[mTrolley_Stop_Number];
    private LinkedList<String> Trolley_stop = new LinkedList<>();
    private LinkedList<LatLng> Trolley_stop_position = new LinkedList<>();

    private final int mWIDTH = 10;
    private final int mSTOP_RADIUS = 6;

    public void draw(GoogleMap map){
        for(int i = 0; i < mExpress_Stop_Number ; i++){
            Express_Polyline[i] = map.addPolyline(Express[i]);
            Express_Circle[i] = map.addCircle(new CircleOptions()
                    .center(Express_stop_position.get(i))
                    .radius(mSTOP_RADIUS)
                    .strokeColor(Color.WHITE)
                    .fillColor(Color.GRAY));
        }

        for(int i = 0; i < mTrolley_Stop_Number ; i++){
            //Trolley_Polyline[i] = map.addPolyline(Trolley[i]);
            Trolley_Circle[i] = map.addCircle(new CircleOptions()
                    .center(Trolley_stop_position.get(i))
                    .radius(mSTOP_RADIUS)
                    .strokeColor(Color.WHITE)
                    .fillColor(Color.YELLOW));
        }

    }

    public void clearAll(){
        for(int i = 0; i < mExpress_Stop_Number ; i++){
            Express_Polyline[i].remove();
            Express_Circle[i].remove();
        }
    }

    public void drawBetweenStop(String route, String start_stop, String end_stop, GoogleMap map){
        clearAll();
        switch (route) {
            case "express" :
                int start = Express_stop.indexOf(start_stop);
                int end = Express_stop.indexOf(end_stop);
                Express_Circle[start] = map.addCircle(new CircleOptions()
                        .center(Express_stop_position.get(start))
                        .radius(mSTOP_RADIUS)
                        .strokeColor(Color.WHITE)
                        .fillColor(Color.GRAY));
                Express_Circle[end] = map.addCircle(new CircleOptions()
                        .center(Express_stop_position.get(end))
                        .radius(mSTOP_RADIUS)
                        .strokeColor(Color.WHITE)
                        .fillColor(Color.GRAY));
                if(end < start)
                    end = end + mExpress_Stop_Number;
                for(int i = start; i < end; i++){
                    int j = i % mExpress_Stop_Number;
                    Express_Polyline[j] = map.addPolyline(Express[j]);
                }
        }

    }

    public void init(){
        /**
         * stop information: Express
         */
        Express_stop.add("clough");
        Express_stop_position.add(new LatLng(33.7753,-84.39611));
        Express_stop.add("techsqua_ib");
        Express_stop_position.add(new LatLng(33.7768,-84.38975));
        Express_stop.add("87");
        Express_stop_position.add(new LatLng(33.77678,-84.38749));
        Express_stop.add("techsqua");
        Express_stop_position.add(new LatLng(33.77692,-84.38978));

        /**
         * stop information: Trolley
         */
        Trolley_stop.add("marta_a");
        Trolley_stop_position.add(new LatLng(33.78082,-84.38641));
        Trolley_stop.add("publix");
        Trolley_stop_position.add(new LatLng(33.7806,-84.388818));
        Trolley_stop.add("techsqua");
        Trolley_stop_position.add(new LatLng(33.77692,-84.38978));
        Trolley_stop.add("tech5rec");
        Trolley_stop_position.add(new LatLng(33.776896,-84.391581));
        Trolley_stop.add("fersforec");
        Trolley_stop_position.add(new LatLng(33.776949,-84.394234));
        Trolley_stop.add("ferschrec");
        Trolley_stop_position.add(new LatLng(33.777634,-84.39575));
        Trolley_stop.add("fersatla");
        Trolley_stop_position.add(new LatLng(33.77832,-84.398083));
        Trolley_stop.add("fersherec");
        Trolley_stop_position.add(new LatLng(33.778141,-84.401829));
        Trolley_stop.add("recctr");
        Trolley_stop_position.add(new LatLng(33.7751,-84.40265));
        Trolley_stop.add("studcentr");
        Trolley_stop_position.add(new LatLng(33.77335,-84.39917));
        Trolley_stop.add("tranhub_a");
        Trolley_stop_position.add(new LatLng(33.773261,-84.397058));
        Trolley_stop.add("centrstud");
        Trolley_stop_position.add(new LatLng(33.77335,-84.39917));
        Trolley_stop.add("ferstdr");
        Trolley_stop_position.add(new LatLng(33.774997,-84.402359));
        Trolley_stop.add("fershemrt");
        Trolley_stop_position.add(new LatLng(33.778363,-84.401007));
        Trolley_stop.add("fersatl_ib");
        Trolley_stop_position.add(new LatLng(33.77819,-84.397491));
        Trolley_stop.add("ferschmrt");
        Trolley_stop_position.add(new LatLng(33.777439,-84.395508));
        Trolley_stop.add("fersfomrt");
        Trolley_stop_position.add(new LatLng(33.776925,-84.393671));
        Trolley_stop.add("tech5mrt");
        Trolley_stop_position.add(new LatLng(33.776878,-84.391914));
        Trolley_stop.add("techsqua_ib");
        Trolley_stop_position.add(new LatLng(33.7768,-84.38975));
        Trolley_stop.add("duprmrt");
        Trolley_stop_position.add(new LatLng(33.77678,-84.38749));
        Trolley_stop.add("wpe7mrt");
        Trolley_stop_position.add(new LatLng(33.778536,-84.38724));


        /**
         * route shape information: Express
         */
        Express[0] = new PolylineOptions().add(
                new LatLng(33.77521,-84.39612),
                new LatLng(33.77521,-84.39606),
                new LatLng(33.77521,-84.39601),
                new LatLng(33.77523,-84.39592),
                new LatLng(33.77524,-84.39583),
                new LatLng(33.77526,-84.39577),
                new LatLng(33.7753,-84.39563),
                new LatLng(33.77533,-84.39557),
                new LatLng(33.77539,-84.39537),
                new LatLng(33.77549,-84.39517),
                new LatLng(33.77558,-84.39501),
                new LatLng(33.7756,-84.39497),
                new LatLng(33.77568,-84.39484),
                new LatLng(33.77569,-84.39483),
                new LatLng(33.77575,-84.3947),
                new LatLng(33.77583,-84.39449),
                new LatLng(33.77588,-84.39429),
                new LatLng(33.77591,-84.39414),
                new LatLng(33.77593,-84.39398),
                new LatLng(33.77593,-84.39381),
                new LatLng(33.77593,-84.39365),
                new LatLng(33.77593,-84.39365),
                new LatLng(33.77632,-84.39365),
                new LatLng(33.77644,-84.39364),
                new LatLng(33.77654,-84.39364),
                new LatLng(33.77691,-84.39363),
                new LatLng(33.77691,-84.39363),
                new LatLng(33.7769,-84.39311),
                new LatLng(33.77689,-84.39256),
                new LatLng(33.77689,-84.3921),
                new LatLng(33.77688,-84.39139),
                new LatLng(33.77688,-84.39123),
                new LatLng(33.77688,-84.39106),
                new LatLng(33.77688,-84.39099),
                new LatLng(33.77688,-84.39096),
                new LatLng(33.77687,-84.39065),
                new LatLng(33.77687,-84.39064),
                new LatLng(33.77687,-84.39054),
                new LatLng(33.77687,-84.3904),
                new LatLng(33.77686,-84.38975))
                .width(mWIDTH)
                .clickable(false)
                .color(Color.GRAY);
        Express[1] = new PolylineOptions().add(
                new LatLng(33.77686,-84.38975),
                new LatLng(33.77686,-84.38955),
                new LatLng(33.77684,-84.3888),
                new LatLng(33.77683,-84.38825),
                new LatLng(33.77683,-84.38805),
                new LatLng(33.77683,-84.38793),
                new LatLng(33.77682,-84.38749)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(Color.GRAY);
        Express[2] = new PolylineOptions().add(
                new LatLng(33.77682,-84.38749),
                new LatLng(33.77682,-84.38746),
                new LatLng(33.77682,-84.3873),
                new LatLng(33.77754,-84.3873),
                new LatLng(33.77755,-84.38791),
                new LatLng(33.77755,-84.38812),
                new LatLng(33.77756,-84.38878),
                new LatLng(33.77684,-84.3888),
                new LatLng(33.77686,-84.38955),
                new LatLng(33.77686,-84.38978)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(Color.GRAY);
        Express[3] = new PolylineOptions().add(
                new LatLng(33.77686,-84.38978),
                new LatLng(33.77687,-84.3904),
                new LatLng(33.77687,-84.39054),
                new LatLng(33.77687,-84.39064),
                new LatLng(33.77687,-84.39065),
                new LatLng(33.77688,-84.39096),
                new LatLng(33.77688,-84.39099),
                new LatLng(33.77688,-84.39106),
                new LatLng(33.77688,-84.39123),
                new LatLng(33.77688,-84.39139),
                new LatLng(33.77689,-84.3921),
                new LatLng(33.77689,-84.3921),
                new LatLng(33.7768,-84.3921),
                new LatLng(33.77647,-84.39207),
                new LatLng(33.77629,-84.39204),
                new LatLng(33.77588,-84.392),
                new LatLng(33.7758,-84.39198),
                new LatLng(33.77545,-84.39197),
                new LatLng(33.77536,-84.39197),
                new LatLng(33.77536,-84.39197),
                new LatLng(33.77534,-84.39203),
                new LatLng(33.77532,-84.39209),
                new LatLng(33.77531,-84.39212),
                new LatLng(33.77531,-84.39212),
                new LatLng(33.7753,-84.39216),
                new LatLng(33.77529,-84.39229),
                new LatLng(33.77529,-84.39229),
                new LatLng(33.77541,-84.39241),
                new LatLng(33.77544,-84.39245),
                new LatLng(33.77545,-84.39246),
                new LatLng(33.7755,-84.39252),
                new LatLng(33.77553,-84.39256),
                new LatLng(33.77557,-84.39261),
                new LatLng(33.77565,-84.39274),
                new LatLng(33.77571,-84.39285),
                new LatLng(33.77573,-84.39291),
                new LatLng(33.77577,-84.39299),
                new LatLng(33.7758,-84.39306),
                new LatLng(33.77581,-84.3931),
                new LatLng(33.77583,-84.39316),
                new LatLng(33.77588,-84.39332),
                new LatLng(33.77591,-84.39348),
                new LatLng(33.77593,-84.39365),
                new LatLng(33.77593,-84.39381),
                new LatLng(33.77593,-84.39398),
                new LatLng(33.77591,-84.39414),
                new LatLng(33.77588,-84.39429),
                new LatLng(33.77583,-84.39449),
                new LatLng(33.77575,-84.3947),
                new LatLng(33.77569,-84.39483),
                new LatLng(33.77568,-84.39484),
                new LatLng(33.7756,-84.39497),
                new LatLng(33.77558,-84.39501),
                new LatLng(33.77549,-84.39517),
                new LatLng(33.77539,-84.39537),
                new LatLng(33.77533,-84.39557),
                new LatLng(33.7753,-84.39563),
                new LatLng(33.77526,-84.39577),
                new LatLng(33.77524,-84.39583),
                new LatLng(33.77523,-84.39592),
                new LatLng(33.77521,-84.39601),
                new LatLng(33.77521,-84.39606),
                new LatLng(33.77521,-84.39612)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(Color.GRAY);



    }



}



