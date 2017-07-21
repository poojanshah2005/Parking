package com.poojanshah.json_fist_application.MVP;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.poojanshah.json_fist_application.Injection.components.APIComponent;
import com.poojanshah.json_fist_application.MVP.interactor.Interactor_Impl;
import com.poojanshah.json_fist_application.MapsActivity;
import com.poojanshah.json_fist_application.model.ParkingSpot;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shahp on 14/07/2017.
 */

public class CakeListPresenterImpl  extends  BasePresenter<ICakeListView> implements  ICakeListPresenter{
    @Inject
    Interactor_Impl interactor_;

    public void setContext(Context context) {
        this.context = context;
    }

    Context context;

    @Inject
    public CakeListPresenterImpl(Interactor_Impl interactor_) {
        this.interactor_ = interactor_;
    }

    @Override
    public void attachView(ICakeListView MVPView) {
            super.attachView(MVPView);
    }

    @Override
    public void detachView() {
    super.detachView();
    }

    @Override
    public void performCakeListDisplay(){
        interactor_.getCakeList().observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread()).subscribe(this:: onSuccess, this:: OnError);
    }

    private void onSuccess(List<ParkingSpot> parkingSpots) {
        for(ParkingSpot parking:parkingSpots){
            Log.i("Parking57",parking.getName());
        }
    }

    private void OnError(Throwable throwable) {
        Log.i("CPL Throwable", throwable.getMessage());
        Log.i("CPL Throwable", String.valueOf(throwable.getCause()));
    }

//    private void onSuccess(ParkingSpot parkingSpot) {
//
////        for(Restaurant restaurant: justEat.getRestaurants()){
////            Log.i("restaurant.getName()", restaurant.getName());
////        }
////        openMaps();
//    }

    public void openMaps(){
        Intent intent = new Intent(context, MapsActivity.class);
//        ArrayList<LatLng> latLngs = new ArrayList<>();
//        for(Restaurant restaurant: justEat.getRestaurants()){
//            LatLng location = new LatLng(restaurant.getLatitude(), restaurant.getLongitude());
//            latLngs.add(location);
//        }
//        intent.putExtra("justEat",latLngs);
        context.startActivity(intent);
    }

    public void injectForData(APIComponent apiComponent) {
        interactor_.initiateInjectionGraph(apiComponent);
    }
}
