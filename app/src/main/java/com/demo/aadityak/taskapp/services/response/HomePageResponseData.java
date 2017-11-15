package com.demo.aadityak.taskapp.services.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aaditya on 15/11/17.
 */

public class HomePageResponseData extends AppResponseData implements Parcelable{

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_items")
    private int totalItems;

    @SerializedName("list")
    private HomePageListItemData []items;

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public HomePageListItemData[] getItems() {
        return items;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.totalPages);
        dest.writeInt(this.totalItems);
        dest.writeTypedArray(this.items, flags);
    }

    public HomePageResponseData() {
    }

    protected HomePageResponseData(Parcel in) {
        this.totalPages = in.readInt();
        this.totalItems = in.readInt();
        this.items = in.createTypedArray(HomePageListItemData.CREATOR);
    }

    public static final Creator<HomePageResponseData> CREATOR = new Creator<HomePageResponseData>() {
        @Override
        public HomePageResponseData createFromParcel(Parcel source) {
            return new HomePageResponseData(source);
        }

        @Override
        public HomePageResponseData[] newArray(int size) {
            return new HomePageResponseData[size];
        }
    };
}
