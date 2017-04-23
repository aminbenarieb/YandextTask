package com.aminbenarieb.yandextask.Services.Repository;


import com.aminbenarieb.yandextask.Entity.Word.Word;

import java.util.List;

public class ABRepositoryRequest implements RepositoryRequest {
    List<Word> wordList;
    Word word;
    public ABRepositoryRequest(Word word) {
        this.word = word;
    }
    public ABRepositoryRequest(List<Word> wordList) {
        this.wordList = wordList;
    }
}
