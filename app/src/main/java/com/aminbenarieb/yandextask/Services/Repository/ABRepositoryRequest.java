package com.aminbenarieb.yandextask.Services.Repository;


import com.aminbenarieb.yandextask.Entity.Word.WordInfo;

import java.util.List;

public class ABRepositoryRequest implements RepositoryRequest {
    List<WordInfo> wordList;
    WordInfo word;
    public ABRepositoryRequest(WordInfo word) {
        this.word = word;
    }
    public ABRepositoryRequest(List<WordInfo> wordList) {
        this.wordList = wordList;
    }
}
