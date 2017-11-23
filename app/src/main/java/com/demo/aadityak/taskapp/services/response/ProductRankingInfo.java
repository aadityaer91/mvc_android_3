package com.demo.aadityak.taskapp.services.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aaditya on 20/11/17.
 */

public class ProductRankingInfo implements Parcelable{
    @SerializedName("id")
    int id;
    @SerializedName("view_count")
    long viewCount;
    @SerializedName("order_count")
    long orderCount;
    @SerializedName("shares")
    long shares;

    public int getId() {
        return id;
    }

    public long getViewCount() {
        return viewCount;
    }

    public long getOrderCount() {
        return orderCount;
    }

    public long getShares() {
        return shares;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeLong(this.viewCount);
        dest.writeLong(this.orderCount);
        dest.writeLong(this.shares);
    }

    public ProductRankingInfo() {
    }

    protected ProductRankingInfo(Parcel in) {
        this.id = in.readInt();
        this.viewCount = in.readLong();
        this.orderCount = in.readLong();
        this.shares = in.readLong();
    }

    public static final Creator<ProductRankingInfo> CREATOR = new Creator<ProductRankingInfo>() {
        @Override
        public ProductRankingInfo createFromParcel(Parcel source) {
            return new ProductRankingInfo(source);
        }

        @Override
        public ProductRankingInfo[] newArray(int size) {
            return new ProductRankingInfo[size];
        }
    };
}
