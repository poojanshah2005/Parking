package com.poojanshah.json_fist_application.MVP.interactor;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.poojanshah.json_fist_application.Constants;
import com.poojanshah.json_fist_application.model.ParkingSpot;
import com.poojanshah.json_fist_application.service.RequestInterface;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shahp on 14/07/2017.
 */

public class Interactor_Impl2 implements Interactor2 {
    static Retrofit retrofit;
    static OkHttpClient okHttpClient;
    RequestInterface requestInterface;

    public Interactor_Impl2() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        File outputDir = context.getCacheDir();
//        Cache cache = new Cache(outputDir, 50000);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        requestInterface = retrofit.create(RequestInterface.class);
//        return retrofit.create(RequestInterface.class);
    }

    @Override
    public Observable<List<ParkingSpot>> getParkingList(double lat, double lng) {
        return requestInterface.getResult(lat, lng);
    }

    @Override
    public Observable<List<ParkingSpot>> getParkingList() {
        return requestInterface.getResult();
    }

    @Override
    public Observable<ParkingSpot> getSpot(int id) {
        return requestInterface.getSingleResult(id);
    }

    @Override
    public Call<ParkingSpot> getSingleResultW(int id) {
        return requestInterface.getSingleResultW(id);
    }

    @Override
    public Call<ParkingSpot> postSinglePost(int id) {
        return requestInterface.postSinglePost(id);
    }
}
