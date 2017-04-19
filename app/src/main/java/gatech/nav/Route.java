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
    private final int mBlue_Stop_Number = 17;
    private final int mRed_Stop_Number = 17;
    private final int mGreen_Stop_Number = 17;

    private PolylineOptions[] Express = new PolylineOptions[mExpress_Stop_Number];
    private LinkedList<Polyline> Express_Polyline = new LinkedList<Polyline>();
    private LinkedList<Circle> Express_Circle = new LinkedList<Circle>();
    private LinkedList<String> Express_stop = new LinkedList<>();
    private LinkedList<LatLng> Express_stop_position = new LinkedList<>();

    private PolylineOptions[] Trolley = new PolylineOptions[mTrolley_Stop_Number];
    private LinkedList<Polyline> Trolley_Polyline = new LinkedList<Polyline>();
    private LinkedList<Circle> Trolley_Circle = new LinkedList<Circle>();
    private LinkedList<String> Trolley_stop = new LinkedList<>();
    private LinkedList<LatLng> Trolley_stop_position = new LinkedList<>();

    private PolylineOptions[] Blue = new PolylineOptions[mBlue_Stop_Number];
    private LinkedList<Polyline> Blue_Polyline = new LinkedList<Polyline>();
    private LinkedList<Circle> Blue_Circle = new LinkedList<Circle>();
    private LinkedList<String> Blue_stop = new LinkedList<>();
    private LinkedList<LatLng> Blue_stop_position = new LinkedList<>();

    private PolylineOptions[] Red = new PolylineOptions[mRed_Stop_Number];
    private LinkedList<Polyline> Red_Polyline = new LinkedList<Polyline>();
    private LinkedList<Circle> Red_Circle = new LinkedList<Circle>();
    private LinkedList<String> Red_stop = new LinkedList<>();
    private LinkedList<LatLng> Red_stop_position = new LinkedList<>();

    private PolylineOptions[] Green = new PolylineOptions[mGreen_Stop_Number];
    private LinkedList<Polyline> Green_Polyline = new LinkedList<Polyline>();
    private LinkedList<Circle> Green_Circle = new LinkedList<Circle>();
    private LinkedList<String> Green_stop = new LinkedList<>();
    private LinkedList<LatLng> Green_stop_position = new LinkedList<>();

    private final int mWIDTH = 10;
    private final int mSTOP_RADIUS = 6;
    private BusSchedule busSchedule = new BusSchedule();

    public void draw(GoogleMap map){
        clearAll();
        busSchedule.init();
        if(BusSchedule.getExpress()) {
            for (int i = 0; i < mExpress_Stop_Number; i++) {
                Express_Polyline.add(map.addPolyline(Express[i]));
                Express_Circle.add(map.addCircle(new CircleOptions()
                        .center(Express_stop_position.get(i))
                        .radius(mSTOP_RADIUS)
                        .strokeColor(Color.WHITE)
                        .fillColor(android.graphics.Color.rgb(0x33, 0x33, 0x33))));
            }
        }

        if(BusSchedule.getTrolley()) {
            for (int i = 0; i < mTrolley_Stop_Number; i++) {
                Trolley_Polyline.add(map.addPolyline(Trolley[i]));
                Trolley_Circle.add(map.addCircle(new CircleOptions()
                        .center(Trolley_stop_position.get(i))
                        .radius(mSTOP_RADIUS)
                        .strokeColor(Color.WHITE)
                        .fillColor(android.graphics.Color.rgb(0xff, 0xcc, 0x00))));
            }
        }

        if(BusSchedule.getBlue()) {
            for (int i = 0; i < mBlue_Stop_Number; i++) {
                Blue_Polyline.add(map.addPolyline(Blue[i]));
                Blue_Circle.add(map.addCircle(new CircleOptions()
                        .center(Blue_stop_position.get(i))
                        .radius(mSTOP_RADIUS)
                        .strokeColor(Color.WHITE)
                        .fillColor(android.graphics.Color.rgb(0x00, 0x00, 0xff))));
            }
        }

        if(BusSchedule.getRed()) {
            for (int i = 0; i < mRed_Stop_Number; i++) {
                Red_Polyline.add(map.addPolyline(Red[i]));
                Red_Circle.add(map.addCircle(new CircleOptions()
                        .center(Red_stop_position.get(i))
                        .radius(mSTOP_RADIUS)
                        .strokeColor(Color.WHITE)
                        .fillColor(android.graphics.Color.rgb(0xff, 0x11, 0x00))));
            }
        }

        if(BusSchedule.getGreen()) {
            for (int i = 0; i < mGreen_Stop_Number; i++) {
                Green_Polyline.add(map.addPolyline(Green[i]));
                Green_Circle.add(map.addCircle(new CircleOptions()
                        .center(Green_stop_position.get(i))
                        .radius(mSTOP_RADIUS)
                        .strokeColor(Color.WHITE)
                        .fillColor(android.graphics.Color.rgb(0x00, 0xcc, 0x66))));
            }
        }

    }

    public void clearAll(){
        if(Express_Polyline.size()>0) {
            for (int i = 0; i < Express_Polyline.size(); i++) {
                Express_Polyline.get(i).remove();
            }
            Express_Polyline.clear();
        }
        if(Express_Circle.size()>0) {
            for (int i = 0; i < Express_Circle.size(); i++) {
                Express_Circle.get(i).remove();
            }
            Express_Circle.clear();
        }

        if(Trolley_Polyline.size()>0) {
            for (int i = 0; i < Trolley_Polyline.size(); i++) {
                Trolley_Polyline.get(i).remove();
            }
            Trolley_Polyline.clear();
        }
        if(Trolley_Circle.size()>0) {
            for (int i = 0; i < Trolley_Circle.size(); i++) {
                Trolley_Circle.get(i).remove();
            }
            Trolley_Circle.clear();
        }

        if(Blue_Polyline.size()>0) {
            for (int i = 0; i < Blue_Polyline.size(); i++) {
                Blue_Polyline.get(i).remove();
            }
            Blue_Polyline.clear();
        }
        if(Blue_Circle.size()>0) {
            for (int i = 0; i < Blue_Circle.size(); i++) {
                Blue_Circle.get(i).remove();
            }
            Blue_Circle.clear();
        }

        if(Red_Polyline.size()>0) {
            for (int i = 0; i < Red_Polyline.size(); i++) {
                Red_Polyline.get(i).remove();
            }
            Red_Polyline.clear();
        }
        if(Red_Circle.size()>0) {
            for (int i = 0; i < Red_Circle.size(); i++) {
                Red_Circle.get(i).remove();
            }
            Red_Circle.clear();
        }

        if(Green_Polyline.size()>0) {
            for (int i = 0; i < Green_Polyline.size(); i++) {
                Green_Polyline.get(i).remove();
            }
            Green_Polyline.clear();
        }
        if(Green_Circle.size()>0) {
            for (int i = 0; i < Green_Circle.size(); i++) {
                Green_Circle.get(i).remove();
            }
            Green_Circle.clear();
        }
    }

    public LatLng getStopLatLng(String stopName, String route){
        int i;
        LatLng position;
        switch (route){
            case "express" :
                i = Express_stop.indexOf(stopName);
                position = Express_stop_position.get(i);
                break;
            case "trolley" :
                i = Trolley_stop.indexOf(stopName);
                position = Trolley_stop_position.get(i);
                break;
            case "red" :
                i = Red_stop.indexOf(stopName);
                position = Red_stop_position.get(i);
                break;
            case "blue" :
                i = Blue_stop.indexOf(stopName);
                position = Blue_stop_position.get(i);
                break;
            case "green" :
                i = Green_stop.indexOf(stopName);
                position = Green_stop_position.get(i);
                break;
            default:
                position = null;
        }
        return position;
    }

    public void drawBetweenStop(String route, String start_stop, String end_stop, GoogleMap map){
        clearAll();
        int start;
        int end;
        switch (route) {
            case "express" :
                start = Express_stop.indexOf(start_stop);
                end = Express_stop.indexOf(end_stop);
                Express_Circle.add(map.addCircle(new CircleOptions()
                        .center(Express_stop_position.get(start))
                        .radius(mSTOP_RADIUS)
                        .strokeColor(Color.WHITE)
                        .fillColor(android.graphics.Color.rgb(0x33,0x33,0x33))));
                Express_Circle.add(map.addCircle(new CircleOptions()
                        .center(Express_stop_position.get(end))
                        .radius(mSTOP_RADIUS)
                        .strokeColor(Color.WHITE)
                        .fillColor(android.graphics.Color.rgb(0x33,0x33,0x33))));
                if(end < start)
                    end = end + mExpress_Stop_Number;
                for(int i = start; i < end; i++){
                    int j = i % mExpress_Stop_Number;
                    Express_Polyline.add(map.addPolyline(Express[j]));
                }
                break;
            case "trolley" :
                start = Trolley_stop.indexOf(start_stop);
                end = Trolley_stop.indexOf(end_stop);
                Trolley_Circle.add(map.addCircle(new CircleOptions()
                        .center(Trolley_stop_position.get(start))
                        .radius(mSTOP_RADIUS)
                        .strokeColor(Color.WHITE)
                        .fillColor(android.graphics.Color.rgb(0xff,0xcc,0x00))));
                Trolley_Circle.add(map.addCircle(new CircleOptions()
                        .center(Trolley_stop_position.get(end))
                        .radius(mSTOP_RADIUS)
                        .strokeColor(Color.WHITE)
                        .fillColor(android.graphics.Color.rgb(0xff,0xcc,0x00))));
                if(end < start)
                    end = end + mTrolley_Stop_Number;
                for(int i = start; i < end; i++){
                    int j = i % mTrolley_Stop_Number;
                    Trolley_Polyline.add(map.addPolyline(Trolley[j]));
                }
                break;
            case "blue" :
                start = Blue_stop.indexOf(start_stop);
                end = Blue_stop.indexOf(end_stop);
                Blue_Circle.add(map.addCircle(new CircleOptions()
                        .center(Blue_stop_position.get(start))
                        .radius(mSTOP_RADIUS)
                        .strokeColor(Color.WHITE)
                        .fillColor(android.graphics.Color.rgb(0x00,0x00,0xff))));
                Blue_Circle.add(map.addCircle(new CircleOptions()
                        .center(Blue_stop_position.get(end))
                        .radius(mSTOP_RADIUS)
                        .strokeColor(Color.WHITE)
                        .fillColor(android.graphics.Color.rgb(0x00,0x00,0xff))));
                if(end < start)
                    end = end + mBlue_Stop_Number;
                for(int i = start; i < end; i++){
                    int j = i % mBlue_Stop_Number;
                    Blue_Polyline.add(map.addPolyline(Blue[j]));
                }
                break;
            case "red" :
                start = Red_stop.indexOf(start_stop);
                end = Red_stop.indexOf(end_stop);
                Red_Circle.add(map.addCircle(new CircleOptions()
                        .center(Red_stop_position.get(start))
                        .radius(mSTOP_RADIUS)
                        .strokeColor(Color.WHITE)
                        .fillColor(android.graphics.Color.rgb(0xff,0x11,0x00))));
                Red_Circle.add(map.addCircle(new CircleOptions()
                        .center(Red_stop_position.get(end))
                        .radius(mSTOP_RADIUS)
                        .strokeColor(Color.WHITE)
                        .fillColor(android.graphics.Color.rgb(0xff,0x11,0x00))));
                if(end < start)
                    end = end + mRed_Stop_Number;
                for(int i = start; i < end; i++){
                    int j = i % mRed_Stop_Number;
                    Red_Polyline.add(map.addPolyline(Red[j]));
                }
                break;
            case "green" :
                start = Green_stop.indexOf(start_stop);
                end = Green_stop.indexOf(end_stop);
                Green_Circle.add(map.addCircle(new CircleOptions()
                        .center(Green_stop_position.get(start))
                        .radius(mSTOP_RADIUS)
                        .strokeColor(Color.WHITE)
                        .fillColor(android.graphics.Color.rgb(0x00,0xcc,0x66))));
                Green_Circle.add(map.addCircle(new CircleOptions()
                        .center(Green_stop_position.get(end))
                        .radius(mSTOP_RADIUS)
                        .strokeColor(Color.WHITE)
                        .fillColor(android.graphics.Color.rgb(0x00,0xcc,0x66))));
                if(end < start)
                    end = end + mGreen_Stop_Number;
                for(int i = start; i < end; i++){
                    int j = i % mGreen_Stop_Number;
                    Green_Polyline.add(map.addPolyline(Green[j]));
                }
                break;



        }

    }

    public void init(){
        /**
         * stop information: Express
         * ***********************************************************************************************************
         * ***********************************************************************************************************
         */
        Express_stop.add("cloucomm");
        Express_stop_position.add(new LatLng(33.7753,-84.39611));
        Express_stop.add("techsqua_ob");
        Express_stop_position.add(new LatLng(33.7768,-84.38975));
        Express_stop.add("duprmrt");
        Express_stop_position.add(new LatLng(33.77678,-84.38749));
        Express_stop.add("techsqua");
        Express_stop_position.add(new LatLng(33.77692,-84.38978));


        /**
         * route shape information: Express
         * ***********************************************************************************************************
         * ***********************************************************************************************************
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
                .color(android.graphics.Color.rgb(0x33,0x33,0x33));
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
                .color(android.graphics.Color.rgb(0x33,0x33,0x33));
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
                .color(android.graphics.Color.rgb(0x33,0x33,0x33));
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
                .color(android.graphics.Color.rgb(0x33,0x33,0x33));


        /**
         * stop information: Trolley
         * ***********************************************************************************************************
         * ***********************************************************************************************************
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
         * route shape information: Trolley
         * ***********************************************************************************************************
         * ***********************************************************************************************************
         */
        Trolley[0] = new PolylineOptions().add(
                new LatLng(33.78075,-84.3864),
                new LatLng(33.78075,-84.38648),
                new LatLng(33.78075,-84.38669),
                new LatLng(33.78075,-84.38732),
                new LatLng(33.78073,-84.38751),
                new LatLng(33.78058,-84.38882)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[1] = new PolylineOptions().add(
                new LatLng(33.78058,-84.38882),
                new LatLng(33.78056,-84.38899),
                new LatLng(33.78056,-84.38899),
                new LatLng(33.77955,-84.38882),
                new LatLng(33.77929,-84.38878),
                new LatLng(33.77916,-84.38877),
                new LatLng(33.77912,-84.38877),
                new LatLng(33.77902,-84.38876),
                new LatLng(33.77838,-84.38877),
                new LatLng(33.77803,-84.38877),
                new LatLng(33.77782,-84.38878),
                new LatLng(33.77756,-84.38878),
                new LatLng(33.77684,-84.3888),
                new LatLng(33.77684,-84.3888),
                new LatLng(33.77686,-84.38955),
                new LatLng(33.77686,-84.38978)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[2] = new PolylineOptions().add(
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
                new LatLng(33.77688,-84.39158)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[3] = new PolylineOptions().add(
                new LatLng(33.77688,-84.39158),
                new LatLng(33.77689,-84.3921),
                new LatLng(33.77689,-84.39256),
                new LatLng(33.7769,-84.39311),
                new LatLng(33.7769,-84.39311),
                new LatLng(33.77691,-84.39363),
                new LatLng(33.77692,-84.39387),
                new LatLng(33.77692,-84.39424)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[4] = new PolylineOptions().add(
                new LatLng(33.77692,-84.39424),
                new LatLng(33.77694,-84.39507),
                new LatLng(33.77694,-84.3951),
                new LatLng(33.77695,-84.39513),
                new LatLng(33.77697,-84.39521),
                new LatLng(33.77699,-84.39525),
                new LatLng(33.777,-84.39527),
                new LatLng(33.77701,-84.39529),
                new LatLng(33.77703,-84.39531),
                new LatLng(33.77704,-84.39533),
                new LatLng(33.77705,-84.39535),
                new LatLng(33.77707,-84.39536),
                new LatLng(33.77711,-84.3954),
                new LatLng(33.77716,-84.39545),
                new LatLng(33.77753,-84.39569),
                new LatLng(33.77755,-84.3957),
                new LatLng(33.77764,-84.39577)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[5] = new PolylineOptions().add(
                new LatLng(33.77764,-84.39577),
                new LatLng(33.77772,-84.39582),
                new LatLng(33.77775,-84.39584),
                new LatLng(33.77781,-84.39588),
                new LatLng(33.7779,-84.39598),
                new LatLng(33.77794,-84.39603),
                new LatLng(33.77798,-84.39608),
                new LatLng(33.778,-84.39611),
                new LatLng(33.77804,-84.39618),
                new LatLng(33.7781,-84.39629),
                new LatLng(33.77816,-84.39644),
                new LatLng(33.7782,-84.39662),
                new LatLng(33.77821,-84.39666),
                new LatLng(33.77822,-84.39673),
                new LatLng(33.77823,-84.39681),
                new LatLng(33.77824,-84.39691),
                new LatLng(33.77824,-84.39719),
                new LatLng(33.77825,-84.39766),
                new LatLng(33.77825,-84.3978),
                new LatLng(33.77826,-84.39808)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[6] = new PolylineOptions().add(
                new LatLng(33.77826,-84.39808),
                new LatLng(33.77826,-84.39836),
                new LatLng(33.77827,-84.39913),
                new LatLng(33.77827,-84.39922),
                new LatLng(33.77828,-84.39937),
                new LatLng(33.77828,-84.39943),
                new LatLng(33.77829,-84.3995),
                new LatLng(33.77839,-84.39996),
                new LatLng(33.77846,-84.4002),
                new LatLng(33.77848,-84.40038),
                new LatLng(33.77849,-84.40048),
                new LatLng(33.77849,-84.40056),
                new LatLng(33.7785,-84.40061),
                new LatLng(33.77849,-84.40068),
                new LatLng(33.77849,-84.40077),
                new LatLng(33.7785,-84.40085),
                new LatLng(33.77849,-84.40101),
                new LatLng(33.77846,-84.40114),
                new LatLng(33.7784,-84.4013),
                new LatLng(33.77838,-84.40133),
                new LatLng(33.77836,-84.40136),
                new LatLng(33.77825,-84.40154),
                new LatLng(33.77812,-84.40175)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[7] = new PolylineOptions().add(
                new LatLng(33.77812,-84.40175),
                new LatLng(33.77796,-84.40199),
                new LatLng(33.77787,-84.40209),
                new LatLng(33.7778,-84.40215),
                new LatLng(33.77773,-84.40221),
                new LatLng(33.77767,-84.40225),
                new LatLng(33.7776,-84.40229),
                new LatLng(33.77753,-84.40233),
                new LatLng(33.77745,-84.40236),
                new LatLng(33.77733,-84.40239),
                new LatLng(33.77729,-84.4024),
                new LatLng(33.77661,-84.40246),
                new LatLng(33.77656,-84.40246),
                new LatLng(33.77634,-84.40247),
                new LatLng(33.77612,-84.40249),
                new LatLng(33.77566,-84.40254),
                new LatLng(33.77551,-84.40255),
                new LatLng(33.77542,-84.40256),
                new LatLng(33.77528,-84.40257),
                new LatLng(33.7751,-84.40259)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[8] = new PolylineOptions().add(
                new LatLng(33.7751,-84.40259),
                new LatLng(33.77497,-84.4026),
                new LatLng(33.77491,-84.40261),
                new LatLng(33.77489,-84.40261),
                new LatLng(33.77485,-84.4026),
                new LatLng(33.7748,-84.4026),
                new LatLng(33.77475,-84.40259),
                new LatLng(33.77469,-84.40258),
                new LatLng(33.77463,-84.40255),
                new LatLng(33.77458,-84.40253),
                new LatLng(33.77438,-84.40244),
                new LatLng(33.77427,-84.40244),
                new LatLng(33.77418,-84.40236),
                new LatLng(33.77398,-84.40213),
                new LatLng(33.7739,-84.40201),
                new LatLng(33.77386,-84.40193),
                new LatLng(33.77383,-84.40186),
                new LatLng(33.77382,-84.40185),
                new LatLng(33.77379,-84.40176),
                new LatLng(33.77376,-84.40168),
                new LatLng(33.77373,-84.40157),
                new LatLng(33.77369,-84.40137),
                new LatLng(33.7737,-84.40123),
                new LatLng(33.77339,-84.39929),
                new LatLng(33.77339,-84.39924),
                new LatLng(33.77337,-84.39916)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[9] = new PolylineOptions().add(
                new LatLng(33.77337,-84.39916),
                new LatLng(33.77337,-84.39913),
                new LatLng(33.77329,-84.39862),
                new LatLng(33.77325,-84.39831),
                new LatLng(33.77322,-84.39814),
                new LatLng(33.77318,-84.39799),
                new LatLng(33.77318,-84.39797),
                new LatLng(33.77316,-84.3979),
                new LatLng(33.77309,-84.39774),
                new LatLng(33.77302,-84.39759),
                new LatLng(33.77297,-84.39746),
                new LatLng(33.772799,-84.397200),
                new LatLng(33.772978,-84.397023),
                new LatLng(33.773059,-84.397185),
                new LatLng(33.773124,-84.397237),
                new LatLng(33.773209,-84.397247),
                new LatLng(33.773289,-84.397219),
                new LatLng(33.773317,-84.397136),
                new LatLng(33.773281,-84.397039)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[10] = new PolylineOptions().add(
                new LatLng(33.773281,-84.397039),
                new LatLng(33.773222,-84.396995),
                new LatLng(33.773049,-84.396998),
                new LatLng(33.772979,-84.397024),
                new LatLng(33.772800,-84.397205),
                new LatLng(33.772969,-84.397464),
                new LatLng(33.773120,-84.397801),
                new LatLng(33.77318,-84.39797),
                new LatLng(33.77318,-84.39799),
                new LatLng(33.77322,-84.39814),
                new LatLng(33.77325,-84.39831),
                new LatLng(33.77329,-84.39862),
                new LatLng(33.77337,-84.39913)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[11] = new PolylineOptions().add(
                new LatLng(33.77337,-84.39913),
                new LatLng(33.77339,-84.39924),
                new LatLng(33.77339,-84.39929),
                new LatLng(33.7737,-84.40123),
                new LatLng(33.77376,-84.40134),
                new LatLng(33.77378,-84.40147),
                new LatLng(33.77381,-84.4016),
                new LatLng(33.77387,-84.40176),
                new LatLng(33.77391,-84.40185),
                new LatLng(33.77395,-84.40193),
                new LatLng(33.77402,-84.40204),
                new LatLng(33.77406,-84.4021),
                new LatLng(33.77411,-84.40216),
                new LatLng(33.77422,-84.40228),
                new LatLng(33.77431,-84.40236),
                new LatLng(33.77438,-84.40244),
                new LatLng(33.77458,-84.40253),
                new LatLng(33.774612,-84.402378),
                new LatLng(33.774656,-84.402303),
                new LatLng(33.774724,-84.402278),
                new LatLng(33.774782,-84.402287),
                new LatLng(33.774994,-84.402373)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[12] = new PolylineOptions().add(
                new LatLng(33.774994,-84.402373),
                new LatLng(33.775141,-84.402425),
                new LatLng(33.775206,-84.402479),
                new LatLng(33.77528,-84.40257),
                new LatLng(33.77542,-84.40256),
                new LatLng(33.77551,-84.40255),
                new LatLng(33.77566,-84.40254),
                new LatLng(33.77612,-84.40249),
                new LatLng(33.77634,-84.40247),
                new LatLng(33.77656,-84.40246),
                new LatLng(33.77661,-84.40246),
                new LatLng(33.77729,-84.4024),
                new LatLng(33.77733,-84.40239),
                new LatLng(33.77745,-84.40236),
                new LatLng(33.77753,-84.40233),
                new LatLng(33.7776,-84.40229),
                new LatLng(33.77767,-84.40225),
                new LatLng(33.77773,-84.40221),
                new LatLng(33.7778,-84.40215),
                new LatLng(33.77787,-84.40209),
                new LatLng(33.77796,-84.40199),
                new LatLng(33.77812,-84.40175),
                new LatLng(33.77825,-84.40154),
                new LatLng(33.77836,-84.40136),
                new LatLng(33.77838,-84.40133),
                new LatLng(33.7784,-84.4013),
                new LatLng(33.77846,-84.40114),
                new LatLng(33.77848,-84.40105)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[13] = new PolylineOptions().add(
                new LatLng(33.77848,-84.40105),
                new LatLng(33.77849,-84.40101),
                new LatLng(33.7785,-84.40085),
                new LatLng(33.77849,-84.40077),
                new LatLng(33.77849,-84.40068),
                new LatLng(33.7785,-84.40061),
                new LatLng(33.77849,-84.40056),
                new LatLng(33.77849,-84.40048),
                new LatLng(33.77848,-84.40038),
                new LatLng(33.77846,-84.4002),
                new LatLng(33.77839,-84.39996),
                new LatLng(33.77829,-84.3995),
                new LatLng(33.77828,-84.39943),
                new LatLng(33.77828,-84.39937),
                new LatLng(33.77827,-84.39922),
                new LatLng(33.77827,-84.39913),
                new LatLng(33.77826,-84.39836),
                new LatLng(33.77825,-84.3978),
                new LatLng(33.77825,-84.39766),
                new LatLng(33.77825,-84.39749)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[14] = new PolylineOptions().add(
                new LatLng(33.77825,-84.39749),
                new LatLng(33.77824,-84.39719),
                new LatLng(33.77824,-84.39691),
                new LatLng(33.77823,-84.39681),
                new LatLng(33.77822,-84.39673),
                new LatLng(33.77821,-84.39666),
                new LatLng(33.7782,-84.39662),
                new LatLng(33.77816,-84.39644),
                new LatLng(33.7781,-84.39629),
                new LatLng(33.77804,-84.39618),
                new LatLng(33.778,-84.39611),
                new LatLng(33.77798,-84.39608),
                new LatLng(33.77794,-84.39603),
                new LatLng(33.7779,-84.39598),
                new LatLng(33.77781,-84.39588),
                new LatLng(33.77775,-84.39584),
                new LatLng(33.77772,-84.39582),
                new LatLng(33.77764,-84.39577),
                new LatLng(33.77755,-84.3957),
                new LatLng(33.77753,-84.39569),
                new LatLng(33.7774,-84.3956)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[15] = new PolylineOptions().add(
                new LatLng(33.7774,-84.3956),
                new LatLng(33.77716,-84.39545),
                new LatLng(33.77711,-84.3954),
                new LatLng(33.77707,-84.39536),
                new LatLng(33.77705,-84.39535),
                new LatLng(33.77704,-84.39533),
                new LatLng(33.77703,-84.39531),
                new LatLng(33.77701,-84.39529),
                new LatLng(33.777,-84.39527),
                new LatLng(33.77699,-84.39525),
                new LatLng(33.77697,-84.39521),
                new LatLng(33.77695,-84.39513),
                new LatLng(33.77694,-84.3951),
                new LatLng(33.77694,-84.39507),
                new LatLng(33.77692,-84.39424),
                new LatLng(33.77692,-84.39387),
                new LatLng(33.77691,-84.39367)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[16] = new PolylineOptions().add(
                new LatLng(33.77691,-84.39367),
                new LatLng(33.77691,-84.39363),
                new LatLng(33.77691,-84.39363),
                new LatLng(33.7769,-84.39311),
                new LatLng(33.77689,-84.39256),
                new LatLng(33.77689,-84.3921),
                new LatLng(33.77689,-84.39191)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[17] = new PolylineOptions().add(
                new LatLng(33.77689,-84.39191),
                new LatLng(33.77688,-84.39139),
                new LatLng(33.77688,-84.39123),
                new LatLng(33.77688,-84.39106),
                new LatLng(33.77688,-84.39099),
                new LatLng(33.77688,-84.39096),
                new LatLng(33.77687,-84.39065),
                new LatLng(33.77687,-84.39064),
                new LatLng(33.77687,-84.39054),
                new LatLng(33.77687,-84.3904),
                new LatLng(33.77686,-84.38975)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[18] = new PolylineOptions().add(
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
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[19] = new PolylineOptions().add(
                new LatLng(33.77682,-84.38749),
                new LatLng(33.77682,-84.38746),
                new LatLng(33.77682,-84.3873),
                new LatLng(33.77682,-84.3873),
                new LatLng(33.77754,-84.3873),
                new LatLng(33.77802,-84.38729),
                new LatLng(33.7784,-84.38729),
                new LatLng(33.77854,-84.38729)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));
        Trolley[20] = new PolylineOptions().add(
                new LatLng(33.77854,-84.38729),
                new LatLng(33.77873,-84.38728),
                new LatLng(33.779,-84.38728),
                new LatLng(33.7792,-84.38728),
                new LatLng(33.77953,-84.38728),
                new LatLng(33.7797,-84.38728),
                new LatLng(33.77991,-84.38729),
                new LatLng(33.78002,-84.38729),
                new LatLng(33.78011,-84.3873),
                new LatLng(33.78016,-84.38731),
                new LatLng(33.78023,-84.38732),
                new LatLng(33.78031,-84.38734),
                new LatLng(33.7804,-84.38737),
                new LatLng(33.78063,-84.38746),
                new LatLng(33.78073,-84.38751),
                new LatLng(33.78118,-84.38771),
                new LatLng(33.78135,-84.38777),
                new LatLng(33.78151,-84.38781),
                new LatLng(33.78151,-84.38781),
                new LatLng(33.7815,-84.38753),
                new LatLng(33.7815,-84.38753),
                new LatLng(33.78148,-84.38717),
                new LatLng(33.78147,-84.38669),
                new LatLng(33.78147,-84.38641),
                new LatLng(33.78147,-84.38634),
                new LatLng(33.78147,-84.38598),
                new LatLng(33.78147,-84.38531),
                new LatLng(33.78148,-84.38526),
                new LatLng(33.78148,-84.38521),
                new LatLng(33.78149,-84.38516),
                new LatLng(33.7815,-84.38511),
                new LatLng(33.78152,-84.38504),
                new LatLng(33.78158,-84.38491),
                new LatLng(33.78158,-84.38491),
                new LatLng(33.78148,-84.38487),
                new LatLng(33.78137,-84.38482),
                new LatLng(33.78134,-84.38481),
                new LatLng(33.78129,-84.38479),
                new LatLng(33.78123,-84.38476),
                new LatLng(33.78113,-84.38471),
                new LatLng(33.78094,-84.38463),
                new LatLng(33.7809,-84.38462),
                new LatLng(33.78078,-84.38462),
                new LatLng(33.78078,-84.38462),
                new LatLng(33.78077,-84.38523),
                new LatLng(33.78076,-84.3856),
                new LatLng(33.78076,-84.38598),
                new LatLng(33.78075,-84.38606),
                new LatLng(33.78075,-84.38633),
                new LatLng(33.78075,-84.3864)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0xcc,0x00));

        /**
         * stop information: Blue
         * ***********************************************************************************************************
         * ***********************************************************************************************************
         */
        Blue_stop.add("fitthall_a");
        Blue_stop_position.add(new LatLng(33.778274,-84.404191));
        Blue_stop.add("mcmil8th");
        Blue_stop_position.add(new LatLng(33.779599,-84.404164));
        Blue_stop.add("8thhemp");
        Blue_stop_position.add(new LatLng(33.779631,-84.40274));
        Blue_stop.add("reccent");
        Blue_stop_position.add(new LatLng(33.7751,-84.40265));
        Blue_stop.add("studcentr");
        Blue_stop_position.add(new LatLng(33.77335,-84.39917));
        Blue_stop.add("fershub");
        Blue_stop_position.add(new LatLng(33.772842,-84.397359));
        Blue_stop.add("cherfers");
        Blue_stop_position.add(new LatLng(33.77234,-84.3957));
        Blue_stop.add("naveapts_a");
        Blue_stop_position.add(new LatLng(33.77019,-84.39174));
        Blue_stop.add("technorth");
        Blue_stop_position.add(new LatLng(33.771857,-84.39192));
        Blue_stop.add("3rdtech");
        Blue_stop_position.add(new LatLng(33.774061,-84.39192));
        Blue_stop.add("4thtech");
        Blue_stop_position.add(new LatLng(33.775066,-84.39194));
        Blue_stop.add("5thtech");
        Blue_stop_position.add(new LatLng(33.776401,-84.392123));
        Blue_stop.add("fersfowl");
        Blue_stop_position.add(new LatLng(33.776949,-84.394234));
        Blue_stop.add("fersklau");
        Blue_stop_position.add(new LatLng(33.777634,-84.39575));
        Blue_stop.add("fersatla");
        Blue_stop_position.add(new LatLng(33.77832,-84.398083));
        Blue_stop.add("fersstat");
        Blue_stop_position.add(new LatLng(33.778436,-84.399961));
        Blue_stop.add("fershemp_ob");
        Blue_stop_position.add(new LatLng(33.778141,-84.401829));


        /**
         * route shape information: Blue
         * ***********************************************************************************************************
         * ***********************************************************************************************************
         */
        Blue[0] = new PolylineOptions().add(
                new LatLng(33.77827,-84.4042),
                new LatLng(33.77844,-84.40419),
                new LatLng(33.77868,-84.40419),
                new LatLng(33.77876,-84.40418),
                new LatLng(33.77902,-84.40418),
                new LatLng(33.77906,-84.40418),
                new LatLng(33.77931,-84.40418),
                new LatLng(33.7796,-84.40417)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0x00,0xff));
        Blue[1] = new PolylineOptions().add(
                new LatLng(33.7796,-84.40417),
                new LatLng(33.77964,-84.40417),
                new LatLng(33.77964,-84.40417),
                new LatLng(33.77964,-84.40401),
                new LatLng(33.77964,-84.40315),
                new LatLng(33.77964,-84.40286),
                new LatLng(33.77964,-84.40274)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0x00,0xff));
        Blue[2] = new PolylineOptions().add(
                new LatLng(33.77964,-84.40274),
                new LatLng(33.77964,-84.40267),
                new LatLng(33.77964,-84.40263),
                new LatLng(33.77966,-84.40259),
                new LatLng(33.7797,-84.40252),
                new LatLng(33.7797,-84.40252),
                new LatLng(33.77964,-84.40247),
                new LatLng(33.77936,-84.4022),
                new LatLng(33.77922,-84.40207),
                new LatLng(33.77912,-84.40198),
                new LatLng(33.77897,-84.40184),
                new LatLng(33.77866,-84.40152),
                new LatLng(33.7784,-84.4013),
                new LatLng(33.7784,-84.4013),
                new LatLng(33.77838,-84.40133),
                new LatLng(33.77836,-84.40136),
                new LatLng(33.77825,-84.40154),
                new LatLng(33.77812,-84.40175),
                new LatLng(33.77796,-84.40199),
                new LatLng(33.77787,-84.40209),
                new LatLng(33.7778,-84.40215),
                new LatLng(33.77773,-84.40221),
                new LatLng(33.77767,-84.40225),
                new LatLng(33.7776,-84.40229),
                new LatLng(33.77753,-84.40233),
                new LatLng(33.77745,-84.40236),
                new LatLng(33.77733,-84.40239),
                new LatLng(33.77729,-84.4024),
                new LatLng(33.77661,-84.40246),
                new LatLng(33.77656,-84.40246),
                new LatLng(33.77634,-84.40247),
                new LatLng(33.77612,-84.40249),
                new LatLng(33.77566,-84.40254),
                new LatLng(33.77551,-84.40255),
                new LatLng(33.77542,-84.40256),
                new LatLng(33.77528,-84.40257),
                new LatLng(33.7751,-84.40259)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0x00,0xff));
        Blue[3] = new PolylineOptions().add(
                new LatLng(33.7751,-84.40259),
                new LatLng(33.77497,-84.4026),
                new LatLng(33.77491,-84.40261),
                new LatLng(33.77489,-84.40261),
                new LatLng(33.77485,-84.4026),
                new LatLng(33.7748,-84.4026),
                new LatLng(33.77475,-84.40259),
                new LatLng(33.77469,-84.40258),
                new LatLng(33.77463,-84.40255),
                new LatLng(33.77458,-84.40253),
                new LatLng(33.77438,-84.40244),
                new LatLng(33.77427,-84.40244),
                new LatLng(33.77418,-84.40236),
                new LatLng(33.77398,-84.40213),
                new LatLng(33.7739,-84.40201),
                new LatLng(33.77386,-84.40193),
                new LatLng(33.77383,-84.40186),
                new LatLng(33.77382,-84.40185),
                new LatLng(33.77379,-84.40176),
                new LatLng(33.77376,-84.40168),
                new LatLng(33.77373,-84.40157),
                new LatLng(33.77369,-84.40137),
                new LatLng(33.7737,-84.40123),
                new LatLng(33.77339,-84.39929),
                new LatLng(33.77339,-84.39924),
                new LatLng(33.77337,-84.39916)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0x00,0xff));
        Blue[4] = new PolylineOptions().add(
                new LatLng(33.77337,-84.39916),
                new LatLng(33.77337,-84.39913),
                new LatLng(33.77329,-84.39862),
                new LatLng(33.77325,-84.39831),
                new LatLng(33.77322,-84.39814),
                new LatLng(33.77318,-84.39799),
                new LatLng(33.77318,-84.39797),
                new LatLng(33.77316,-84.3979),
                new LatLng(33.77309,-84.39774),
                new LatLng(33.77302,-84.39759),
                new LatLng(33.77297,-84.39746),
                new LatLng(33.77288,-84.39733)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0x00,0xff));
        Blue[5] = new PolylineOptions().add(
                new LatLng(33.77288,-84.39733),
                new LatLng(33.77287,-84.39732),
                new LatLng(33.7728,-84.3972),
                new LatLng(33.77266,-84.39702),
                new LatLng(33.77245,-84.39672),
                new LatLng(33.77241,-84.39664),
                new LatLng(33.77238,-84.39652),
                new LatLng(33.77237,-84.39645),
                new LatLng(33.77237,-84.39629),
                new LatLng(33.77237,-84.39611),
                new LatLng(33.77236,-84.3957)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0x00,0xff));
        Blue[6] = new PolylineOptions().add(
                new LatLng(33.77236,-84.3957),
                new LatLng(33.77236,-84.39567),
                new LatLng(33.77236,-84.3955),
                new LatLng(33.77236,-84.3955),
                new LatLng(33.7723,-84.3955),
                new LatLng(33.77177,-84.39552),
                new LatLng(33.77133,-84.39552),
                new LatLng(33.77133,-84.39552),
                new LatLng(33.77133,-84.39584),
                new LatLng(33.77135,-84.39609),
                new LatLng(33.77135,-84.39609),
                new LatLng(33.77123,-84.39606),
                new LatLng(33.77103,-84.39606),
                new LatLng(33.77074,-84.39607),
                new LatLng(33.77051,-84.39607),
                new LatLng(33.77027,-84.39607),
                new LatLng(33.77002,-84.39608),
                new LatLng(33.76968,-84.39608),
                new LatLng(33.7695,-84.39608),
                new LatLng(33.76937,-84.39608),
                new LatLng(33.76921,-84.39608),
                new LatLng(33.76888,-84.39608),
                new LatLng(33.76888,-84.39608),
                new LatLng(33.76888,-84.39583),
                new LatLng(33.76888,-84.39565),
                new LatLng(33.76888,-84.39531),
                new LatLng(33.76887,-84.39511),
                new LatLng(33.76887,-84.39454),
                new LatLng(33.76887,-84.39421),
                new LatLng(33.76886,-84.39413),
                new LatLng(33.76885,-84.39407),
                new LatLng(33.76881,-84.39397),
                new LatLng(33.7688,-84.39395),
                new LatLng(33.76878,-84.39391),
                new LatLng(33.76875,-84.39386),
                new LatLng(33.76873,-84.39381),
                new LatLng(33.76871,-84.39374),
                new LatLng(33.7687,-84.39367),
                new LatLng(33.76869,-84.39358),
                new LatLng(33.76868,-84.39345),
                new LatLng(33.7687,-84.39328),
                new LatLng(33.76871,-84.39319),
                new LatLng(33.76874,-84.3931),
                new LatLng(33.76877,-84.39303),
                new LatLng(33.76884,-84.39289),
                new LatLng(33.76887,-84.39281),
                new LatLng(33.76889,-84.39275),
                new LatLng(33.76889,-84.39269),
                new LatLng(33.76889,-84.39262),
                new LatLng(33.76889,-84.39254),
                new LatLng(33.76889,-84.3924),
                new LatLng(33.76889,-84.39237),
                new LatLng(33.7689,-84.39214),
                new LatLng(33.7689,-84.39214),
                new LatLng(33.76897,-84.39216),
                new LatLng(33.769,-84.39216),
                new LatLng(33.76905,-84.39217),
                new LatLng(33.76912,-84.39217),
                new LatLng(33.76973,-84.39216),
                new LatLng(33.76978,-84.39216),
                new LatLng(33.76991,-84.39215),
                new LatLng(33.76996,-84.39215),
                new LatLng(33.76996,-84.39215),
                new LatLng(33.76995,-84.39174),
                new LatLng(33.76996,-84.3917),
                new LatLng(33.76997,-84.39167),
                new LatLng(33.76998,-84.39165),
                new LatLng(33.77,-84.39163),
                new LatLng(33.77002,-84.39163),
                new LatLng(33.77003,-84.39163),
                new LatLng(33.77009,-84.39163),
                new LatLng(33.77012,-84.39163),
                new LatLng(33.77015,-84.39166),
                new LatLng(33.77016,-84.39168),
                new LatLng(33.77017,-84.39171),
                new LatLng(33.77017,-84.39174)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0x00,0xff));
        Blue[7] = new PolylineOptions().add(
                new LatLng(33.77017,-84.39176),
                new LatLng(33.77016,-84.39215),
                new LatLng(33.77016,-84.39215),
                new LatLng(33.77059,-84.39214),
                new LatLng(33.77089,-84.39213),
                new LatLng(33.77095,-84.39213),
                new LatLng(33.77104,-84.39212),
                new LatLng(33.77115,-84.3921),
                new LatLng(33.77129,-84.39206),
                new LatLng(33.77129,-84.39206),
                new LatLng(33.77139,-84.39203),
                new LatLng(33.77154,-84.39202),
                new LatLng(33.77186,-84.39201)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0x00,0xff));
        Blue[8] = new PolylineOptions().add(
                new LatLng(33.77186,-84.39201),
                new LatLng(33.77191,-84.392),
                new LatLng(33.77227,-84.392),
                new LatLng(33.77262,-84.39199),
                new LatLng(33.77293,-84.39199),
                new LatLng(33.773,-84.39199),
                new LatLng(33.77326,-84.39199),
                new LatLng(33.77389,-84.39198),
                new LatLng(33.77406,-84.39198)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0x00,0xff));
        Blue[9] = new PolylineOptions().add(
                new LatLng(33.77406,-84.39198),
                new LatLng(33.77427,-84.39198),
                new LatLng(33.77432,-84.39198),
                new LatLng(33.77439,-84.39198),
                new LatLng(33.77444,-84.39198),
                new LatLng(33.77484,-84.39197),
                new LatLng(33.77497,-84.39197),
                new LatLng(33.77507,-84.39197)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0x00,0xff));
        Blue[10] = new PolylineOptions().add(
                new LatLng(33.77507,-84.39197),
                new LatLng(33.77514,-84.39197),
                new LatLng(33.77528,-84.39197),
                new LatLng(33.77536,-84.39197),
                new LatLng(33.77545,-84.39197),
                new LatLng(33.7758,-84.39198),
                new LatLng(33.77588,-84.392),
                new LatLng(33.77629,-84.39204),
                new LatLng(33.77641,-84.39206)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0x00,0xff));
        Blue[11] = new PolylineOptions().add(
                new LatLng(33.77641,-84.39206),
                new LatLng(33.77647,-84.39207),
                new LatLng(33.7768,-84.3921),
                new LatLng(33.77689,-84.3921),
                new LatLng(33.77689,-84.3921),
                new LatLng(33.77689,-84.39256),
                new LatLng(33.7769,-84.39311),
                new LatLng(33.7769,-84.39311),
                new LatLng(33.77691,-84.39363),
                new LatLng(33.77692,-84.39387),
                new LatLng(33.77692,-84.39424)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0x00,0xff));
        Blue[12] = new PolylineOptions().add(
                new LatLng(33.77692,-84.39424),
                new LatLng(33.77694,-84.39507),
                new LatLng(33.77694,-84.3951),
                new LatLng(33.77695,-84.39513),
                new LatLng(33.77697,-84.39521),
                new LatLng(33.77699,-84.39525),
                new LatLng(33.777,-84.39527),
                new LatLng(33.77701,-84.39529),
                new LatLng(33.77703,-84.39531),
                new LatLng(33.77704,-84.39533),
                new LatLng(33.77705,-84.39535),
                new LatLng(33.77707,-84.39536),
                new LatLng(33.77711,-84.3954),
                new LatLng(33.77716,-84.39545),
                new LatLng(33.77753,-84.39569),
                new LatLng(33.77755,-84.3957),
                new LatLng(33.77764,-84.39577)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0x00,0xff));
        Blue[13] = new PolylineOptions().add(
                new LatLng(33.77764,-84.39577),
                new LatLng(33.77772,-84.39582),
                new LatLng(33.77775,-84.39584),
                new LatLng(33.77781,-84.39588),
                new LatLng(33.7779,-84.39598),
                new LatLng(33.77794,-84.39603),
                new LatLng(33.77798,-84.39608),
                new LatLng(33.778,-84.39611),
                new LatLng(33.77804,-84.39618),
                new LatLng(33.7781,-84.39629),
                new LatLng(33.77816,-84.39644),
                new LatLng(33.7782,-84.39662),
                new LatLng(33.77821,-84.39666),
                new LatLng(33.77822,-84.39673),
                new LatLng(33.77823,-84.39681),
                new LatLng(33.77824,-84.39691),
                new LatLng(33.77824,-84.39719),
                new LatLng(33.77825,-84.39766),
                new LatLng(33.77825,-84.3978),
                new LatLng(33.77826,-84.39808)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0x00,0xff));
        Blue[14] = new PolylineOptions().add(
                new LatLng(33.77826,-84.39808),
                new LatLng(33.77826,-84.39836),
                new LatLng(33.77827,-84.39913),
                new LatLng(33.77827,-84.39922),
                new LatLng(33.77828,-84.39937),
                new LatLng(33.77828,-84.39943),
                new LatLng(33.77829,-84.3995),
                new LatLng(33.77839,-84.39996),
                new LatLng(33.77839,-84.39996),
                new LatLng(33.77844,-84.39995)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0x00,0xff));
        Blue[15] = new PolylineOptions().add(
                new LatLng(33.77844,-84.39995),
                new LatLng(33.77839,-84.39996),
                new LatLng(33.77839,-84.39996),
                new LatLng(33.77846,-84.4002),
                new LatLng(33.77848,-84.40038),
                new LatLng(33.77849,-84.40048),
                new LatLng(33.77849,-84.40056),
                new LatLng(33.7785,-84.40061),
                new LatLng(33.77849,-84.40068),
                new LatLng(33.77849,-84.40077),
                new LatLng(33.7785,-84.40085),
                new LatLng(33.77849,-84.40101),
                new LatLng(33.77846,-84.40114),
                new LatLng(33.7784,-84.4013),
                new LatLng(33.77838,-84.40133),
                new LatLng(33.77836,-84.40136),
                new LatLng(33.77825,-84.40154),
                new LatLng(33.77812,-84.40175)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0x00,0xff));
        Blue[16] = new PolylineOptions().add(
                new LatLng(33.77812,-84.40175),
                new LatLng(33.77796,-84.40199),
                new LatLng(33.77787,-84.40209),
                new LatLng(33.7778,-84.40215),
                new LatLng(33.77773,-84.40221),
                new LatLng(33.77767,-84.40225),
                new LatLng(33.7776,-84.40229),
                new LatLng(33.77753,-84.40233),
                new LatLng(33.77745,-84.40236),
                new LatLng(33.77733,-84.40239),
                new LatLng(33.77729,-84.4024),
                new LatLng(33.77729,-84.4024),
                new LatLng(33.77728,-84.40378),
                new LatLng(33.77728,-84.40417),
                new LatLng(33.77728,-84.40417),
                new LatLng(33.77733,-84.40422),
                new LatLng(33.77737,-84.40423),
                new LatLng(33.77774,-84.40421),
                new LatLng(33.77797,-84.40421),
                new LatLng(33.77804,-84.4042),
                new LatLng(33.77827,-84.4042)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0x00,0xff));

        /**
         * stop information: Red
         * ***********************************************************************************************************
         * ***********************************************************************************************************
         */

        Red_stop.add("fitthall");
        Red_stop_position.add(new LatLng(33.778274,-84.404191));
        Red_stop.add("mcmil8th");
        Red_stop_position.add(new LatLng(33.779599,-84.404164));
        Red_stop.add("8thhemp");
        Red_stop_position.add(new LatLng(33.779631,-84.40274));
        Red_stop.add("fershemp");
        Red_stop_position.add(new LatLng(33.778363,-84.401007));
        Red_stop.add("fersstat");
        Red_stop_position.add(new LatLng(33.778293,-84.399279));
        Red_stop.add("fersatla");
        Red_stop_position.add(new LatLng(33.77819,-84.397491));
        Red_stop.add("klaubldg");
        Red_stop_position.add(new LatLng(33.777439,-84.395508));
        Red_stop.add("fersfowl");
        Red_stop_position.add(new LatLng(33.776925,-84.393671));
        Red_stop.add("tech5th");
        Red_stop_position.add(new LatLng(33.776401,-84.392123));
        Red_stop.add("tech4th");
        Red_stop_position.add(new LatLng(33.774954,-84.392049));
        Red_stop.add("techbob");
        Red_stop_position.add(new LatLng(33.773667,-84.39205));
        Red_stop.add("technorth");
        Red_stop_position.add(new LatLng(33.77145,-84.3921));
        Red_stop.add("naveapts_a");
        Red_stop_position.add(new LatLng(33.76994,-84.391629));
        Red_stop.add("ferstcher");
        Red_stop_position.add(new LatLng(33.772284,-84.39548));
        Red_stop.add("centrstud");
        Red_stop_position.add(new LatLng(33.77346,-84.399159));
        Red_stop.add("hubfers");
        Red_stop_position.add(new LatLng(33.77276,-84.396983));
        Red_stop.add("creccent");
        Red_stop_position.add(new LatLng(33.774997,-84.402359));



        /**
         * route shape information: Red
         * ***********************************************************************************************************
         * ***********************************************************************************************************
         */
        Red[0] = new PolylineOptions().add(
                new LatLng(33.77827,-84.4042),
                new LatLng(33.77844,-84.40419),
                new LatLng(33.77868,-84.40419),
                new LatLng(33.77876,-84.40418),
                new LatLng(33.77902,-84.40418),
                new LatLng(33.77906,-84.40418),
                new LatLng(33.77931,-84.40418),
                new LatLng(33.7796,-84.40417)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0x11,0x00));
        Red[1] = new PolylineOptions().add(
                new LatLng(33.7796,-84.40417),
                new LatLng(33.77964,-84.40417),
                new LatLng(33.77964,-84.40417),
                new LatLng(33.77964,-84.40401),
                new LatLng(33.77964,-84.40315),
                new LatLng(33.77964,-84.40286),
                new LatLng(33.77964,-84.40274)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0x11,0x00));
        Red[2] = new PolylineOptions().add(
                new LatLng(33.77964,-84.40274),
                new LatLng(33.77964,-84.40267),
                new LatLng(33.77964,-84.40263),
                new LatLng(33.77966,-84.40259),
                new LatLng(33.7797,-84.40252),
                new LatLng(33.7797,-84.40252),
                new LatLng(33.77964,-84.40247),
                new LatLng(33.77936,-84.4022),
                new LatLng(33.77922,-84.40207),
                new LatLng(33.77912,-84.40198),
                new LatLng(33.77897,-84.40184),
                new LatLng(33.77866,-84.40152),
                new LatLng(33.7784,-84.4013),
                new LatLng(33.7784,-84.4013),
                new LatLng(33.77846,-84.40114),
                new LatLng(33.77848,-84.40105)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0x11,0x00));
        Red[3] = new PolylineOptions().add(
                new LatLng(33.77848,-84.40105),
                new LatLng(33.77849,-84.40101),
                new LatLng(33.7785,-84.40085),
                new LatLng(33.77849,-84.40077),
                new LatLng(33.77849,-84.40068),
                new LatLng(33.7785,-84.40061),
                new LatLng(33.77849,-84.40056),
                new LatLng(33.77849,-84.40048),
                new LatLng(33.77848,-84.40038),
                new LatLng(33.77846,-84.4002),
                new LatLng(33.77839,-84.39996),
                new LatLng(33.77829,-84.3995),
                new LatLng(33.77828,-84.39943),
                new LatLng(33.77828,-84.39937),
                new LatLng(33.77828,-84.39928)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0x11,0x00));
        Red[4] = new PolylineOptions().add(
                new LatLng(33.77827,-84.39928),
                new LatLng(33.77827,-84.39922),
                new LatLng(33.77827,-84.39913),
                new LatLng(33.77826,-84.39836),
                new LatLng(33.77825,-84.3978),
                new LatLng(33.77825,-84.39766),
                new LatLng(33.77825,-84.39749)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0x11,0x00));
        Red[5] = new PolylineOptions().add(
                new LatLng(33.77825,-84.39749),
                new LatLng(33.77824,-84.39719),
                new LatLng(33.77824,-84.39691),
                new LatLng(33.77823,-84.39681),
                new LatLng(33.77822,-84.39673),
                new LatLng(33.77821,-84.39666),
                new LatLng(33.7782,-84.39662),
                new LatLng(33.77816,-84.39644),
                new LatLng(33.7781,-84.39629),
                new LatLng(33.77804,-84.39618),
                new LatLng(33.778,-84.39611),
                new LatLng(33.77798,-84.39608),
                new LatLng(33.77794,-84.39603),
                new LatLng(33.7779,-84.39598),
                new LatLng(33.77781,-84.39588),
                new LatLng(33.77775,-84.39584),
                new LatLng(33.77772,-84.39582),
                new LatLng(33.77764,-84.39577),
                new LatLng(33.77755,-84.3957),
                new LatLng(33.77753,-84.39569),
                new LatLng(33.7774,-84.3956)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0x11,0x00));
        Red[6] = new PolylineOptions().add(
                new LatLng(33.7774,-84.3956),
                new LatLng(33.77716,-84.39545),
                new LatLng(33.77711,-84.3954),
                new LatLng(33.77707,-84.39536),
                new LatLng(33.77705,-84.39535),
                new LatLng(33.77704,-84.39533),
                new LatLng(33.77703,-84.39531),
                new LatLng(33.77701,-84.39529),
                new LatLng(33.777,-84.39527),
                new LatLng(33.77699,-84.39525),
                new LatLng(33.77697,-84.39521),
                new LatLng(33.77695,-84.39513),
                new LatLng(33.77694,-84.3951),
                new LatLng(33.77694,-84.39507),
                new LatLng(33.77692,-84.39424),
                new LatLng(33.77692,-84.39387),
                new LatLng(33.77691,-84.39367)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0x11,0x00));
        Red[7] = new PolylineOptions().add(
                new LatLng(33.77691,-84.39367),
                new LatLng(33.77691,-84.39363),
                new LatLng(33.77691,-84.39363),
                new LatLng(33.7769,-84.39311),
                new LatLng(33.77689,-84.39256),
                new LatLng(33.77689,-84.3921),
                new LatLng(33.77689,-84.3921),
                new LatLng(33.7768,-84.3921),
                new LatLng(33.77647,-84.39207),
                new LatLng(33.77641,-84.39206)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0x11,0x00));
        Red[8] = new PolylineOptions().add(
                new LatLng(33.77641,-84.39206),
                new LatLng(33.77629,-84.39204),
                new LatLng(33.77588,-84.392),
                new LatLng(33.7758,-84.39198),
                new LatLng(33.77545,-84.39197),
                new LatLng(33.77536,-84.39197),
                new LatLng(33.77528,-84.39197),
                new LatLng(33.77514,-84.39197),
                new LatLng(33.77497,-84.39197)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0x11,0x00));
        Red[9] = new PolylineOptions().add(
                new LatLng(33.77497,-84.39197),
                new LatLng(33.77484,-84.39197),
                new LatLng(33.77444,-84.39198),
                new LatLng(33.77439,-84.39198),
                new LatLng(33.77432,-84.39198),
                new LatLng(33.77427,-84.39198),
                new LatLng(33.77389,-84.39198),
                new LatLng(33.77367,-84.39198)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0x11,0x00));
        Red[10] = new PolylineOptions().add(
                new LatLng(33.77367,-84.39198),
                new LatLng(33.77326,-84.39199),
                new LatLng(33.773,-84.39199),
                new LatLng(33.77293,-84.39199),
                new LatLng(33.77262,-84.39199),
                new LatLng(33.77227,-84.392),
                new LatLng(33.77191,-84.392),
                new LatLng(33.77154,-84.39202),
                new LatLng(33.77145,-84.39202)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0x11,0x00));
        Red[11] = new PolylineOptions().add(
                new LatLng(33.77145,-84.39202),
                new LatLng(33.77139,-84.39203),
                new LatLng(33.77129,-84.39206),
                new LatLng(33.77129,-84.39206),
                new LatLng(33.77115,-84.3921),
                new LatLng(33.77104,-84.39212),
                new LatLng(33.77095,-84.39213),
                new LatLng(33.77089,-84.39213),
                new LatLng(33.77059,-84.39214),
                new LatLng(33.77016,-84.39215),
                new LatLng(33.76996,-84.39215),
                new LatLng(33.76996,-84.39215),
                new LatLng(33.76995,-84.39174),
                new LatLng(33.76996,-84.3917)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0x11,0x00));
        Red[12] = new PolylineOptions().add(
                new LatLng(33.76996,-84.3917),
                new LatLng(33.76997,-84.39167),
                new LatLng(33.76998,-84.39165),
                new LatLng(33.77,-84.39163),
                new LatLng(33.77002,-84.39163),
                new LatLng(33.77003,-84.39163),
                new LatLng(33.77009,-84.39163),
                new LatLng(33.77012,-84.39163),
                new LatLng(33.77015,-84.39166),
                new LatLng(33.77016,-84.39168),
                new LatLng(33.77017,-84.39171),
                new LatLng(33.77017,-84.39174),
                new LatLng(33.77017,-84.39176),
                new LatLng(33.77016,-84.39215),
                new LatLng(33.77016,-84.39215),
                new LatLng(33.77059,-84.39214),
                new LatLng(33.77089,-84.39213),
                new LatLng(33.77095,-84.39213),
                new LatLng(33.77104,-84.39212),
                new LatLng(33.77115,-84.3921),
                new LatLng(33.77129,-84.39206),
                new LatLng(33.77129,-84.39206),
                new LatLng(33.77128,-84.39282),
                new LatLng(33.77128,-84.39285),
                new LatLng(33.77129,-84.39298),
                new LatLng(33.77132,-84.39328),
                new LatLng(33.77132,-84.39368),
                new LatLng(33.77132,-84.39372),
                new LatLng(33.77132,-84.39381),
                new LatLng(33.77132,-84.39399),
                new LatLng(33.77132,-84.39455),
                new LatLng(33.77132,-84.39478),
                new LatLng(33.77132,-84.39492),
                new LatLng(33.77133,-84.39552),
                new LatLng(33.77133,-84.39552),
                new LatLng(33.77177,-84.39552),
                new LatLng(33.77228,-84.3955)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0x11,0x00));
        Red[13] = new PolylineOptions().add(
                new LatLng(33.77228,-84.3955),
                new LatLng(33.7723,-84.3955),
                new LatLng(33.77236,-84.3955),
                new LatLng(33.77236,-84.3955),
                new LatLng(33.77236,-84.39567),
                new LatLng(33.77237,-84.39611),
                new LatLng(33.77237,-84.39629),
                new LatLng(33.77237,-84.39645),
                new LatLng(33.77238,-84.39652),
                new LatLng(33.77241,-84.39664),
                new LatLng(33.77245,-84.39672),
                new LatLng(33.77266,-84.39702),
                new LatLng(33.7728,-84.3972),
                new LatLng(33.77287,-84.39732),
                new LatLng(33.77297,-84.39746),
                new LatLng(33.77302,-84.39759),
                new LatLng(33.77309,-84.39774),
                new LatLng(33.77316,-84.3979),
                new LatLng(33.77318,-84.39797),
                new LatLng(33.77318,-84.39799),
                new LatLng(33.77322,-84.39814),
                new LatLng(33.77325,-84.39831),
                new LatLng(33.77329,-84.39862),
                new LatLng(33.77337,-84.39913)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0x11,0x00));
        Red[14] = new PolylineOptions().add(
                new LatLng(33.77337,-84.39913),
                new LatLng(33.77329,-84.39862),
                new LatLng(33.77325,-84.39831),
                new LatLng(33.77322,-84.39814),
                new LatLng(33.77318,-84.39799),
                new LatLng(33.77318,-84.39797),
                new LatLng(33.77316,-84.3979),
                new LatLng(33.77309,-84.39774),
                new LatLng(33.77302,-84.39759),
                new LatLng(33.77297,-84.39746),
                new LatLng(33.77287,-84.39732),
                new LatLng(33.7728,-84.3972),
                new LatLng(33.77269,-84.39706)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0x11,0x00));
        Red[15] = new PolylineOptions().add(
                new LatLng(33.77269,-84.39706),
                new LatLng(33.7728,-84.3972),
                new LatLng(33.77287,-84.39732),
                new LatLng(33.77297,-84.39746),
                new LatLng(33.77302,-84.39759),
                new LatLng(33.77309,-84.39774),
                new LatLng(33.77316,-84.3979),
                new LatLng(33.77318,-84.39797),
                new LatLng(33.77318,-84.39799),
                new LatLng(33.77322,-84.39814),
                new LatLng(33.77325,-84.39831),
                new LatLng(33.77329,-84.39862),
                new LatLng(33.77337,-84.39913),
                new LatLng(33.77339,-84.39924),
                new LatLng(33.77339,-84.39929),
                new LatLng(33.7737,-84.40123),
                new LatLng(33.77376,-84.40134),
                new LatLng(33.77378,-84.40147),
                new LatLng(33.77381,-84.4016),
                new LatLng(33.77387,-84.40176),
                new LatLng(33.77391,-84.40185),
                new LatLng(33.77395,-84.40193),
                new LatLng(33.77402,-84.40204),
                new LatLng(33.77406,-84.4021),
                new LatLng(33.77411,-84.40216),
                new LatLng(33.77422,-84.40228),
                new LatLng(33.77431,-84.40236),
                new LatLng(33.77438,-84.40244),
                new LatLng(33.77458,-84.40253),
                new LatLng(33.774612,-84.402378),
                new LatLng(33.774656,-84.402303),
                new LatLng(33.774724,-84.402278),
                new LatLng(33.774782,-84.402287),
                new LatLng(33.774994,-84.402373)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0x11,0x00));
        Red[16] = new PolylineOptions().add(
                new LatLng(33.774994,-84.402373),
                new LatLng(33.775141,-84.402425),
                new LatLng(33.775206,-84.402479),
                new LatLng(33.77528,-84.40257),
                new LatLng(33.77542,-84.40256),
                new LatLng(33.77551,-84.40255),
                new LatLng(33.77566,-84.40254),
                new LatLng(33.77612,-84.40249),
                new LatLng(33.77634,-84.40247),
                new LatLng(33.77656,-84.40246),
                new LatLng(33.77661,-84.40246),
                new LatLng(33.77729,-84.4024),
                new LatLng(33.77729,-84.4024),
                new LatLng(33.77728,-84.40378),
                new LatLng(33.77728,-84.40417),
                new LatLng(33.77728,-84.40417),
                new LatLng(33.77733,-84.40422),
                new LatLng(33.77737,-84.40423),
                new LatLng(33.77774,-84.40421),
                new LatLng(33.77797,-84.40421),
                new LatLng(33.77804,-84.4042),
                new LatLng(33.77827,-84.4042)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0xff,0x11,0x00));



        /**
         * stop information: Green
         * ***********************************************************************************************************
         * ***********************************************************************************************************
         */
        Green_stop.add("tranhub");
        Green_stop_position.add(new LatLng(33.773226,-84.397016));
        Green_stop.add("studcent_ib");
        Green_stop_position.add(new LatLng(33.77335,-84.39917));
        Green_stop.add("creccent");
        Green_stop_position.add(new LatLng(33.774997,-84.402359));
        Green_stop.add("fershemp");
        Green_stop_position.add(new LatLng(33.778363,-84.401007));
        Green_stop.add("fersstat");
        Green_stop_position.add(new LatLng(33.778293,-84.399279));
        Green_stop.add("ndec");
        Green_stop_position.add(new LatLng(33.780233,-84.39905));
        Green_stop.add("10thhemp");
        Green_stop_position.add(new LatLng(33.781619,-84.404082));
        Green_stop.add("hempcurr");
        Green_stop_position.add(new LatLng(33.784665,-84.405937));
        Green_stop.add("14thbusy_a");
        Green_stop_position.add(new LatLng(33.78617,-84.405241));
        Green_stop.add("14thstat");
        Green_stop_position.add(new LatLng(33.78613,-84.398799));
        Green_stop.add("gcat");
        Green_stop_position.add(new LatLng(33.78628,-84.39559));
        Green_stop.add("glc");
        Green_stop_position.add(new LatLng(33.78158,-84.39645));
        Green_stop.add("bakebldg");
        Green_stop_position.add(new LatLng(33.780355,-84.39928));
        Green_stop.add("fersstat_ob");
        Green_stop_position.add(new LatLng(33.778436,-84.399961));
        Green_stop.add("fershemp_ob");
        Green_stop_position.add(new LatLng(33.778141,-84.401829));
        Green_stop.add("reccent_ob");
        Green_stop_position.add(new LatLng(33.7751,-84.40265));
        Green_stop.add("studcent");
        Green_stop_position.add(new LatLng(33.77335,-84.39917));


        /**
         * route shape information: Green
         * ***********************************************************************************************************
         * ***********************************************************************************************************
         */

        Green[0] = new PolylineOptions().add(
                new LatLng(33.773226,-84.397016),
                new LatLng(33.773222,-84.396995),
                new LatLng(33.773049,-84.396998),
                new LatLng(33.772979,-84.397024),
                new LatLng(33.772800,-84.397205),
                new LatLng(33.772969,-84.397464),
                new LatLng(33.773120,-84.397801),
                new LatLng(33.77318,-84.39797),
                new LatLng(33.77318,-84.39799),
                new LatLng(33.77322,-84.39814),
                new LatLng(33.77325,-84.39831),
                new LatLng(33.77329,-84.39862),
                new LatLng(33.77337,-84.39913)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0xcc,0x66));
        Green[1] = new PolylineOptions().add(
                new LatLng(33.77337,-84.39913),
                new LatLng(33.77339,-84.39924),
                new LatLng(33.77339,-84.39929),
                new LatLng(33.7737,-84.40123),
                new LatLng(33.77376,-84.40134),
                new LatLng(33.77378,-84.40147),
                new LatLng(33.77381,-84.4016),
                new LatLng(33.77387,-84.40176),
                new LatLng(33.77391,-84.40185),
                new LatLng(33.77395,-84.40193),
                new LatLng(33.77402,-84.40204),
                new LatLng(33.77406,-84.4021),
                new LatLng(33.77411,-84.40216),
                new LatLng(33.77422,-84.40228),
                new LatLng(33.77431,-84.40236),
                new LatLng(33.77438,-84.40244),
                new LatLng(33.77458,-84.40253),
                new LatLng(33.774612,-84.402378),
                new LatLng(33.774656,-84.402303),
                new LatLng(33.774724,-84.402278),
                new LatLng(33.774782,-84.402287),
                new LatLng(33.774994,-84.402373)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0xcc,0x66));
        Green[2] = new PolylineOptions().add(
                new LatLng(33.774994,-84.402373),
                new LatLng(33.775141,-84.402425),
                new LatLng(33.775206,-84.402479),
                new LatLng(33.77528,-84.40257),
                new LatLng(33.77542,-84.40256),
                new LatLng(33.77551,-84.40255),
                new LatLng(33.77566,-84.40254),
                new LatLng(33.77612,-84.40249),
                new LatLng(33.77634,-84.40247),
                new LatLng(33.77656,-84.40246),
                new LatLng(33.77661,-84.40246),
                new LatLng(33.77729,-84.4024),
                new LatLng(33.77733,-84.40239),
                new LatLng(33.77745,-84.40236),
                new LatLng(33.77753,-84.40233),
                new LatLng(33.7776,-84.40229),
                new LatLng(33.77767,-84.40225),
                new LatLng(33.77773,-84.40221),
                new LatLng(33.7778,-84.40215),
                new LatLng(33.77787,-84.40209),
                new LatLng(33.77796,-84.40199),
                new LatLng(33.77812,-84.40175),
                new LatLng(33.77825,-84.40154),
                new LatLng(33.77836,-84.40136),
                new LatLng(33.77838,-84.40133),
                new LatLng(33.7784,-84.4013),
                new LatLng(33.77846,-84.40114),
                new LatLng(33.77848,-84.40105)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0xcc,0x66));
        Green[3] = new PolylineOptions().add(
                new LatLng(33.77848,-84.40105),
                new LatLng(33.77849,-84.40101),
                new LatLng(33.7785,-84.40085),
                new LatLng(33.77849,-84.40077),
                new LatLng(33.77849,-84.40068),
                new LatLng(33.7785,-84.40061),
                new LatLng(33.77849,-84.40056),
                new LatLng(33.77849,-84.40048),
                new LatLng(33.77848,-84.40038),
                new LatLng(33.77846,-84.4002),
                new LatLng(33.77839,-84.39996),
                new LatLng(33.77829,-84.3995),
                new LatLng(33.77828,-84.39943),
                new LatLng(33.77828,-84.39937),
                new LatLng(33.77828,-84.39928)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0xcc,0x66));
        Green[4] = new PolylineOptions().add(
                new LatLng(33.77827,-84.39928),
                new LatLng(33.77827,-84.39922),
                new LatLng(33.77827,-84.39922),
                new LatLng(33.77827,-84.39913),
                new LatLng(33.77894,-84.39912),
                new LatLng(33.77906,-84.39911),
                new LatLng(33.77954,-84.39908),
                new LatLng(33.77963,-84.39908),
                new LatLng(33.78021,-84.39907),
                new LatLng(33.78023,-84.39907)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0xcc,0x66));
        Green[5] = new PolylineOptions().add(
                new LatLng(33.78023,-84.39907),
                new LatLng(33.78045,-84.39908),
                new LatLng(33.7805,-84.39909),
                new LatLng(33.7806,-84.39915),
                new LatLng(33.78152,-84.39917),
                new LatLng(33.78152,-84.39917),
                new LatLng(33.78151,-84.39991),
                new LatLng(33.78151,-84.40027),
                new LatLng(33.78152,-84.4006),
                new LatLng(33.78152,-84.40083),
                new LatLng(33.78152,-84.4013),
                new LatLng(33.78152,-84.40182),
                new LatLng(33.78153,-84.40219),
                new LatLng(33.78153,-84.40261),
                new LatLng(33.78153,-84.40275),
                new LatLng(33.78153,-84.40291),
                new LatLng(33.78153,-84.40337),
                new LatLng(33.78153,-84.40408)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0xcc,0x66));
        Green[6] = new PolylineOptions().add(
                new LatLng(33.78153,-84.40408),
                new LatLng(33.78154,-84.40419),
                new LatLng(33.78154,-84.40419),
                new LatLng(33.78172,-84.4043),
                new LatLng(33.7836,-84.40544),
                new LatLng(33.78453,-84.40598),
                new LatLng(33.78463,-84.40603)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0xcc,0x66));
        Green[7] = new PolylineOptions().add(
                new LatLng(33.78463,-84.40604),
                new LatLng(33.78477,-84.40612),
                new LatLng(33.78477,-84.40612),
                new LatLng(33.78481,-84.40609),
                new LatLng(33.78484,-84.40608),
                new LatLng(33.78488,-84.40607),
                new LatLng(33.7849,-84.40607),
                new LatLng(33.78492,-84.40606),
                new LatLng(33.78493,-84.40606),
                new LatLng(33.78495,-84.40606),
                new LatLng(33.78499,-84.40606),
                new LatLng(33.78557,-84.40606),
                new LatLng(33.78557,-84.40606),
                new LatLng(33.78622,-84.40606),
                new LatLng(33.78622,-84.40606),
                new LatLng(33.78622,-84.40564),
                new LatLng(33.78622,-84.40524),
                new LatLng(33.78622,-84.40524),
                new LatLng(33.78617,-84.40524)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0xcc,0x66));
        Green[8] = new PolylineOptions().add(
                new LatLng(33.78617,-84.40524),
                new LatLng(33.78622,-84.40524),
                new LatLng(33.78622,-84.40524),
                new LatLng(33.78622,-84.40486),
                new LatLng(33.78622,-84.40425),
                new LatLng(33.78621,-84.40282),
                new LatLng(33.7862,-84.40209),
                new LatLng(33.7862,-84.40188),
                new LatLng(33.7862,-84.40137),
                new LatLng(33.7862,-84.40065),
                new LatLng(33.7862,-84.3998),
                new LatLng(33.78619,-84.3995),
                new LatLng(33.78619,-84.3988)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0xcc,0x66));
        Green[9] = new PolylineOptions().add(
                new LatLng(33.78619,-84.3988),
                new LatLng(33.78619,-84.39874),
                new LatLng(33.78619,-84.39768),
                new LatLng(33.78619,-84.39721),
                new LatLng(33.78622,-84.3969),
                new LatLng(33.78624,-84.39673),
                new LatLng(33.7863,-84.39617),
                new LatLng(33.78632,-84.39604),
                new LatLng(33.78635,-84.3958),
                new LatLng(33.78637,-84.3956)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0xcc,0x66));
        Green[10] = new PolylineOptions().add(
                new LatLng(33.78637,-84.3956),
                new LatLng(33.78641,-84.39522),
                new LatLng(33.78645,-84.3948),
                new LatLng(33.78645,-84.39462),
                new LatLng(33.78646,-84.39452),
                new LatLng(33.78649,-84.39401),
                new LatLng(33.7865,-84.39391),
                new LatLng(33.78654,-84.39327),
                new LatLng(33.78655,-84.39262),
                new LatLng(33.78656,-84.39194),
                new LatLng(33.78656,-84.39194),
                new LatLng(33.78599,-84.39196),
                new LatLng(33.78542,-84.39197),
                new LatLng(33.78512,-84.39197),
                new LatLng(33.78476,-84.39197),
                new LatLng(33.78437,-84.39197),
                new LatLng(33.78437,-84.39197),
                new LatLng(33.78434,-84.39199),
                new LatLng(33.78432,-84.392),
                new LatLng(33.7843,-84.392),
                new LatLng(33.78428,-84.392),
                new LatLng(33.7842,-84.392),
                new LatLng(33.78419,-84.392),
                new LatLng(33.78393,-84.392),
                new LatLng(33.78322,-84.39199),
                new LatLng(33.78302,-84.39196),
                new LatLng(33.78297,-84.39195),
                new LatLng(33.7829,-84.39194),
                new LatLng(33.78274,-84.39194),
                new LatLng(33.78257,-84.39194),
                new LatLng(33.78225,-84.39194),
                new LatLng(33.78218,-84.39194),
                new LatLng(33.78218,-84.39194),
                new LatLng(33.78179,-84.39194),
                new LatLng(33.78155,-84.39195),
                new LatLng(33.78155,-84.39195),
                new LatLng(33.78155,-84.39229),
                new LatLng(33.78155,-84.39259),
                new LatLng(33.78154,-84.39269),
                new LatLng(33.78152,-84.39357),
                new LatLng(33.7815,-84.39443),
                new LatLng(33.7815,-84.39473),
                new LatLng(33.7815,-84.3949),
                new LatLng(33.78151,-84.39607),
                new LatLng(33.78151,-84.39641)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0xcc,0x66));
        Green[11] = new PolylineOptions().add(
                new LatLng(33.78151,-84.39641),
                new LatLng(33.78151,-84.39648),
                new LatLng(33.78151,-84.39772),
                new LatLng(33.78152,-84.39917),
                new LatLng(33.78152,-84.39917),
                new LatLng(33.7806,-84.39915),
                new LatLng(33.78051,-84.39919),
                new LatLng(33.78045,-84.39918),
                new LatLng(33.78035,-84.39919)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0xcc,0x66));
        Green[12] = new PolylineOptions().add(
                new LatLng(33.78035,-84.39919),
                new LatLng(33.7802,-84.39919),
                new LatLng(33.77967,-84.39919),
                new LatLng(33.77955,-84.39919),
                new LatLng(33.77892,-84.39922),
                new LatLng(33.77827,-84.39922),
                new LatLng(33.77827,-84.39922),
                new LatLng(33.77828,-84.39937),
                new LatLng(33.77828,-84.39943),
                new LatLng(33.77829,-84.3995),
                new LatLng(33.77839,-84.39996),
                new LatLng(33.77839,-84.39996),
                new LatLng(33.77844,-84.39995)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0xcc,0x66));
        Green[13] = new PolylineOptions().add(
                new LatLng(33.77844,-84.39995),
                new LatLng(33.77839,-84.39996),
                new LatLng(33.77839,-84.39996),
                new LatLng(33.77846,-84.4002),
                new LatLng(33.77848,-84.40038),
                new LatLng(33.77849,-84.40048),
                new LatLng(33.77849,-84.40056),
                new LatLng(33.7785,-84.40061),
                new LatLng(33.77849,-84.40068),
                new LatLng(33.77849,-84.40077),
                new LatLng(33.7785,-84.40085),
                new LatLng(33.77849,-84.40101),
                new LatLng(33.77846,-84.40114),
                new LatLng(33.7784,-84.4013),
                new LatLng(33.77838,-84.40133),
                new LatLng(33.77836,-84.40136),
                new LatLng(33.77825,-84.40154),
                new LatLng(33.77812,-84.40175)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0xcc,0x66));
        Green[14] = new PolylineOptions().add(
                new LatLng(33.77812,-84.40175),
                new LatLng(33.77796,-84.40199),
                new LatLng(33.77787,-84.40209),
                new LatLng(33.7778,-84.40215),
                new LatLng(33.77773,-84.40221),
                new LatLng(33.77767,-84.40225),
                new LatLng(33.7776,-84.40229),
                new LatLng(33.77753,-84.40233),
                new LatLng(33.77745,-84.40236),
                new LatLng(33.77733,-84.40239),
                new LatLng(33.77729,-84.4024),
                new LatLng(33.77661,-84.40246),
                new LatLng(33.77656,-84.40246),
                new LatLng(33.77634,-84.40247),
                new LatLng(33.77612,-84.40249),
                new LatLng(33.77566,-84.40254),
                new LatLng(33.77551,-84.40255),
                new LatLng(33.77542,-84.40256),
                new LatLng(33.77528,-84.40257),
                new LatLng(33.7751,-84.40259)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0xcc,0x66));
        Green[15] = new PolylineOptions().add(
                new LatLng(33.7751,-84.40259),
                new LatLng(33.77497,-84.4026),
                new LatLng(33.77491,-84.40261),
                new LatLng(33.77489,-84.40261),
                new LatLng(33.77485,-84.4026),
                new LatLng(33.7748,-84.4026),
                new LatLng(33.77475,-84.40259),
                new LatLng(33.77469,-84.40258),
                new LatLng(33.77463,-84.40255),
                new LatLng(33.77458,-84.40253),
                new LatLng(33.77438,-84.40244),
                new LatLng(33.77427,-84.40244),
                new LatLng(33.77418,-84.40236),
                new LatLng(33.77398,-84.40213),
                new LatLng(33.7739,-84.40201),
                new LatLng(33.77386,-84.40193),
                new LatLng(33.77383,-84.40186),
                new LatLng(33.77382,-84.40185),
                new LatLng(33.77379,-84.40176),
                new LatLng(33.77376,-84.40168),
                new LatLng(33.77373,-84.40157),
                new LatLng(33.77369,-84.40137),
                new LatLng(33.7737,-84.40123),
                new LatLng(33.77339,-84.39929),
                new LatLng(33.77339,-84.39924),
                new LatLng(33.77337,-84.39916)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0xcc,0x66));
        Green[16] = new PolylineOptions().add(
                new LatLng(33.77337,-84.39916),
                new LatLng(33.77337,-84.39913),
                new LatLng(33.77329,-84.39862),
                new LatLng(33.77325,-84.39831),
                new LatLng(33.77322,-84.39814),
                new LatLng(33.77318,-84.39799),
                new LatLng(33.77318,-84.39797),
                new LatLng(33.77316,-84.3979),
                new LatLng(33.77309,-84.39774),
                new LatLng(33.77302,-84.39759),
                new LatLng(33.77297,-84.39746),
                new LatLng(33.772799,-84.397200),
                new LatLng(33.772978,-84.397023),
                new LatLng(33.773059,-84.397185),
                new LatLng(33.773124,-84.397237),
                new LatLng(33.773209,-84.397247),
                new LatLng(33.773289,-84.397219),
                new LatLng(33.773317,-84.397136),
                new LatLng(33.773281,-84.397039),
                new LatLng(33.773226,-84.397016)
        )
                .width(mWIDTH)
                .clickable(false)
                .color(android.graphics.Color.rgb(0x00,0xcc,0x66));





    }



}



