package com.poojanshah.json_fist_application;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.poojanshah.json_fist_application.MVP.interactor.Interactor_Impl2;
import com.poojanshah.json_fist_application.Realm.RealmHelper;
import com.poojanshah.json_fist_application.model.ParkingSpot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener,OnMapReadyCallback {

//    @Inject
//    Interactor_Impl interactor_;

    static final Integer LOCATION = 0x1;
    static final Integer CALL = 0x2;
    static final Integer WRITE_EXST = 0x3;
    static final Integer READ_EXST = 0x4;
    static final Integer CAMERA = 0x5;
    static final Integer ACCOUNTS = 0x6;
    static final Integer GPS_SETTINGS = 0x7;
    Interactor_Impl2 interactor_2;
    private GoogleMap mMap;

    List<Marker> markers;
    List<ParkingSpot> parkingSpots;

    Realm realm;
    RealmHelper realmHelper;

    public MapsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        interactor_2 = new Interactor_Impl2();
        markers = new ArrayList<>();
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
        mMap.setOnMarkerClickListener(this);
//        for(LatLng l:latLngs){
////            Log.i("onMapReady", l.longitude + " " + l.latitude);
//            LatLng sydney = new LatLng(l.latitude,l.longitude);
//            mMap.addMarker(new MarkerOptions().position(sydney));
//        }
//
//        interactor_2.getCakeList().observeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.newThread()).subscribe(this::onSuccess, this::OnError);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askForPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION);
            return;
        }
        mMap.setMyLocationEnabled(true);

        performCakeListDisplay();

//        // Set a listener for marker click.
//        mMap.setOnMarkerClickListener(this);


        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void onSuccess(List<ParkingSpot> parkingSpots) {
        displayParkingSpots(parkingSpots);
    }

    private void displayParkingSpots(List<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
        realmHelper.SaveData(parkingSpots);
        for(ParkingSpot parking:parkingSpots){
            realmHelper.SaveData(parking);
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            if(parking.getIsReserved()){
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            }else{
                bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            }
            LatLng latLng = new LatLng(parking.getLat(),parking.getLng());
            Log.i("Parking57",latLng.toString());
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(parking.getName())
                    .icon(bitmapDescriptor));
            marker.setTag(parking.getId());
            markers.add(marker);
        }
        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

    private void OnError(Throwable throwable) {
        Log.i("CPL Throwable", throwable.getMessage());
        Log.i("CPL Throwable", String.valueOf(throwable.getCause()));
        displayParkingSpots(realmHelper.getParkingList());
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

    public void performCakeListDisplay() {
        interactor_2.getCakeList(51.508862, -0.069227).observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread()).subscribe(this:: onSuccess, this:: OnError);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        // Retrieve the data from the marker.
        Long clickCount = (Long) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            ParkingSpot parking = parkingSpots.get(0);

            for(ParkingSpot parkingListItem : parkingSpots) {
                if (marker.getTag().equals(parkingListItem.getId())) {
                    parking = parkingListItem;
                }

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Marker ID: ");
                stringBuilder.append(parking.getLat() + " " + parking.getLng());
                stringBuilder.append(("getIsReserved: " + parking.getIsReserved()));
                stringBuilder.append(("getReservedUntil: " + parking.getReservedUntil()));

                long tag = (long) marker.getTag();
                Toast.makeText(this,
                        stringBuilder.toString() ,
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
}
