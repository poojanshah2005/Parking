package com.poojanshah.json_fist_application.Injection;

import com.poojanshah.json_fist_application.MVP.CakeListPresenterImpl;
import com.poojanshah.json_fist_application.MVP.interactor.Interactor_Impl;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shahp on 18/07/2017.
 */
@Module
public class PresenterModule {
    @Provides
    public Interactor_Impl getInteractor()
    {return new Interactor_Impl();}
}
