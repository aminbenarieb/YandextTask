package com.aminbenarieb.yandextask.Entity.Word;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ABWord extends RealmObject {

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
        this.id = 0;
    }
    public ABWord(long id, String source, String result){
        this.source = source;
        this.result = result;
        this.isFavorite = false;
        this.dateCreated = new Date();
        this.id = id;
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
    public Date getDateCreated() {
        return dateCreated;
    }

    //Setters
    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

}
