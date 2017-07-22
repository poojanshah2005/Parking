package com.poojanshah.json_fist_application.service;

import com.poojanshah.json_fist_application.Constants;
import com.poojanshah.json_fist_application.model.ParkingSpot;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shahp on 12/07/2017.
 */

public interface RequestInterface {

    @GET(Constants.DATA_TO_FETCH)
    Observable<List<ParkingSpot>> getResult(@Query("lng") Double lng, @Query("lat") Double lat);

    @GET("{id}")
    Observable<ParkingSpot> getSingleResult(@Path("id") int id);




}
