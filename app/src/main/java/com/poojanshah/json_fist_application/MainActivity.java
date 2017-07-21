package com.poojanshah.json_fist_application;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.poojanshah.json_fist_application.Injection.components.APIComponent;
import com.poojanshah.json_fist_application.MVP.CakeListPresenterImpl;
import com.poojanshah.json_fist_application.MVP.interactor.Interactor_Impl;
import com.poojanshah.json_fist_application.model.JustEat;
import com.poojanshah.json_fist_application.model.Restaurant;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Inject
    CakeListPresenterImpl cakeListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((MyApp)getApplicationContext()).getiPresenterComponent().inject(this);
        setInjections();
        cakeListPresenter.setContext(getApplicationContext());
        cakeListPresenter.performCakeListDisplay();
//        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
//        startActivity(intent);
    }

    private void setInjections() {
        APIComponent apiComponent = ((MyApp) getApplicationContext()).getApiComponent();
        cakeListPresenter.injectForData(apiComponent);
    }

//    public void openMaps(JustEat justEat){
//        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//        intent.putExtra("justEat",justEat);
//        startActivity(intent);
//
//    }
}
