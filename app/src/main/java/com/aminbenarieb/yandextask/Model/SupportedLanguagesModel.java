package com.aminbenarieb.yandextask.Model;


import java.util.List;

import com.aminbenarieb.yandextask.Extensions.LanguagesMap;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aminbenarieb on 4/16/17.
 */

public class SupportedLanguagesModel {

    @SerializedName("dirs")
    @Expose
    private List<String> dirs = null;
    @SerializedName("langs")
    @Expose
    private LanguagesMap langs;

    public List<String> getDirs() {
        return dirs;
    }

    public void setDirs(List<String> dirs) {
        this.dirs = dirs;
    }

    public LanguagesMap getLangs() {
        return langs;
    }

    public void setLangs(LanguagesMap langs) {
        this.langs = langs;
    }

}


