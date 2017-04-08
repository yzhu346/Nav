package gatech.nav;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
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


public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = MapsActivity.class.getSimpleName();

    private final LatLng mDefaultLocation = new LatLng(33.777301, -84.397908);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    private Marker mMarker;

    private Location mLastLocation;
    private LatLng mMyLocation;
    private CameraPosition mCameraPosition;
    private View mapView;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";





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

   /* @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastLocation);
            super.onSaveInstanceState(outState);
        }
    }*/

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

    public void onClickButton(View view){
        new buildingSearch().execute();
    }

    private class buildingSearch extends AsyncTask<Void, Void, JsonArray> {
        private String stringUrl = "http://m.gatech.edu/api/gtplaces/buildings/";
        EditText editText = (EditText) findViewById(R.id.editText);
        private String key = editText.getText().toString();

        @Override
        protected JsonArray doInBackground(Void...params) {
            try{
                URL url = new URL(stringUrl + key);
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
            if (result != null) {
                JsonObject firstBuilding = result.get(0).getAsJsonObject();
                LatLng buildingLatLng = new LatLng(Double.parseDouble(firstBuilding.get("latitude").toString().substring(1,firstBuilding.get("latitude").toString().length()-1)),Double.parseDouble(firstBuilding.get("longitude").toString().substring(1,firstBuilding.get("longitude").toString().length()-1)));
                String name = firstBuilding.get("name").toString().substring(1,firstBuilding.get("name").toString().length()-1);
                String address = firstBuilding.get("address").toString().substring(1,firstBuilding.get("address").toString().length()-1);
                if (mMarker != null) {
                    mMarker.remove();
                }
                mMarker = mMap.addMarker(new MarkerOptions().position(buildingLatLng).title(name).snippet(address));
                View button = findViewById(R.id.gobutton);
                button.setVisibility(View.VISIBLE);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(buildingLatLng, 16));
            }
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
        System.out.println("system ready");

        updateLocationUI();
        getMyLocation();

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
            CompRlp.setMargins(0,180,180,0);
        }

        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapClickListener(this);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMyLocation, DEFAULT_ZOOM));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



        /*EditText editText = (EditText) findViewById(R.id.editText) ;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                EditText editText = (EditText) findViewById(R.id.editText);
                String key = editText.getText().toString();
                try {

                    if(buildingArray.size() > 0){
                        JsonObject firstBuilding = buildingArray.get(0).getAsJsonObject();
                        LatLng buildingLatLng = new LatLng(Double.parseDouble(firstBuilding.get("latitude").toString().substring(1,firstBuilding.get("latitude").toString().length()-1)),Double.parseDouble(firstBuilding.get("longitude").toString().substring(1,firstBuilding.get("longitude").toString().length()-1)));
                        String name = firstBuilding.get("name").toString().substring(1,firstBuilding.get("name").toString().length()-1);
                        String address = firstBuilding.get("address").toString().substring(1,firstBuilding.get("address").toString().length()-1);
                        if (mMarker != null) {
                            mMarker.remove();
                        }
                        mMarker = mMap.addMarker(new MarkerOptions().position(buildingLatLng).title(name).snippet(address));
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });*/


        Express express = new Express();
        express.draw_express(mMap);
        /*ToggleButton express_toggle = (ToggleButton) findViewById(R.id.Exp);
        express_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Express express = new Express();
                    express.draw_express(mMap);
                }
            }
        });*/


    }

    public void getMyLocation() {
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