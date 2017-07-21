package com.poojanshah.json_fist_application.MVP.interactor;

import com.poojanshah.json_fist_application.Injection.components.APIComponent;
import com.poojanshah.json_fist_application.model.ParkingSpot;
import com.poojanshah.json_fist_application.service.RequestInterface;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by shahp on 14/07/2017.
 */

public class Interactor_Impl implements Interactor {
    //    static OkHttpClient okHttpClient;
    @Inject
    RequestInterface requestInterface;

    public Interactor_Impl() {
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
////        File outputDir = context.getCacheDir();
////        Cache cache = new Cache(outputDir, 50000);
//
//        okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(httpLoggingInterceptor)
//                .build();
//
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
////                .client(okHttpClient)
//                .build();
//
//        requestInterface = retrofit.create(RequestInterface.class);
//        return retrofit.create(RequestInterface.class);
    }

    /**
     * The Dagger graph injection is performed in the method.
     * @param apiComponent
     */
    @Override
    public void initiateInjectionGraph(APIComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    public Observable<List<ParkingSpot>> getCakeList() {
        return requestInterface.getResult(51D,51D);
    }
}
