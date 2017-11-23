package com.demo.aadityak.taskapp.realmStorage;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by aaditya on 22/11/17.
 */

public class ProductsTable extends RealmObject implements Parcelable{
    @PrimaryKey
    private int id;
    private String name;
    private String dateAdded;
    private boolean hasVariants;
    private int categoryId;
    private String taxType;
    private double tax;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public boolean isHasVariants() {
        return hasVariants;
    }

    public void setHasVariants(boolean hasVariants) {
        this.hasVariants = hasVariants;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
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
        dest.writeByte(this.hasVariants ? (byte) 1 : (byte) 0);
        dest.writeInt(this.categoryId);
        dest.writeString(this.taxType);
        dest.writeDouble(this.tax);
    }

    public ProductsTable() {
    }

    protected ProductsTable(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.dateAdded = in.readString();
        this.hasVariants = in.readByte() != 0;
        this.categoryId = in.readInt();
        this.taxType = in.readString();
        this.tax = in.readDouble();
    }

    public static final Creator<ProductsTable> CREATOR = new Creator<ProductsTable>() {
        @Override
        public ProductsTable createFromParcel(Parcel source) {
            return new ProductsTable(source);
        }

        @Override
        public ProductsTable[] newArray(int size) {
            return new ProductsTable[size];
        }
    };
}
