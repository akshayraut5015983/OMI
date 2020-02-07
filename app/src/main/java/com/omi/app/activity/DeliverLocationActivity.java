package com.omi.app.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;
import com.omi.app.R;
import com.omi.app.api.ApiServiceProvider;
import com.omi.app.listeners.RetrofitListener;
import com.omi.app.models.SendCurrentLocationResponse;
import com.omi.app.utils.Constants;
import com.omi.app.utils.ErrorObject;
import com.omi.app.utils.NetworkUtils;
import com.omi.app.utils.SharePreferenceUtility;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.omi.app.utils.Constants.AppConst.CITY;
import static com.omi.app.utils.Constants.AppConst.LAT;
import static com.omi.app.utils.Constants.AppConst.LONGI;

public class DeliverLocationActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, RetrofitListener {

    private Dialog mDialog;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 10 * 60000;  /* 10 secs */
    private long FASTEST_INTERVAL = 5 * 60000; /* 20 sec */
    private GoogleMap mGoogleMap;

    private LocationManager locationManager;
    private LatLng latLng;
    private boolean isPermission;
    PlacesClient placesClient;
    private FloatingActionButton mFabLocation;
    private String str_selectedAddress = "";
    private TextView txt_selectedAddress;
    private EditText txt_currentLocation;
    private ApiServiceProvider apiServiceProvider;
    String cityName="";
    String address;
    SupportMapFragment mapFragment;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_location);
        getSupportActionBar().hide();
        apiServiceProvider = ApiServiceProvider.getInstance(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        builder.setCancelable(true);
        mDialog = builder.create();
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnSubmitProfile = findViewById(R.id.btnSubmitProfile);
        Button btn_moreOption = findViewById(R.id.btnAddMoredetails);
        txt_selectedAddress = findViewById(R.id.txt_currentLocation);
        txt_currentLocation = findViewById(R.id.etSetLocation);
        mFabLocation = findViewById(R.id.fab_locate);

        LinearLayout line4 = findViewById(R.id.lllocation);
        checkLocationPermission();

        btn_moreOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DeliverLocationActivity.this, AddMoreAddress.class));
                gotofindaddress();
            }
        });

        mFabLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLocate();
            }
        });

        ((Button) findViewById(R.id.btnConfirmLocation)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.isNetworkConnected(DeliverLocationActivity.this)) {
                    if (cityName.isEmpty()) {
                        Toast.makeText(DeliverLocationActivity.this, "Please set your location", Toast.LENGTH_SHORT).show();
                    } else {
                        apiServiceProvider.getRequestSendLocation(cityName, DeliverLocationActivity.this);
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation == null) {
            startLocationUpdates();
        }
        if (mLocation != null) {
            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        // Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps

        latLng = new LatLng(location.getLatitude(), location.getLongitude());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //it was pre written
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    void onLocate() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

        if (null != location) {
            // map is an instance of GoogleMap
            if (mGoogleMap != null) {
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(14)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        //.tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            } else {
                // cannot retrieve user's location.
                // fallback to display some generic location
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if (latLng != null) {
            Address address = new Address(Locale.getDefault());
            address.setLocality("Jule Solapur, Solapur, Maharashtra, India");

            mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Current Location"));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f));
            mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
                    LatLng midLatLng = mGoogleMap.getCameraPosition().target;
                    Log.i("newLatLang", midLatLng.latitude + " " + midLatLng.longitude);

                    Geocoder geocoder = new Geocoder(DeliverLocationActivity.this);

                    double latitude = midLatLng.latitude;
                    double longitude = midLatLng.longitude;

                    SharePreferenceUtility.saveStringPreferences(DeliverLocationActivity.this, LAT, String.valueOf(latitude));
                    SharePreferenceUtility.saveStringPreferences(DeliverLocationActivity.this, LONGI, String.valueOf(longitude));


                    try {
                        List<Address> list = geocoder.getFromLocation(latitude, longitude, 1);
                        Log.e("newAddr", list.get(0).getAddressLine(0));
//                        Toast.makeText(DeliverLocationActivity.this, list.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                        txt_currentLocation.setText(list.get(0).getAddressLine(0));
                        txt_selectedAddress.setText(list.get(0).getAddressLine(0));

                        if (list != null && list.size() > 0) {
                            for (Address adr : list) {
                                if (adr.getLocality() != null && adr.getLocality().length() > 0) {
                                    cityName = list.get(0).getLocality();
                                } else {
                                    cityName = list.get(0).getAddressLine(0);
                                }
                            }
                        }
                        SharePreferenceUtility.saveStringPreferences(DeliverLocationActivity.this, CITY, cityName);

                        SharePreferenceUtility.saveStringPreferences(DeliverLocationActivity.this, Constants.AppConst.USER_LOCATION, list.get(0).getAddressLine(0));
//                        cityName = list.get(0).getLocality();
                        str_selectedAddress = list.get(0).getAddressLine(0);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Use fields to define the data types to return.
                    List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME,
                            Place.Field.ID,
                            Place.Field.ADDRESS);

                    // Use the builder to create a FindCurrentPlaceRequest.
                    FindCurrentPlaceRequest request = FindCurrentPlaceRequest.builder(placeFields).build();


                    // Call findCurrentPlace and handle the response (first check that the user has granted permission).
                    if (ContextCompat.checkSelfPermission(DeliverLocationActivity.this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        placesClient.findCurrentPlace(request).addOnSuccessListener((new OnSuccessListener<FindCurrentPlaceResponse>() {
                            @Override
                            public void onSuccess(FindCurrentPlaceResponse response) {
                                //txt_selectedAddress.setText(response.getPlaceLikelihoods().get(0).getPlace().getName() + "\n" + response.getPlaceLikelihoods().get(0).getPlace().getAddress());
                                for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                                    Log.i("location_data", String.format("Place '%s' has likelihood: %f",
                                            placeLikelihood.getPlace().getName(),
                                            placeLikelihood.getLikelihood()));
                                }
                            }
                        })).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                if (exception instanceof ApiException) {
                                    ApiException apiException = (ApiException) exception;
                                    Log.e("location_data", "Place not found: " + apiException.getStatusCode());
                                }
                            }
                        });
                    } else {
                        // A local method to request required permissions;
                        // See https://developer.android.com/training/permissions/requesting
                        //getLocationPermission();
                    }

                }
            });
        } else {
            Criteria criteria = new Criteria();

            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

            if (location != null) {
                Address address = new Address(Locale.getDefault());
                address.setLocality("Jule Solapur, Solapur, Maharashtra, India");
                onLocate();

                mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {
                        LatLng midLatLng = mGoogleMap.getCameraPosition().target;
                        mGoogleMap.addMarker(new MarkerOptions().position(midLatLng).title("Marker in Current Location"));
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(midLatLng, 14f));

                        Log.i("newLatLang", midLatLng.latitude + " " + midLatLng.longitude);

                        Geocoder geocoder = new Geocoder(DeliverLocationActivity.this);

                        double latitude = midLatLng.latitude;
                        double longitude = midLatLng.longitude;

                        SharePreferenceUtility.saveStringPreferences(DeliverLocationActivity.this, LAT, String.valueOf(latitude));
                        SharePreferenceUtility.saveStringPreferences(DeliverLocationActivity.this, LONGI, String.valueOf(longitude));


                        try {
                            List<Address> list = geocoder.getFromLocation(latitude, longitude, 1);
                            Log.e("newAddr", list.get(0).getAddressLine(0));
//                        Toast.makeText(DeliverLocationActivity.this, list.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                            txt_currentLocation.setText(list.get(0).getAddressLine(0));
                            txt_selectedAddress.setText(list.get(0).getAddressLine(0));

                            if (list != null && list.size() > 0) {
                                for (Address adr : list) {
                                    if (adr.getLocality() != null && adr.getLocality().length() > 0) {
                                        cityName = list.get(0).getLocality();
                                    } else {
                                        cityName = list.get(0).getAddressLine(0);
                                    }
                                }
                            }
                            SharePreferenceUtility.saveStringPreferences(DeliverLocationActivity.this, CITY, cityName);

                            SharePreferenceUtility.saveStringPreferences(DeliverLocationActivity.this, Constants.AppConst.USER_LOCATION, list.get(0).getAddressLine(0));
//                        cityName = list.get(0).getLocality();
                            str_selectedAddress = list.get(0).getAddressLine(0);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // Use fields to define the data types to return.
                        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME,
                                Place.Field.ID,
                                Place.Field.ADDRESS);

                        // Use the builder to create a FindCurrentPlaceRequest.
                        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.builder(placeFields).build();


                        // Call findCurrentPlace and handle the response (first check that the user has granted permission).
                        if (ContextCompat.checkSelfPermission(DeliverLocationActivity.this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            placesClient.findCurrentPlace(request).addOnSuccessListener((new OnSuccessListener<FindCurrentPlaceResponse>() {
                                @Override
                                public void onSuccess(FindCurrentPlaceResponse response) {
                                    //txt_selectedAddress.setText(response.getPlaceLikelihoods().get(0).getPlace().getName() + "\n" + response.getPlaceLikelihoods().get(0).getPlace().getAddress());
                                    for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                                        Log.i("location_data", String.format("Place '%s' has likelihood: %f",
                                                placeLikelihood.getPlace().getName(),
                                                placeLikelihood.getLikelihood()));
                                    }
                                }
                            })).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    if (exception instanceof ApiException) {
                                        ApiException apiException = (ApiException) exception;
                                        Log.e("location_data", "Place not found: " + apiException.getStatusCode());
                                    }
                                }
                            });
                        } else {
                            // A local method to request required permissions;
                            // See https://developer.android.com/training/permissions/requesting
                            //getLocationPermission();
                        }

                    }
                });
            }

        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    public synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getBaseContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onPause() {
        super.onPause();
        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (requestSinglePermission()) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            //it was pre written
            mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            buildGoogleApiClient();

            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            checkLocation(); //check whether location service is enable or not in your  phone

            // Initialize Places.
            Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));

            // Create a new Places client instance.
            placesClient = Places.createClient(this);

        }
        if (this.mGoogleApiClient != null) {
            this.mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean requestSinglePermission() {

        Dexter.withActivity(this)
                .withPermission(ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        //Single Permission is granted
                        isPermission = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            isPermission = false;
                            checkLocationPermission();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                        finish();
                    }

                }).check();

        return isPermission;
    }

    @Override
    public void onResponseSuccess(Object responseBody, int apiFlag) {
        if (responseBody instanceof SendCurrentLocationResponse) {
            SendCurrentLocationResponse sendCurrentLocationResponse = (SendCurrentLocationResponse) responseBody;
            if (sendCurrentLocationResponse.success) {
                startActivity(new Intent(DeliverLocationActivity.this, HomeActivity.class));
                finish();
            }
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(DeliverLocationActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:

                        if (requestSinglePermission()) {
                            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                            //it was pre written
                            mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.map);
                            mapFragment.getMapAsync(this);

                            buildGoogleApiClient();

                            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                            checkLocation(); //check whether location service is enable or not in your  phone

                            // Initialize Places.
                            Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));

                            // Create a new Places client instance.
                            placesClient = Places.createClient(this);

                        }


                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    checkLocationPermission();

                }
                return;
            }

        }
    }

    int AUTOCOMPLETE_REQUEST_CODE = 1;
    List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS);


    private void gotofindaddress() {
        try {
            if (NetworkUtils.isNetworkConnected(DeliverLocationActivity.this)) {

                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Please check internet connection", Snackbar.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i("Location", "Place: " + place.getName() + ", " + place.getId());
                setnewaddress(place);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("Location", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    String currentlocation;


    private void setnewaddress(Place place) {
        try {
            if (place != null) {
                currentlocation = place.getName() + place.getAddress();
                latLng = place.getLatLng();

                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)      // Sets the center of the map to location user
                        .zoom(14)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        //.tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                txt_currentLocation.setText(currentlocation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
