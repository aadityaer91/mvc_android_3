package com.demo.aadityak.taskapp.services.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aaditya on 20/11/17.
 */

public class Ranking extends AppResponseData implements Parcelable {
    @SerializedName("ranking")
    String ranking;
    @SerializedName("products")
    ProductRankingInfo[] products;

    public String getRanking() {
        return ranking;
    }

    public ProductRankingInfo[] getProducts() {
        return products;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ranking);
        dest.writeTypedArray(this.products, flags);
    }

    public Ranking() {
    }

    protected Ranking(Parcel in) {
        this.ranking = in.readString();
        this.products = in.createTypedArray(ProductRankingInfo.CREATOR);
    }

    public static final Creator<Ranking> CREATOR = new Creator<Ranking>() {
        @Override
        public Ranking createFromParcel(Parcel source) {
            return new Ranking(source);
        }

        @Override
        public Ranking[] newArray(int size) {
            return new Ranking[size];
        }
    };
}
