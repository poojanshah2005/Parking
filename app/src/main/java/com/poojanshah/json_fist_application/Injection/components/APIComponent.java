package com.poojanshah.json_fist_application.Injection.components;

import com.poojanshah.json_fist_application.Injection.APIModule;
import com.poojanshah.json_fist_application.Injection.PerActivity;
import com.poojanshah.json_fist_application.MVP.interactor.Interactor_Impl;

import dagger.Component;

/**
 * Created by shahp on 19/07/2017.
 */
@PerActivity
@Component(dependencies = NetComponent.class, modules = APIModule.class)
public interface APIComponent {
    void inject(Interactor_Impl interactor_);
}
