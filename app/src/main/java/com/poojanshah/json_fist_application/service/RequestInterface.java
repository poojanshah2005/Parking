package com.poojanshah.json_fist_application.service;

import com.poojanshah.json_fist_application.Constants;
import com.poojanshah.json_fist_application.model.JustEat;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by shahp on 12/07/2017.
 */

public interface RequestInterface {
    @Headers({
            "Accept-Tenant: uk",
            "Accept-Language: en-GB",
            "Authorization: Basic VGVjaFRlc3RBUEk6dXNlcjI=",
            "Host: public.je-apis.com"

    })
    @GET(Constants.DATA_TO_FETCH)
    Observable<JustEat> getResult(@Query("q") String location);




}
