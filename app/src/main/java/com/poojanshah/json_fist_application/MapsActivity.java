package com.poojanshah.json_fist_application;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.bumptech.glide.load.HttpException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
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
import com.poojanshah.json_fist_application.MVP.interactor.Interactor_Impl2;
import com.poojanshah.json_fist_application.Realm.RealmHelper;
import com.poojanshah.json_fist_application.model.ParkingSpot;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.observers.DisposableLambdaObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_LONG;
import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;
import static java.lang.Math.toIntExact;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

//    @Inject
//    Interactor_Impl interactor_;

    static final Integer LOCATION = 0x1;
    private static final int REQUEST_FINE_LOCATION = 0x1;
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
        displayParkingSpots(realmHelper.getParkingListMine());
        double lat = 51.508862 ,lng = -0.069227;
//        double lat = 0 ,lng = -0;
        if(location != null){
            lat = location.getLatitude();
            lng = location.getLongitude();
            Log.i("Location", lat + " " + lng);
        }
        interactor_2.getCakeList(lat, lng).observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread()).subscribe(this:: onSuccess, this:: OnError);
    }

    private void onSuccess(List<ParkingSpot> parkingSpots) {
        for(ParkingSpot p : parkingSpots){
            realmHelper.SaveData(p);
        }
        displayParkingSpots(parkingSpots);
    }

    private void onSuccess(ParkingSpot parkingSpot) {
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
        displayParkingSpots(realmHelper.getParkingList());
    }

    private void displayParkingSpots(List<ParkingSpot> parkingSpots) {
        for(ParkingSpot parking:parkingSpots){
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
//            try {
//                if(parking.getReservedUntil()!= null){Date date = (Date) parking.getReservedUntil();
//                    if (date.after(new Date())) {
////                        Log.i("displayParkingSpots304", date.toString());
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            if(parking.getMine() != null
                    &&parking.getMine()
                    ){
                try {
                    Date date = (Date) parking.getReservedUntil();
                        Log.i("date.after(new Date())", String.valueOf(date.after(new Date())));
                        if (date.before(new Date())) {
//                            Log.i("displayParkingSpots314", date.toString());
                            bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
                        }

                } catch (Exception e) {
                    e.printStackTrace();
                }
//                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
            }else if(parking.getIsReserved()){
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            }

            LatLng latLng = new LatLng(parking.getLat(),parking.getLng());
//            Log.i("Parking57",latLng.toString() + " "  + parking.getIsReserved());
//            Log.i("Parking57", String.valueOf(parking.getIsReserved()));
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(parking.getName())
                    .icon(bitmapDescriptor));
            marker.setTag(parking.getId());
            markers.add(marker);

            if(!parking.getIsReserved()) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }

            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @BindView(R.id.tvName) TextView textViewName;
                @BindView(R.id.tvlatlng) TextView textViewLatLng;
                @BindView(R.id.tvcostperminute) TextView textViewCost;
                @BindView(R.id.tvmin)  TextView textViewMin;
                @BindView(R.id.tvMax) TextView textViewMax;
                @BindView(R.id.tvuntil) TextView textViewUntil;
                @BindView(R.id.button3) Button button;



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
                    if(parkingSpots == null){
                        parkingSpots = parking;
                    }
                    textViewName.setText("Name: " +parkingSpots.getName());
                    textViewLatLng.setText("Location: " +parkingSpots.getLat() + " " + parkingSpots.getLng());
                    textViewCost.setText("Cost Per Minute: " +parkingSpots.getCostPerMinute());
                    textViewMin.setText("MinReserve Time: " +String.valueOf(parkingSpots.getMinReserveTimeMins()));
                    textViewMax.setText("MaxReserve Time: " +String.valueOf(parkingSpots.getMaxReserveTimeMins()));
                    textViewUntil.setText("Reserved Until: " +String.valueOf(parkingSpots.getReservedUntil()));
                    if(parking.getIsReserved()){
                        button.setVisibility(View.INVISIBLE);
                    } else{
                        button.setVisibility(View.VISIBLE);
                        button.setText("Reserve");
                    }
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.i("CLick 298","Click");
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

//                    if (!parkingSpot.getIsReserved()) {


                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MapsActivity.this);
                        builder1.setMessage("Do you want to try and book ths Parking Spot?");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Log.i("CLick 308", "Click");
                                        int a = (int) marker.getTag();
                                        Log.i("CLick 369", String.valueOf(a));
                                        interactor_2.getSinglePost(a).observeOn(AndroidSchedulers.mainThread())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeOn(Schedulers.newThread()).subscribe(this::onSuccessPost, this::OnErrorPost);
                                        dialog.cancel();
                                    }

                                    private void OnErrorPost(Throwable throwable) {
                                        Log.i("CPL Throwable", throwable.getMessage());
                                        Log.i("CPL Throwable", String.valueOf(throwable.getCause()));
                                        Toast.makeText(getApplicationContext(), "You can't book this Parking Spot", LENGTH_LONG).show();
                                        showMessage("You can't book this Parking Spot");
                                    }

                                    private void onSuccessPost(ParkingSpot parkingSpot) {
                                        Log.i("onSuccessPost", String.valueOf(parkingSpot.getReservedUntil()));
                                        showMessage("You have been able to book this Parking Spot");
                                        BitmapDescriptor bitmapDescriptorNew = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
                                        marker.setIcon(bitmapDescriptorNew);
                                        parkingSpot.setMine(true);
                                        realmHelper.SaveData(parkingSpot);

                                    }
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
//                    }
                }
            });
        }
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
                .subscribeOn(Schedulers.newThread()).subscribe(this:: onSuccess, this:: OnError);
    }
}
