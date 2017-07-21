package com.poojanshah.json_fist_application.MVP;

import com.poojanshah.json_fist_application.model.JustEat;

/**
 * Created by shahp on 14/07/2017.
 */

public interface ICakeListView extends MVPView {

    //mvp step 3

    void onFetchDataSuccess(JustEat justEat);
    void onFetchDataFailure(Throwable throwable);
    void onFetchDataCompleted();
    void onFetchDataInProgress();

}
