package com.poojanshah.json_fist_application.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CuisineType implements Parcelable
{

    @SerializedName("Id")
    @Expose
    private Double id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("SeoName")
    @Expose
    private String seoName;
    public final static Parcelable.Creator<CuisineType> CREATOR = new Creator<CuisineType>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CuisineType createFromParcel(Parcel in) {
            CuisineType instance = new CuisineType();
            instance.id = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.seoName = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public CuisineType[] newArray(int size) {
            return (new CuisineType[size]);
        }

    }
            ;

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeoName() {
        return seoName;
    }

    public void setSeoName(String seoName) {
        this.seoName = seoName;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(seoName);
    }

    public int describeContents() {
        return 0;
    }

}