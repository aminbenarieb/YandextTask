package com.aminbenarieb.yandextask.Services.Repository;

import com.aminbenarieb.yandextask.Entity.Word.WordInfo;

import java.util.List;

public class ABRepositoryResponse implements RepositoryResponse {
    private List<WordInfo> words;
    private Boolean successed;
    private Throwable error;

    ABRepositoryResponse(List<WordInfo> words, Boolean successed, Throwable error){
        this.words = words;
        this.successed = successed;
        this.error = error;
    }

    public List<WordInfo> getWords() {
        return this.words;
    }
    public Boolean getSuccessed() {
        return this.successed;
    }
    public Throwable getError() {
        return this.error;
    }
}
