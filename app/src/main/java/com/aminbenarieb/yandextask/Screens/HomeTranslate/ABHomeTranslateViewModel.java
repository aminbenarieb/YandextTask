package com.aminbenarieb.yandextask.Screens.HomeTranslate;


import android.support.annotation.NonNull;

import com.aminbenarieb.yandextask.Entity.Word.WordInfo;
import com.aminbenarieb.yandextask.Extensions.Dynamic;

public class ABHomeTranslateViewModel implements HomeTranslateViewModel {

    private HomeTranslateModel model;
    private WordInfo wordInfo;

    public ABHomeTranslateViewModel(String sourceWord,
                                    String resultWord,
                                    HomeTranslateModel model) {
        this.model = model;
        this.wordInfo = new WordInfo(sourceWord, resultWord);
    }

    @Override
    public void translateWord(@NonNull String word,
                              @NonNull String resultLanguage,
                              final @NonNull HomeTranslateCompetionHandler competionHandler) {
        model.translateWord(word, resultLanguage,
                new HomeTranslateModel.TranslateWordCompletionHandler() {
                    @Override
                    public void handle(WordInfo resultWordInfo, Throwable t) {
                        if (t != null) {
                            competionHandler.handle(t);
                            return;
                        }
                        if (resultWordInfo == null) {
                            return;
                        }

                        wordInfo = resultWordInfo;
                        mTranslatedWord.setValue(resultWordInfo.getResult());
                    }
                });
    }

    @Override
    public void cleanWord() {
        mTranslatedWord.setValue("");
    }

    @Override
    public void addWordToBookmarks(final @NonNull HomeTranslateCompetionHandler competionHandler) {

        Boolean newValue = !wordInfo.getFavorite();
        isFavorite.setValue( newValue );
        wordInfo.setFavorite( newValue );

        model.addWordToBookmarks(wordInfo,
                new HomeTranslateModel.TranslateWordCompletionHandler() {
            @Override
            public void handle(WordInfo wordInfo, Throwable t) {
                competionHandler.handle(t);
            }
        });
    }
}
