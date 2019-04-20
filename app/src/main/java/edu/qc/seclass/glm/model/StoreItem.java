package edu.qc.seclass.glm.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class StoreItem implements Parcelable {

    private String itemId;
    private String itemName;
    private String categoryID;

    public StoreItem() {
    }

    public StoreItem(String itemId, String itemName, String categoryID) {
        if (itemId == null) {
            itemId = UUID.randomUUID().toString();
        }

        this.itemId = itemId;
        this.itemName = itemName;
        this.categoryID = categoryID;

    }


    @Override
    public String toString() {
        return "DataItem{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", categoryID='" + categoryID + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemId);
        dest.writeString(this.itemName);
        dest.writeString(this.categoryID);
    }


    protected StoreItem(Parcel in) {
        this.itemId = in.readString();
        this.itemName = in.readString();
        this.categoryID = in.readString();
    }


    public static final Parcelable.Creator<StoreItem> CREATOR = new Parcelable.Creator<StoreItem>() {
        @Override
        public StoreItem createFromParcel(Parcel source) {
            return new StoreItem(source);
        }

        @Override
        public StoreItem[] newArray(int size) {
            return new StoreItem[size];
        }
    };
}
