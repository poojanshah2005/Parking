package com.poojanshah.json_fist_application.Realm;

import io.realm.Realm;

/**
 * Created by shahp on 12/07/2017.
 */

public class RealmHelper {
    Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

//    public void saveData(CakesModel cakesModel){
//        Cake cake = new Cake();
//        cake.setDesc(cakesModel.getDesc());
//        cake.setImage(cakesModel.getImage());
//        cake.setTitle(cakesModel.getTitle());
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.copyToRealmOrUpdate(cake);
//            }
//        });
//    }

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
