package com.aminbenarieb.yandextask;

import android.os.Parcel;

import com.aminbenarieb.yandextask.WordInt;
import android.os.Parcelable;
import android.os.Parcel;

/**
 * Created by aminbenarieb on 4/6/17.
 */

public class Word implements WordInt {

    private String source;
    private String result;


    public Word(String source, String result){
        this.source = source;
        this.result = result;
    }

    //region Parcelable subroutines

    // Parcelling part
    public Word(Parcel in){
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
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
    //endregion
}
