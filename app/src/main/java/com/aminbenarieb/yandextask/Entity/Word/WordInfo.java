package com.aminbenarieb.yandextask.Entity.Word;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class WordInfo implements Parcelable {

    private long id;
    private String source;
    private String result;
    private Boolean isFavorite;
    private Date dateCreated;

    public WordInfo() {
        this.source = "";
        this.result = "";
        this.isFavorite = false;
        this.dateCreated = new Date();
    }

    public WordInfo(String source, String result) {
        this.source = source;
        this.result = result;
        this.isFavorite = false;
        this.dateCreated = new Date();
    }

    public WordInfo(long id, String source, String result, Date date, Boolean isFavorite) {
        this.id = id;
        this.source = source;
        this.result = result;
        this.isFavorite = isFavorite;
        this.dateCreated = date;
    }

    //Getters
    public String getSource() {
        return source;
    }

    public String getResult() {
        return result;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public long getId() {
        return id;
    }

    //Setters
    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    //region Parcelable subroutines

    public WordInfo(Parcel in) {
        String[] data = new String[2];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.source = data[0];
        this.result = data[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public WordInfo createFromParcel(Parcel in) {
            return new WordInfo(in);
        }

        public WordInfo[] newArray(int size) {
            return new WordInfo[size];
        }
    };
    //endregion
}
