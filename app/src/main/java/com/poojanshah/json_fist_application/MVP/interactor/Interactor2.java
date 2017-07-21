package com.poojanshah.json_fist_application.MVP.interactor;

import com.poojanshah.json_fist_application.model.ParkingSpot;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by shahp on 20/07/2017.
 */

public interface Interactor2 {
        Observable<List<ParkingSpot>> getCakeList(double lat, double lng);

}