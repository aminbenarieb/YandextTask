package com.aminbenarieb.yandextask.Services.Repository;

import com.aminbenarieb.yandextask.Entity.Word.Word;

import java.util.List;

public class ABRepositoryResponse implements RepositoryResponse {
    private List<Word> words;
    private Boolean successed;
    private Throwable error;

    ABRepositoryResponse(List<Word> words, Boolean successed, Throwable error){
        this.words = words;
        this.successed = successed;
        this.error = error;
    }
}
