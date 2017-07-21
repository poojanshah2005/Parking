package com.poojanshah.json_fist_application.Injection;

import com.poojanshah.json_fist_application.MVP.CakeListPresenterImpl;
import com.poojanshah.json_fist_application.MVP.interactor.Interactor_Impl;
import com.poojanshah.json_fist_application.MainActivity;

import dagger.Component;
import dagger.Provides;

/**
 * Created by shahp on 18/07/2017.
 */
@PerActivity
@Component(dependencies = {PresenterModule.class})
public interface IPresenterComponent {
    void inject(MainActivity mainActivity);
   // void inject(CakeListPresenterImpl cakeListPresenter);
//    void inject(Interactor_Impl interactor_);
}
