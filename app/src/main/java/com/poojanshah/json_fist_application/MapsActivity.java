package com.poojanshah.json_fist_application;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.poojanshah.json_fist_application.MVP.interactor.Interactor_Impl2;
import com.poojanshah.json_fist_application.Realm.RealmHelper;
import com.poojanshah.json_fist_application.model.ParkingSpot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_LONG;
import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

//    @Inject
//    Interactor_Impl interactor_;

    static final Integer LOCATION = 0x1;
    private static final int REQUEST_FINE_LOCATION = 0x1;
    private static final String TAG_NULL = "TAG_NULL";
    Interactor_Impl2 interactor_2;
    List<Marker> markers;
    List<ParkingSpot> parkingSpots;
    Realm realm;
    RealmHelper realmHelper;
    Location location;
    private GoogleMap mMap;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private boolean run = false;

    SharedPreferences sharedPref;

    public MapsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        startLocationUpdates();
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        interactor_2 = new Interactor_Impl2();
        markers = new ArrayList<>();

        sharedPref = MapsActivity.this.getSharedPreferences(
                getString(R.string.preference_file_key), this.MODE_PRIVATE);
    }

    // Trigger new location updates at interval
    protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    public void onLocationChanged(Location location) {
        // New location has now been determined
        this.location = location;
        if(run) {
            onMapReady(this.mMap);
            run = true;
        }
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }



    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissions();
            return false;
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_FINE_LOCATION);
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
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e("MapsActivityRaw", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivityRaw", "Can't find style.", e);
        }
//        mMap.setOnMarkerClickListener(this);
//        mMap.setOnInfoWindowClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askForPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION);
            return;
        }
        mMap.setMyLocationEnabled(true);

        performCakeListDisplay();
    }

    public void performCakeListDisplay() {
        double lat = 51.508862 ,lng = -0.069227;
//        double lat = 0 ,lng = -0;
        if(location != null){
            lat = location.getLatitude();
            lng = location.getLongitude();
            Log.i("Location", lat + " " + lng);
        }
        interactor_2.getParkingList(lat,lng).observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread()).subscribe(this:: onSuccess, this:: OnError);
    }

    private void onSuccess(List<ParkingSpot> parkingSpots) {
        for(ParkingSpot p : parkingSpots){
            realmHelper.SaveData(p);
        }

        List<ParkingSpot> passing = new ArrayList<>();
        for(int i = 0; i < parkingSpots.size() || i < 20; i++){
            passing.add(parkingSpots.get(i));
        }
        displayParkingSpots(passing);
    }

    private void onSuccessGet(ParkingSpot parkingSpot) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Marker ID: ");
        stringBuilder.append(parkingSpot.getLat() + " " + parkingSpot.getLng());
        stringBuilder.append(("getIsReserved: " + parkingSpot.getIsReserved()));
        stringBuilder.append(("getReservedUntil: " + parkingSpot.getReservedUntil()));
        Toast.makeText(this,
                stringBuilder.toString() ,
                Toast.LENGTH_SHORT).show();
    }

    private void OnError(Throwable throwable) {
        Log.i("CPL Throwable", throwable.getMessage());
        Log.i("CPL Throwable", String.valueOf(throwable.getCause()));
        List<ParkingSpot> parkingSpots = realmHelper.getParkingList();
        List<ParkingSpot> passing = new ArrayList<>();
        for(int i = 0; i < parkingSpots.size() || i < 20; i++){
            passing.add(parkingSpots.get(i));
        }
        displayParkingSpots(passing);
    }

    private void displayParkingSpots(List<ParkingSpot> parkingSpots) {
        int i = 0;
        ParkingSpot myParking;
        try {
            myParking = getParking();
            Date date = (Date) myParking.getReservedUntil();
            if (date.after(new Date())) {
                Marker markerMine = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(myParking.getLat(), myParking.getLng()))
                        .title(myParking.getName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                markerMine.setTag(myParking.getId());
                markers.add(markerMine);
            }
        } catch (NullPointerException exc){

        }
        for(ParkingSpot parking:parkingSpots) {
//            if(!parking.getIsReserved()) {
//                for (; i < 20; i++) {
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);

//            if(parking.getMine() != null
//                    &&parking.getMine()
//                    ){
//                try {
//                    Date date = (Date) parking.getReservedUntil();
//                        Log.i("date.after(new Date())", String.valueOf(date.after(new Date())));
//                        if (date.before(new Date())) {
////                            Log.i("displayParkingSpots314", date.toString());
//                            bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
//                        }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
////                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
//            }
                        if(getParking() != null && getParking().getId().equals(parking.getId())){
                            bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
                        }

                    else if (parking.getIsReserved()) {
                        bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                    }

                    LatLng latLng = new LatLng(parking.getLat(), parking.getLng());
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(parking.getName())
                            .icon(bitmapDescriptor));
                    marker.setTag(parking.getId());
                    markers.add(marker);

                    if (!parking.getIsReserved()) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    }

                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                        @BindView(R.id.tvName)
                        TextView textViewName;
                        @BindView(R.id.tvlatlng)
                        TextView textViewLatLng;
                        @BindView(R.id.tvcostperminute)
                        TextView textViewCost;
                        @BindView(R.id.tvmin)
                        TextView textViewMin;
                        @BindView(R.id.tvMax)
                        TextView textViewMax;
                        @BindView(R.id.tvuntil)
                        TextView textViewUntil;
                        @BindView(R.id.button3)
                        Button button;


                        @Override
                        public View getInfoWindow(Marker marker) {
                            return null;
                        }

                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public View getInfoContents(Marker marker) {


                            View v = getLayoutInflater().inflate(R.layout.infowindow, null);
                            ButterKnife.bind(this, v);

                            TextView textViewName = (TextView) v.findViewById(R.id.tvName);
                            TextView textViewLatLng = (TextView) v.findViewById(R.id.tvlatlng);
                            TextView textViewCost = (TextView) v.findViewById(R.id.tvcostperminute);
                            TextView textViewMin = (TextView) v.findViewById(R.id.tvmin);
                            TextView textViewMax = (TextView) v.findViewById(R.id.tvMax);
                            TextView textViewUntil = (TextView) v.findViewById(R.id.tvuntil);
                            Button button = (Button) v.findViewById(R.id.button3);


                            ParkingSpot parkingSpots = getParkingSpot(parking.getId());
                            if (parkingSpots == null) {
                                parkingSpots = parking;
                            }
                            textViewName.setText("Name: " + parkingSpots.getName());
                            textViewLatLng.setText("Location: " + parkingSpots.getLat() + " " + parkingSpots.getLng());
                            textViewCost.setText("Cost Per Minute: " + parkingSpots.getCostPerMinute());
                            textViewMin.setText("MinReserve Time: " + String.valueOf(parkingSpots.getMinReserveTimeMins()));
                            textViewMax.setText("MaxReserve Time: " + String.valueOf(parkingSpots.getMaxReserveTimeMins()));
                            textViewUntil.setText("Reserved Until: " + String.valueOf(parkingSpots.getReservedUntil()));
                            if (parking.getIsReserved()) {
                                button.setVisibility(View.INVISIBLE);
                            } else {
                                button.setVisibility(View.VISIBLE);
                                button.setText("Reserve");
                            }
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Log.i("CLick 298", "Click");
                                }
                            });
                            return v;
                        }
                    });
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            int a = (int) marker.getTag();
                            ParkingSpot parkingSpot = getParkingSpot(a);

                            Log.i("getIsReserved", String.valueOf(parking.getIsReserved()!= null && !parking.getIsReserved()));

                            if (parking.getIsReserved()!= null && !parking.getIsReserved()) {


                                AlertDialog.Builder builder1 = new AlertDialog.Builder(MapsActivity.this);
                                builder1.setMessage("Do you want to try and book ths Parking Spot?");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Yes",
                                        (dialog, id) -> {
                                            Log.i("CLick 308", "Click");
                                            int a1 = (int) marker.getTag();
                                            Log.i("CLick 369", String.valueOf(a1));
                                            interactor_2.postSinglePost(a1).enqueue(new Callback<ParkingSpot>() {
                                                @Override
                                                public void onResponse(Call<ParkingSpot> call, Response<ParkingSpot> response) {
                                                    try {
                                                        Log.i("onSuccessPost", String.valueOf(response.body().getReservedUntil()));
                                                        showMessage("You have been able to book this Parking Spot");
                                                        BitmapDescriptor bitmapDescriptorNew = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
                                                        marker.setIcon(bitmapDescriptorNew);
                                                        Log.i("message", response.message());
                                                        saveParking(response.body());
                                                    } catch (NullPointerException exc){
                                                        Log.i(TAG_NULL,exc.getMessage());
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ParkingSpot> call, Throwable t) {
                                                    Toast.makeText(getApplicationContext(), "You can't book this Parking Spot", LENGTH_LONG).show();
                                                    showMessage("You can't book this Parking Spot");
                                                    Log.i("CPL Throwable", t.getMessage());
                                                    Log.i("CPL Throwable", String.valueOf(t.getCause()));

                                                }
                                            });
//                                                interactor_2.postSinglePost(a).observeOn(AndroidSchedulers.mainThread())
//                                                        .observeOn(AndroidSchedulers.mainThread())
//                                                        .subscribeOn(Schedulers.newThread()).subscribe(this::onSuccessPost, this::OnErrorPost);
                                            dialog.cancel();
                                        });

                                builder1.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            } else {
                                Toast.makeText(getApplicationContext(), "You can't book this Parking Spot", LENGTH_LONG).show();
                                showMessage("You can't book this Parking Spot");
                            }
                        }
                    });

                }
//            }
//        }
    }

    private  void showMessage(String messagee){
        AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(messagee);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private ParkingSpot getParkingSpot(int a) {
        final ParkingSpot[] parkingSpots1 = new ParkingSpot[1];
        Call<ParkingSpot> call = interactor_2.getSingleResultW(a);
        call.enqueue(new Callback<ParkingSpot>() {
            @Override
            public void onResponse(Call<ParkingSpot> call, Response<ParkingSpot> response) {
                parkingSpots1[0] = response.body();
            }

            @Override
            public void onFailure(Call<ParkingSpot> call, Throwable t) {
                // Log error here since request failed
                Log.e("onFailure", t.toString());
            }
        });
        Log.e("onFailure", "");
        return parkingSpots1[0];
    }


    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MapsActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }



    private void getInfo(int id){
        interactor_2.getSpot(id).observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread()).subscribe(this:: onSuccessGet, this:: OnError);
    }

//    public void saveLocation(double lat, double lng, Object date ){
//        SharedPreferences sharedPref = MapsActivity.this.getPreferences(MapsActivity.this.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//
//        editor.putFloat(getString(R.string.lat), (float) lat);
//        editor.putFloat(getString(R.string.lng), (float) lng);
//        editor.putString(getString(R.string.date), (String) date);
//        editor.commit();
//    }

    public void saveLocation(ParkingSpot parking){
        SharedPreferences sharedPref = MapsActivity.this.getPreferences(MapsActivity.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putFloat(getString(R.string.lat),  Float.parseFloat(parking.getLat().toString()));
        editor.putFloat(getString(R.string.lng),  Float.parseFloat(parking.getLng().toString()));
        Date date = (Date) parking.getReservedUntil();
        editor.putString(getString(R.string.date), date.toString());
        editor.commit();
    }

    public void saveParking(ParkingSpot parking){
        SharedPreferences sharedPref = MapsActivity.this.getPreferences(MapsActivity.this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();
        String json = gson.toJson(parking);
        editor.putString("MyObject", json);
        editor.commit();
    }

    public ParkingSpot getParking() {
        SharedPreferences sharedPref = MapsActivity.this.getPreferences(MapsActivity.this.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("MyObject", "");
        ParkingSpot parkingSpot = gson.fromJson(json, ParkingSpot.class);
        return parkingSpot;
    }

    public LatLng getLocation(){
        SharedPreferences sharedPref = MapsActivity.this.getPreferences(MapsActivity.this.MODE_PRIVATE);
//        long highScore = sharedPref.getInt(getString(R.string.saved_high_score), defaultValue);
        Float lat = sharedPref.getFloat(getString(R.string.lat), 0F);
        Float lng = sharedPref.getFloat(getString(R.string.lng), 0F);
        String date = sharedPref.getString(getString(R.string.date), String.valueOf(new Date()));
        Log.i("getLocation",lat.toString());
        Log.i("getLocation",lng.toString());

        LatLng location = new LatLng(lat,lng);

        return location;
    }
}
