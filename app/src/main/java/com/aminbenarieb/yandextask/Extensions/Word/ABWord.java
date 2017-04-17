package com.aminbenarieb.yandextask.Extensions.Word;

import android.os.Parcel;

import android.os.Parcelable;

/**
 * Created by aminbenarieb on 4/6/17.
 */

public class ABWord implements Word {

    private String source;
    private String result;

    public ABWord(String source, String result){
        this.source = source;
        this.result = result;
    }

    //Getters
    public String getSource() {
        return source;
    }
    public String getResult() {
        return result;
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
