package com.poojanshah.json_fist_application.Injection;

import com.poojanshah.json_fist_application.MapsActivity;

import dagger.Component;

/**
 * Created by shahp on 18/07/2017.
 */
@PerActivity
@Component(dependencies = {PresenterModule.class})
public interface IPresenterComponent {
    void inject(MapsActivity mapsActivity);
   // void inject(CakeListPresenterImpl cakeListPresenter);
//    void inject(Interactor_Impl interactor_);
}
