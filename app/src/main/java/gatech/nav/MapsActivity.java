package gatech.nav;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.maps.android.SphericalUtil;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Calendar;

import static com.google.maps.android.SphericalUtil.computeHeading;
import static java.lang.Math.abs;

import android.graphics.BitmapFactory;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = MapsActivity.class.getSimpleName();


    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    private Marker mMarker;
    private String mSearchKey;
    private ArrayList<SearchSuggestion> mSearchSuggestion = new ArrayList<>();
    private Route mRoute = new Route();
    private LinkedList<Polyline> mAllWalkingRoute = new LinkedList<Polyline>();

    private Location mLastLocation;
    private LatLng mMyLocation;
    private View mapView;
    private FloatingSearchView mSearchView;
    private JsonArray mBus;
    private JsonArray resultArray;
    Circle circle = null;
    Polygon polygon = null;
    Marker marker;
    List<Circle> allCircles = new ArrayList<Circle>();
    int threadtickscount = 0;
    private List<Marker> markerList = new ArrayList<Marker>();

    Handler handler = new Handler(Looper.getMainLooper());
    // Keys for storing activity state.
    private static final String DIRECTION_API_KEY = "AIzaSyCfH6jsTdZgxFXMyBkKcsBlBywGxq7UnnQ";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Retrieve location and camera position from saved instance state.
        /*if (savedInstanceState != null) {
            mLastLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }*/

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }




    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    /**
     * Builds the map when the Google Play services client is successfully connected.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Build the map.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);


    }

    /**
     * Handles failure to connect to the Google Play services client.
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        // Refer to the reference doc for ConnectionResult to see what error codes might
        // be returned in onConnectionFailed.
        Log.d(TAG, "Play services connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    /**
     * Handles suspension of the connection to the Google Play services client.
     */
    @Override
    public void onConnectionSuspended(int cause) {
        Log.d(TAG, "Play services connection suspended");
    }

    /**
     * Add a marker on map after long click, remove any previously exist marker
     * @param point
     */
    @Override
    public void onMapLongClick(LatLng point) {
        if (mMarker != null) {
            mMarker.remove();
        }
        mMarker = mMap.addMarker(new MarkerOptions().position(point).title(point.toString()));
        View button = findViewById(R.id.gobutton);
        button.setVisibility(View.VISIBLE);
    }


    @Override
    public void onMapClick(LatLng point){
        if(mMarker != null){
            mMarker.remove();
        }
        if(mAllWalkingRoute.size()>0) {
            for (int i = 0; i < mAllWalkingRoute.size(); i++){
                mAllWalkingRoute.get(i).remove();
            }
        }
        mAllWalkingRoute.clear();
        mRoute.draw(mMap);
        View button = findViewById(R.id.gobutton);
        button.setVisibility(View.INVISIBLE);
    }


    private class buildingSearch extends AsyncTask<Void, Void, JsonArray> {
        private String stringUrl = "http://m.gatech.edu/api/gtplaces/buildings/";


        @Override
        protected JsonArray doInBackground(Void...params) {
            try{
                URL url = new URL(stringUrl + mSearchKey);
                URLConnection uc = url.openConnection();
                uc.setRequestProperty("Accept", "text/html");
                uc.connect();
                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(new InputStreamReader((InputStream) uc.getContent()));
                JsonArray jsonArray = root.getAsJsonArray();
                if(jsonArray.size() > 0) {
                    return jsonArray;
                }
                else
                    return null;
            } catch (MalformedURLException e){
                Log.e("ERROR",e.getMessage(),e);
                return null;
            } catch (IOException e){
                Log.e("ERROR",e.getMessage(),e);
                return null;
            }



        }

        @Override
        protected void onPostExecute(JsonArray result) {
            mSearchSuggestion.clear();
            int s;

            if (result != null) {
                if(result.size()>5)
                    s = 5;
                else
                    s = result.size();
                for (int i = 0; i < s; i++) {
                    JsonObject selectBuilding = result.get(i).getAsJsonObject();
                    BuildingSuggestion buildingSuggestion = new BuildingSuggestion();
                    buildingSuggestion.set(selectBuilding);
                    mSearchSuggestion.add(buildingSuggestion);
                }
            }
            mSearchView.swapSuggestions(mSearchSuggestion);
        }

        @Override
        protected void onPreExecute(){

        }

    }


    private class busLive extends AsyncTask<Void, Void, JsonArray> {
        private String stringUrl = "http://m.gatech.edu/api/buses/position";

        @Override
        protected void onPreExecute(){
        }

        @Override
        protected JsonArray doInBackground(Void...params) {
            try{
                resultArray = new JsonArray();
                Calendar c = Calendar.getInstance();
                JsonObject jObj;
                URL url = new URL(stringUrl);
                URLConnection uc = url.openConnection();
                uc.setRequestProperty("Accept", "text/html");
                uc.connect();
                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(new InputStreamReader((InputStream) uc.getContent()));
                JsonArray jsonArray = root.getAsJsonArray();
                if(jsonArray.size() > 0) {
                    int s = jsonArray.size();
                        for (int i = 0; i < s; i++) {
                         jObj = jsonArray.get(i).getAsJsonObject();
                        int len = jObj.get("ts").toString().length();
                            int timeNow = c.get(Calendar.SECOND)+c.get(Calendar.MINUTE)*60+c.get(Calendar.HOUR_OF_DAY)*3600;
                            int timeBus = Integer.parseInt(jObj.get("ts").toString().substring(7,9))
                                     +Integer.parseInt(jObj.get("ts").toString().substring(4,6))*60
                                     +Integer.parseInt(jObj.get("ts").toString().substring(1,3))*3600;
                        int timeDiff = abs(timeNow - timeBus);
                        if (timeDiff <100)
                        {resultArray.add(jObj);}
                    }
                    return resultArray;
                }
                else
                    return null;
            } catch (MalformedURLException e){
                Log.e("ERROR",e.getMessage(),e);
                return null;
            } catch (IOException e){
                Log.e("ERROR",e.getMessage(),e);
                return null;
            }

        }

        @Override
        protected void onPostExecute(JsonArray result) {
            JsonObject jObj;
            mBus = new JsonArray();
            int s;

            if (result != null) {
                s = result.size();
                for (int i = 0; i < s; i++) {
                    JsonObject bus = result.get(i).getAsJsonObject();
                    mBus.add(bus);
                }
            }
        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        Log.d(TAG, "onSearchTextChanged()");


        updateLocationUI();
        getMyLocation();
        initFindMyLocation();

        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapClickListener(this);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mMyLocation, DEFAULT_ZOOM));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        setSearchBar();
        setGoButton();

        mRoute.init();
        mRoute.draw(mMap);

        /*route.drawBetweenStop("trolley","marta_a","recctr",mMap);*/

        /*markerPoints = new ArrayList<LatLng>();*/
        /*String url = getDirectionsUrl(new LatLng(33.7751,-84.40259),new LatLng(33.77335,-84.39917));
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);*/

      /*  new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                createCircles();

            }
        });*/


        Thread t = new Thread() {

            @Override
            public void run() {
                try{
                    while(true) {
                        if (threadtickscount <3)
                            { Thread.sleep(1);}
                        else
                        {Thread.sleep(2000);}
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new busLive().execute();

                                    createCircles();

                                }
                            });
                        threadtickscount++;
                    }
                }catch (InterruptedException e) {}

            }
        };
        t.start();

  /*      Runnable refresh;
        refresh = new Runnable() {

            public void run() {

                    createCircles();
            }
        };
        handler.postDelayed(refresh,1000);*/

    }

    private void createCircles() {


        if (markerList != null){
            for (Marker marker : markerList){
                marker.remove();}
        }

        if (mBus != null) {
            int s;
            s = mBus.size();

           /* for (int j = 0; j < s; j++)
            {
                if (marker != null){
                    marker.remove();
                }
            }*/

            for (int i = 0; i < s; i++) {
                String colour = "";
                boolean realbus = false;

                JsonObject bus = mBus.get(i).getAsJsonObject();
                Double a = Double.parseDouble(bus.get("lat").toString().substring(0, bus.get("lat").toString().length()));
                Double b = Double.parseDouble(bus.get("lng").toString().substring(0, bus.get("lng").toString().length()));
                Double c = Double.parseDouble(bus.get("plat").toString().substring(0, bus.get("plat").toString().length()));
                Double d = Double.parseDouble(bus.get("plng").toString().substring(0, bus.get("plng").toString().length()));

                //Logic here is that we draw imaginary line, perpendicular to original from-to [(a,b) to (c,d)]
                //and offset ends of that imaginary perpendicular line by certain amount, which are coordinate alpha and beta
                Double bearing = computeHeading(new LatLng(a,b),new LatLng(c,d));

         /*       Double gamma_x = c + Math.cos(bearing)/10000; // gamma is coordinate of to-location, scaled.
                Double gamma_y = d + Math.sin(bearing)/10000;
                Double alpha_x = a + Math.cos(bearing+90)/10000; //alpha is one side of bottom of triangular marker
                Double alpha_y = b + Math.sin(bearing+90)/10000;
                Double beta_x = a + Math.cos(bearing+270)/10000; // beta is the other side of triangular marker
                Double beta_y = b + Math.sin(bearing+270)/10000;
*/
                if (bus.get("route").toString().substring(1, bus.get("route").toString().length() - 1).equals("red")) {
                    colour = "red";
                    realbus = true;
                } else if (bus.get("route").toString().substring(1, bus.get("route").toString().length() - 1).equals("blue")) {
                    colour = "blue";
                    realbus = true;
                } else if (bus.get("route").toString().substring(1, bus.get("route").toString().length() - 1).equals("trolley")) {
                    colour = "yellow";
                    realbus = true;
                } else if (bus.get("route").toString().substring(1, bus.get("route").toString().length() - 1).equals("green")) {
                    colour = "green";
                    realbus = true;
                } else if (bus.get("route").toString().substring(1, bus.get("route").toString().length() - 1).equals("express")) {
                    colour = "black";
                    realbus = true;
                }

                if (realbus){
                    if (colour == "red") {
                        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_red);
                        Bitmap bmm = Bitmap.createScaledBitmap(bm, 50, 50, true);
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(a, b))
                                .anchor(0.5f, 0.5f)
                                .rotation(Float.valueOf(String.valueOf(bearing)))
                                .icon(BitmapDescriptorFactory.fromBitmap(bmm)));
                    }
                    else if (colour == "blue"){
                        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_blue);
                        Bitmap bmm = Bitmap.createScaledBitmap(bm, 50, 50, true);
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(a, b))
                                .anchor(0.5f, 0.5f)
                                .rotation(Float.valueOf(String.valueOf(bearing)))
                                .icon(BitmapDescriptorFactory.fromBitmap(bmm)));
                    }
                    else if (colour == "green"){
                        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_green);
                        Bitmap bmm = Bitmap.createScaledBitmap(bm, 50, 50, true);
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(a, b))
                                .anchor(0.5f, 0.5f)
                                .rotation(Float.valueOf(String.valueOf(bearing)))
                                .icon(BitmapDescriptorFactory.fromBitmap(bmm)));
                    }
                    else if (colour == "yellow"){
                        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_yellow);
                        Bitmap bmm = Bitmap.createScaledBitmap(bm, 50, 50, true);
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(a, b))
                                .anchor(0.5f, 0.5f)
                                .rotation(Float.valueOf(String.valueOf(bearing)))
                                .icon(BitmapDescriptorFactory.fromBitmap(bmm)));
                    }
                    else if (colour == "black"){
                        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_black);
                        Bitmap bmm = Bitmap.createScaledBitmap(bm, 50, 50, true);
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(a, b))
                                .anchor(0.5f, 0.5f)
                                .rotation(Float.valueOf(String.valueOf(bearing)))
                                .icon(BitmapDescriptorFactory.fromBitmap(bmm)));
                    }
                }
                markerList.add(marker);
            }
        }
    }


    private void setGoButton (){
        Button button = (Button) findViewById(R.id.gobutton);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String start = "ferstdr";
                String dest = "techsqua_ib";
                String route_id = "trolley";
                /*List<PatternItem> pattern = Arrays.<PatternItem>asList(
                        new Dash(20), new Gap(20));*/

                mRoute.drawBetweenStop(route_id,start,dest,mMap);
                if(mAllWalkingRoute.size()>0) {
                    for (int i = 0; i < mAllWalkingRoute.size(); i++){
                        mAllWalkingRoute.get(i).remove();
                    }
                }
                mAllWalkingRoute.clear();
                drawWalkRoute(mMyLocation,mRoute.getStopLatLng(start,route_id));
                drawWalkRoute(mRoute.getStopLatLng(dest,route_id),mMarker.getPosition());



            }
        });
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

        /*// Waypoints
        String waypoints = "";
        for(int i=2;i<markerPoints.size();i++){
            LatLng point  = (LatLng) markerPoints.get(i);
            if(i==2)
                waypoints = "waypoints=";
            waypoints += point.latitude + "," + point.longitude + "|";
        }*/

        /*LatLng point = new LatLng(33.782180, -84.391928);
        String waypoints = "waypoints=";
        waypoints += point.latitude + "," + point.longitude + "|";*/
        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor + "&" + mode/*+"&"+waypoints*/;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
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
    private class DownloadTask extends AsyncTask<String, Void, String>{

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

    /** A class to parse the Google Places in JSON format */
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
               /* mWalkingRoute = mMap.addPolyline(mPolylineOptions);
                mWalkingRoute.setPattern(pattern);*//*
                //}*/
                mAllWalkingRoute.add(mMap.addPolyline(lineOptions));
                mAllWalkingRoute.get(mAllWalkingRoute.size()-1).setPattern(pattern);
            }

            // Drawing polyline in the Google Map for the i-th route

        }
    }






    private void setSearchBar(){
        mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {
                    mSearchView.showProgress();
                    mSearchKey = newQuery;
                    new buildingSearch().execute();
                    mSearchView.hideProgress();
                }
                //get suggestions based on newQuery


                //pass them on to the search view
               /* mSearchView.swapSuggestions(mSearchSuggestion);*/

            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                BuildingSuggestion buildingSuggestion = (BuildingSuggestion) searchSuggestion;
                if(mMarker != null)
                    mMarker.remove();
                mMarker = mMap.addMarker(new MarkerOptions().position(buildingSuggestion.getLatLng()).title(buildingSuggestion.getBody()).snippet(buildingSuggestion.getAddress()));
                View button = findViewById(R.id.gobutton);
                button.setVisibility(View.VISIBLE);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(buildingSuggestion.getLatLng(), 16));
            }

            @Override
            public void onSearchAction(String currentQuery) {

            }
        });

    }

    private void initFindMyLocation(){
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            View compassButton = ((ViewGroup) mapView.findViewById(Integer.parseInt("1")).getParent()).getChildAt(4);
            RelativeLayout.LayoutParams CompRlp = (RelativeLayout.LayoutParams) compassButton.getLayoutParams();
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);

            CompRlp.addRule(RelativeLayout.ALIGN_PARENT_TOP,0);
            CompRlp.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);
            CompRlp.setMargins(0,240,180,0);
        }
    }

    private void getMyLocation() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        if (mLocationPermissionGranted) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        if (mLastLocation != null) {
            mMyLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
    }


    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }


    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }

        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mLastLocation = null;
        }
    }


}