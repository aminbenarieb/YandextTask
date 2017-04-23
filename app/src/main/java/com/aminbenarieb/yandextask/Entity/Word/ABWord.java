package com.aminbenarieb.yandextask.Entity.Word;

import android.os.Parcel;

import android.os.Parcelable;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ABWord extends RealmObject implements Word {

    @PrimaryKey
    private long id;
    private String source;
    private String result;
    private Boolean isFavorite;
    private Date dateCreated;

    public ABWord() {
        this.source = "";
        this.result = "";
        this.isFavorite = false;
        this.dateCreated = new Date();
    }
    public ABWord(String source, String result){
        this.source = source;
        this.result = result;
        this.isFavorite = false;
        this.dateCreated = new Date();
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

    //Setters
    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }
    public void setId(long id) {
        this.id = id;
    }

    //region Parcelable subroutines

    // Parcelling part
    public ABWord(Parcel in){
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
        public ABWord createFromParcel(Parcel in) {
            return new ABWord(in);
        }

        public ABWord[] newArray(int size) {
            return new ABWord[size];
        }
    };
    //endregion
}
