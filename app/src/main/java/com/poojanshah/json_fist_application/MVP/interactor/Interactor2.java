package com.poojanshah.json_fist_application.MVP.interactor;

import com.poojanshah.json_fist_application.model.ParkingSpot;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by shahp on 20/07/2017.
 */

public interface Interactor2 {
        Observable<List<ParkingSpot>> getCakeList(double lat, double lng);
        Observable<List<ParkingSpot>> getCakeList();
        Observable<ParkingSpot> getSpot(int id);
        Call<ParkingSpot> getSingleResultW(int id);
        Call<ParkingSpot> postSinglePost(int id);
}