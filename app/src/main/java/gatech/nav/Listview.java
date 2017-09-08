package gatech.nav;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;


/**
 * Created by yzhu3 on 4/15/2017.
 */

public class Listview extends Fragment implements AdapterView.OnItemClickListener {
    private static ArrayList<String> values;
    private static RouteAdaptor adapter;
    private static ListView list;
    private static Context context;
    private static GoogleMap map;
    private static Route route;
    private static boolean flag = false;
    private static ArrayList<Polyline> AllWalkingRoute = new ArrayList<Polyline>();
    private static Marker marker;
    private static LatLng MyLocation;

    public void setValues(ArrayList<String> data,Marker marker,LatLng MyLocation){
        values = data;
        this.marker = marker;
        this.MyLocation = MyLocation;
    }

    public void init(GoogleMap map,Route route){
        this.map = map;
        this.route = route;

    }

    public ArrayList<Polyline> getWalkingRoute(){
        return AllWalkingRoute;
    }

    public void clearRoute(){
        AllWalkingRoute.clear();
    }

    public boolean getFlag(){
        return flag;
    }


    public void update(){
        if(values.size()==0)
            flag = false;
        else
            flag = true;
        list.setAdapter(new RouteAdaptor(context, R.layout.list_view, values));
        adapter.setData(values);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        final ListView lv = (ListView)view.findViewById(android.R.id.list);
        list = lv;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);

        values = new ArrayList<String>();
        context = getActivity();
        adapter = new RouteAdaptor(getActivity(), R.layout.list_view, values);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        String r = values.get(position);

        String start_stop = new String();
        String end_stop = new String();
        String route_id = new String();
        String total_time = new String();

        StringTokenizer st = new StringTokenizer(r);
        while (st.hasMoreElements()) {
            start_stop = st.nextElement().toString();
            end_stop = st.nextElement().toString();
            route_id = st.nextElement().toString();
            total_time = st.nextElement().toString();
        }
        switch (route_id){
            case "walk" :
                drawWalkRoute(MyLocation,marker.getPosition());
                break;
            default:
                route.drawBetweenStop(route_id, start_stop, end_stop, map);
                drawWalkRoute(MyLocation, route.getStopLatLng(start_stop, route_id));
                drawWalkRoute(route.getStopLatLng(end_stop, route_id), marker.getPosition());
                break;
        }

        values.clear();
        update();

    }

    private void drawWalkRoute(LatLng start, LatLng dest){
        String url = getDirectionsUrl(start,dest);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }

    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        //walking mode
        String mode = "mode=walking";


        String parameters = str_origin+"&"+str_dest+"&"+sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    //* A method to download json data from url
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception download url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service

            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    //* A class to parse the Google Places in JSON format
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.BLUE);
                List<PatternItem> pattern = Arrays.<PatternItem>asList(
                        new Dash(20), new Gap(20));

                AllWalkingRoute.add(map.addPolyline(lineOptions));
                AllWalkingRoute.get(AllWalkingRoute.size()-1).setPattern(pattern);
            }

            // Drawing polyline in the Google Map for the i-th route

        }
    }


}


