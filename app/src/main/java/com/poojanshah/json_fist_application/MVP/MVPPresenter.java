package com.poojanshah.json_fist_application.MVP;

/**
 * Created by shahp on 14/07/2017.
 */
//MVP step 2
public interface MVPPresenter <v extends MVPView> {

        void attachView(v MVPView);

        void detachView();

}
