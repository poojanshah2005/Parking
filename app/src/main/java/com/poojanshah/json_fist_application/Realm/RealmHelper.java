package com.poojanshah.json_fist_application.Realm;

import com.poojanshah.json_fist_application.model.ParkingSpot;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by shahp on 12/07/2017.
 */

public class RealmHelper {
    Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }



    public void SaveData(ParkingSpot parkingSpot){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(parkingSpot);
            }
        });
    }
//    public void SaveData(List<ParkingSpot> parkingSpot){
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.copyToRealm(parkingSpot);
//            }
//        });
//    }

    public List<ParkingSpot> getParkingList(){
        ArrayList<ParkingSpot> parkingList = new ArrayList<>();
        RealmResults<ParkingSpot> result = realm.where(ParkingSpot.class).findAll();
        for(ParkingSpot spot: result){
            parkingList.add(spot);
        }
        return parkingList;
    }
        public List<ParkingSpot> getMine() {
            ArrayList<ParkingSpot> parkingList = new ArrayList<>();
            RealmResults<ParkingSpot> result = realm.where(ParkingSpot.class).equalTo("isMine",true).findAll();
            RealmResults<ParkingSpot> result2 = realm.where(ParkingSpot.class).findAll();
            for(ParkingSpot spot: result){
                parkingList.add(spot);
            }
            return parkingList;
    }
    public List<ParkingSpot> getParkingListMine(){
        ArrayList<ParkingSpot> parkingList = new ArrayList<>();
        RealmResults<ParkingSpot> result = realm.where(ParkingSpot.class).findAll();
        for(ParkingSpot spot: result){
            if(spot.getMine() != null && spot.getMine()){
                parkingList.add(spot);
            }
        }
        return parkingList;
    }


//    public void saveCake(Cake cake){
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.copyToRealmOrUpdate(cake);
//            }
//        });
//    }
//
//    public void saveCakesModel(CakesModel cake){
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.copyToRealmOrUpdate(cake);
//            }
//        });
//    }
//
//    public Cake findInRealm(String title) {
//        return realm.where(Cake.class).equalTo("title", title).findFirst();
//    }
//
//    public ArrayList<Cake> getCakess(){
//        ArrayList<Cake> cakeList = new ArrayList<>();
//        RealmResults<Cake> result = realm.where(Cake.class).findAll();
//        for(Cake c1: result){
//            cakeList.add(c1);
//        }
//        return cakeList;
//    }
//
//    public ArrayList<CakesModel> getCakesModel(){
//        ArrayList<CakesModel> cakeList = new ArrayList<>();
//        RealmResults<CakesModel> result = realm.where(CakesModel.class).findAll();
//        for(CakesModel c1: result){
//            cakeList.add(c1);
//        }
//        return cakeList;
//    }


}
