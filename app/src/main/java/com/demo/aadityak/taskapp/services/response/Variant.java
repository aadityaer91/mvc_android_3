package com.demo.aadityak.taskapp.services.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aaditya on 20/11/17.
 */

public class Variant implements Parcelable {

    @SerializedName("id")
    int id;

    @SerializedName("color")
    String color;

    @SerializedName("size")
    int size;

    @SerializedName("price")
    double price;

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.color);
        dest.writeInt(this.size);
        dest.writeDouble(this.price);
    }

    public Variant() {
    }

    protected Variant(Parcel in) {
        this.id = in.readInt();
        this.color = in.readString();
        this.size = in.readInt();
        this.price = in.readDouble();
    }

    public static final Creator<Variant> CREATOR = new Creator<Variant>() {
        @Override
        public Variant createFromParcel(Parcel source) {
            return new Variant(source);
        }

        @Override
        public Variant[] newArray(int size) {
            return new Variant[size];
        }
    };
}
