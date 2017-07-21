package com.poojanshah.json_fist_application.Injection.components;

import com.poojanshah.json_fist_application.Injection.AppModule;
import com.poojanshah.json_fist_application.Injection.NetModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by shahp on 19/07/2017.
 */

@Singleton
@Component(modules = {NetModule.class, AppModule.class})
public interface NetComponent {
    Retrofit retrofit();
}
