package com.poojanshah.json_fist_application;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Base64;

import com.poojanshah.json_fist_application.Injection.APIModule;
import com.poojanshah.json_fist_application.Injection.AppModule;
import com.poojanshah.json_fist_application.Injection.DaggerIPresenterComponent;
import com.poojanshah.json_fist_application.Injection.IPresenterComponent;
import com.poojanshah.json_fist_application.Injection.NetModule;
import com.poojanshah.json_fist_application.Injection.components.APIComponent;
import com.poojanshah.json_fist_application.Injection.components.DaggerAPIComponent;
import com.poojanshah.json_fist_application.Injection.components.DaggerNetComponent;
import com.poojanshah.json_fist_application.Injection.components.NetComponent;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by shahp on 12/07/2017.
 */

public class MyApp extends Application {
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String KEY = "KEY";

    IPresenterComponent iPresenterComponent;
    NetComponent netComponent;
    APIComponent apiComponent;

    public NetComponent getNetComponent() {
        return netComponent;
    }

    public APIComponent getApiComponent() {
        return apiComponent;
    }

    public IPresenterComponent getiPresenterComponent() {
        return iPresenterComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        iPresenterComponent= DaggerIPresenterComponent.create();
        netComponent= DaggerNetComponent.builder()
                .netModule(new NetModule(Constants.BASE_URL))
                .appModule(new AppModule(this))
                .build();

        apiComponent= DaggerAPIComponent.builder()
                .netComponent(netComponent)
                .aPIModule(new APIModule())
                .build();

        Realm.init(getApplicationContext());
//        byte[] key = new byte[64];
//        byte[] keyValue=new byte[] {'0','2','3','4','5','6','7','8','9','1','2','3','4','5','6','7','0','2','3','4','5','6','7','8','9','1','2','3','4','5','6','7','0','2','3','4','5','6','7','8','9','1','2','3','4','5','6','7','0','2','3','4','5','6','7','8','9','1','2','3','4','5','6','7'};
        byte[] key;
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if(!settings.contains(KEY)){
            key = new byte[64];
            new SecureRandom().nextBytes(key);
            String saveThis = Base64.encodeToString(key, Base64.DEFAULT);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(KEY,saveThis);
            editor.commit();
        } else{
            String stringFromSharedPrefs = settings.getString(KEY,"");
            key = Base64.decode(stringFromSharedPrefs, Base64.DEFAULT);
        }



//        new SecureRandom().nextBytes(key);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(1)
                .encryptionKey(key)
                .deleteRealmIfMigrationNeeded()
                .build();
        // Commit the edits!

        Realm.setDefaultConfiguration(realmConfiguration);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
