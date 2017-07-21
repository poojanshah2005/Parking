package com.poojanshah.json_fist_application.MVP.interactor;

import com.poojanshah.json_fist_application.Injection.components.APIComponent;
import com.poojanshah.json_fist_application.model.JustEat;

import io.reactivex.Observable;

/**
 * Created by shahp on 14/07/2017.
 */

public interface Interactor {

    public void initiateInjectionGraph(APIComponent apiComponent);

    Observable<JustEat> getCakeList();

}
