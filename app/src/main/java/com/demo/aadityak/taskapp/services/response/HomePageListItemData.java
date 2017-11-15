package com.demo.aadityak.taskapp.services.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aaditya on 15/11/17.
 */

public class HomePageListItemData implements Parcelable{
    @SerializedName("name")
    private String name;

    @SerializedName("icon")
    private String icon;

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.icon);
    }

    public HomePageListItemData() {
    }

    protected HomePageListItemData(Parcel in) {
        this.name = in.readString();
        this.icon = in.readString();
    }

    public static final Creator<HomePageListItemData> CREATOR = new Creator<HomePageListItemData>() {
        @Override
        public HomePageListItemData createFromParcel(Parcel source) {
            return new HomePageListItemData(source);
        }

        @Override
        public HomePageListItemData[] newArray(int size) {
            return new HomePageListItemData[size];
        }
    };
}
