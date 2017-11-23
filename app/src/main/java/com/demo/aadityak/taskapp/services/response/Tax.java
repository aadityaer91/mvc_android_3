package com.demo.aadityak.taskapp.services.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aaditya on 20/11/17.
 */

public class Tax implements Parcelable {

    @SerializedName("name")
    String name;
    @SerializedName("value")
    double value;

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeDouble(this.value);
    }

    public Tax() {
    }

    protected Tax(Parcel in) {
        this.name = in.readString();
        this.value = in.readDouble();
    }

    public static final Creator<Tax> CREATOR = new Creator<Tax>() {
        @Override
        public Tax createFromParcel(Parcel source) {
            return new Tax(source);
        }

        @Override
        public Tax[] newArray(int size) {
            return new Tax[size];
        }
    };
}
