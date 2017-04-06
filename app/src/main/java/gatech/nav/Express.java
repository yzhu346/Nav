package gatech.nav;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.LatLng;
/**
 * Created by yzhu3 on 4/5/2017.
 */

public class Express {
    private PolylineOptions express_shape = new PolylineOptions()
            .add(   new LatLng(33.77530,-84.39611),

                    new LatLng(33.775363, -84.395438),
                    new LatLng(33.775626, -84.394892),
                    new LatLng(33.775871, -84.394291),
                    new LatLng(33.775945, -84.393645),
                    new LatLng(33.776398, -84.393626),
                    new LatLng(33.776897, -84.393624),
                    new LatLng(33.776881, -84.392170),
                    new LatLng(33.776859, -84.390472),
                    new LatLng(33.7768,-84.38975),
                    new LatLng(33.776826, -84.388889),
                    new LatLng(33.77678,-84.38749),
                    new LatLng(33.776815, -84.387323),
                    new LatLng(33.777499, -84.387328),
                    new LatLng(33.777563, -84.387406),
                    new LatLng(33.777575, -84.388803),
                    new LatLng(33.776899, -84.388846),
                    new LatLng(33.776918, -84.389439),
                    new LatLng(33.77692,-84.38978),
                    new LatLng(33.77692,-84.38978),
                    new LatLng(33.776902, -84.391026),
                    new LatLng(33.776857, -84.392124),
                    new LatLng(33.776407, -84.392087),
                    new LatLng(33.775369, -84.391986),
                    new LatLng(33.775311, -84.392158),
                    new LatLng(33.775330, -84.392305),
                    new LatLng(33.775585, -84.392597),
                    new LatLng(33.775817, -84.393037),
                    new LatLng(33.775946, -84.393587),
                    new LatLng(33.775946, -84.394027),
                    new LatLng(33.775703, -84.394848),
                    new LatLng(33.775331, -84.395641),
                    new LatLng(33.77530,-84.39611)
            )
            .width(10)
            .clickable(false)
            .color(Color.GRAY);



    public void draw_express(GoogleMap map){
        Polyline express_line = map.addPolyline(express_shape);
        express_line.setTag("4thtotechwood");

    }
}
