package com.demo.aadityak.taskapp.realmStorage;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by aaditya on 21/11/17.
 */

public class CategoriesTable extends RealmObject implements Parcelable{
    @PrimaryKey
    private int id;
    private String name;
    private int parentId = 0;
    private int childType;//1 for sub category, 2 for products

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getParentId() {
        return parentId;
    }

    public int getChildType() {
        return childType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setChildType(int childType) {
        this.childType = childType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.parentId);
        dest.writeInt(this.childType);
    }

    public CategoriesTable() {
    }

    protected CategoriesTable(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.parentId = in.readInt();
        this.childType = in.readInt();
    }

    public static final Creator<CategoriesTable> CREATOR = new Creator<CategoriesTable>() {
        @Override
        public CategoriesTable createFromParcel(Parcel source) {
            return new CategoriesTable(source);
        }

        @Override
        public CategoriesTable[] newArray(int size) {
            return new CategoriesTable[size];
        }
    };
}
