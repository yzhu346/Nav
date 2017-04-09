package gatech.nav;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


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
    private List<SearchSuggestion> mSearchSuggestion = new ArrayList<>();

    private Location mLastLocation;
    private LatLng mMyLocation;
    private View mapView;
    private FloatingSearchView mSearchView;

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
                if(jsonArray.size() > 0)
                    return jsonArray;
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

        mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        setSearchBar();

        Express express = new Express();
        express.draw_express(mMap);



    }


    private void setSearchBar(){
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