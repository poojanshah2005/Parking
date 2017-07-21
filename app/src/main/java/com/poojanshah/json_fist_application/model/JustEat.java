package com.poojanshah.json_fist_application.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JustEat implements Parcelable
{

    @SerializedName("Restaurants")
    @Expose
    private List<Restaurant> restaurants = null;
    public final static Parcelable.Creator<JustEat> CREATOR = new Creator<JustEat>() {


        @SuppressWarnings({
                "unchecked"
        })
        public JustEat createFromParcel(Parcel in) {
            JustEat instance = new JustEat();
            in.readList(instance.restaurants, (com.poojanshah.json_fist_application.model.Restaurant.class.getClassLoader()));
            return instance;
        }

        public JustEat[] newArray(int size) {
            return (new JustEat[size]);
        }

    }
            ;

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(restaurants);
    }

    public int describeContents() {
        return 0;
    }

}