package com.poojanshah.json_fist_application.Injection;

import com.poojanshah.json_fist_application.MainActivity;

import dagger.Component;

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
