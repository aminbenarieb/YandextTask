package com.aminbenarieb.yandextask;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostModel implements Parcelable
{

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("text")
    @Expose
    private List<String> text = new ArrayList<String>();
    public final static Parcelable.Creator<PostModel> CREATOR = new Creator<PostModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PostModel createFromParcel(Parcel in) {
            PostModel instance = new PostModel();
            instance.code = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.lang = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.text, (java.lang.String.class.getClassLoader()));
            return instance;
        }

        public PostModel[] newArray(int size) {
            return (new PostModel[size]);
        }

    }
            ;

    /**
     * No args constructor for use in serialization
     *
     */
    public PostModel() {
    }

    /**
     *
     * @param text
     * @param code
     * @param lang
     */
    public PostModel(Integer code, String lang, List<String> text) {
        super();
        this.code = code;
        this.lang = lang;
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(lang);
        dest.writeList(text);
    }

    public int describeContents() {
        return 0;
    }

}
