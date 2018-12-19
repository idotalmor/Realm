package com.example.idotalmor.realmdatabase;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmApplication extends Application {

    //I added this application name in the manifest under application
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);


    }
}


//
//    You can have multiple RealmConfiguration objects, so you can control the version, schema and location of each Realm independently.
//
//        RealmConfiguration myConfig = new RealmConfiguration.Builder()
//        .name("myrealm.realm")
//        .schemaVersion(2)
//        .modules(new MyCustomSchema())
//        .build();
//
//        RealmConfiguration otherConfig = new RealmConfiguration.Builder()
//        .name("otherrealm.realm")
//        .schemaVersion(5)
//        .modules(new MyOtherSchema())
//        .build();
//
//        Realm myRealm = Realm.getInstance(myConfig);
//        Realm otherRealm = Realm.getInstance(otherConfig);