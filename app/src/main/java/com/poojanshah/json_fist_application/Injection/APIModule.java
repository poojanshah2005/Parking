package com.poojanshah.json_fist_application.Injection;

import com.poojanshah.json_fist_application.service.RequestInterface;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by shahp on 19/07/2017.
 */
@Module
public class APIModule {
    @PerActivity
    @Provides
    RequestInterface provideMovieAPI(Retrofit retrofit) {
        return retrofit.create(RequestInterface.class);
    }
}
