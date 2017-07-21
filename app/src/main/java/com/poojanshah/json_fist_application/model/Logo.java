package com.poojanshah.json_fist_application.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Logo implements Parcelable
{

    @SerializedName("StandardResolutionURL")
    @Expose
    private String standardResolutionURL;
    public final static Parcelable.Creator<Logo> CREATOR = new Creator<Logo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Logo createFromParcel(Parcel in) {
            Logo instance = new Logo();
            instance.standardResolutionURL = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Logo[] newArray(int size) {
            return (new Logo[size]);
        }

    }
            ;

    public String getStandardResolutionURL() {
        return standardResolutionURL;
    }

    public void setStandardResolutionURL(String standardResolutionURL) {
        this.standardResolutionURL = standardResolutionURL;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(standardResolutionURL);
    }

    public int describeContents() {
        return 0;
    }

}