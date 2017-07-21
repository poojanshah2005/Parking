package com.poojanshah.json_fist_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((MyApp)getApplicationContext()).getiPresenterComponent().inject(this);
//        setInjections();
//        cakeListPresenter.setContext(getApplicationContext());
//        cakeListPresenter.performCakeListDisplay();
        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
        startActivity(intent);
    }

//    private void setInjections() {
//        APIComponent apiComponent = ((MyApp) getApplicationContext()).getApiComponent();
//        cakeListPresenter.injectForData(apiComponent);
//    }

//    public void openMaps(JustEat justEat){
//        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//        intent.putExtra("justEat",justEat);
//        startActivity(intent);
//
//    }
}
