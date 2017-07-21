package com.poojanshah.json_fist_application.model;

/**
 * Created by shahp on 21/07/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParkingSpot implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cost_per_minute")
    @Expose
    private String costPerMinute;
    @SerializedName("max_reserve_time_mins")
    @Expose
    private Long maxReserveTimeMins;
    @SerializedName("min_reserve_time_mins")
    @Expose
    private Long minReserveTimeMins;
    @SerializedName("is_reserved")
    @Expose
    private Boolean isReserved;
    @SerializedName("reserved_until")
    @Expose
    private Object reservedUntil;
    public final static Parcelable.Creator<ParkingSpot> CREATOR = new Creator<ParkingSpot>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ParkingSpot createFromParcel(Parcel in) {
            ParkingSpot instance = new ParkingSpot();
            instance.id = ((Long) in.readValue((Long.class.getClassLoader())));
            instance.lat = ((String) in.readValue((String.class.getClassLoader())));
            instance.lng = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.costPerMinute = ((String) in.readValue((String.class.getClassLoader())));
            instance.maxReserveTimeMins = ((Long) in.readValue((Long.class.getClassLoader())));
            instance.minReserveTimeMins = ((Long) in.readValue((Long.class.getClassLoader())));
            instance.isReserved = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.reservedUntil = ((Object) in.readValue((Object.class.getClassLoader())));
            return instance;
        }

        public ParkingSpot[] newArray(int size) {
            return (new ParkingSpot[size]);
        }

    }
            ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCostPerMinute() {
        return costPerMinute;
    }

    public void setCostPerMinute(String costPerMinute) {
        this.costPerMinute = costPerMinute;
    }

    public Long getMaxReserveTimeMins() {
        return maxReserveTimeMins;
    }

    public void setMaxReserveTimeMins(Long maxReserveTimeMins) {
        this.maxReserveTimeMins = maxReserveTimeMins;
    }

    public Long getMinReserveTimeMins() {
        return minReserveTimeMins;
    }

    public void setMinReserveTimeMins(Long minReserveTimeMins) {
        this.minReserveTimeMins = minReserveTimeMins;
    }

    public Boolean getIsReserved() {
        return isReserved;
    }

    public void setIsReserved(Boolean isReserved) {
        this.isReserved = isReserved;
    }

    public Object getReservedUntil() {
        return reservedUntil;
    }

    public void setReservedUntil(Object reservedUntil) {
        this.reservedUntil = reservedUntil;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(lat);
        dest.writeValue(lng);
        dest.writeValue(name);
        dest.writeValue(costPerMinute);
        dest.writeValue(maxReserveTimeMins);
        dest.writeValue(minReserveTimeMins);
        dest.writeValue(isReserved);
        dest.writeValue(reservedUntil);
    }

    public int describeContents() {
        return 0;
    }

}