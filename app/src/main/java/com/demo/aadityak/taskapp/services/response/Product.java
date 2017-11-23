package com.demo.aadityak.taskapp.services.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aaditya on 20/11/17.
 */

public class Product extends AppResponseData implements Parcelable{
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("date_added")
    String dateAdded;
    @SerializedName("variants")
    Variant[] variants;
    @SerializedName("tax")
    Tax tax;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public Variant[] getVariants() {
        return variants;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.dateAdded);
        dest.writeTypedArray(this.variants, flags);
        dest.writeParcelable(this.tax, flags);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.dateAdded = in.readString();
        this.variants = in.createTypedArray(Variant.CREATOR);
        this.tax = in.readParcelable(Tax.class.getClassLoader());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
