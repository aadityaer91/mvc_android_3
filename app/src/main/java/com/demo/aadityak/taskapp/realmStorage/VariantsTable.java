package com.demo.aadityak.taskapp.realmStorage;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by aaditya on 22/11/17.
 */

public class VariantsTable extends RealmObject implements Parcelable{
    @PrimaryKey
    private  int id;
    private String color;
    private int size;
    private double price;
    private int parentProductId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getParentProductId() {
        return parentProductId;
    }

    public void setParentProductId(int parentProductId) {
        this.parentProductId = parentProductId;
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
        dest.writeInt(this.parentProductId);
    }

    public VariantsTable() {
    }

    protected VariantsTable(Parcel in) {
        this.id = in.readInt();
        this.color = in.readString();
        this.size = in.readInt();
        this.price = in.readDouble();
        this.parentProductId = in.readInt();
    }

    public static final Creator<VariantsTable> CREATOR = new Creator<VariantsTable>() {
        @Override
        public VariantsTable createFromParcel(Parcel source) {
            return new VariantsTable(source);
        }

        @Override
        public VariantsTable[] newArray(int size) {
            return new VariantsTable[size];
        }
    };
}
