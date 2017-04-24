package com.aminbenarieb.yandextask.Screens.HomeTranslate;


import com.aminbenarieb.yandextask.Entity.Word.WordInfo;


public interface HomeTranslateModel {
    interface TranslateWordCompletionHandler {
        void handle(WordInfo wordInfo, Throwable t);
    }

    void translateWord(final String word,
                       final String resultLanguage,
                       final TranslateWordCompletionHandler completionHandler);

    void addWordToBookmarks(WordInfo wordInfo,
                            final TranslateWordCompletionHandler completionHandler);
}
