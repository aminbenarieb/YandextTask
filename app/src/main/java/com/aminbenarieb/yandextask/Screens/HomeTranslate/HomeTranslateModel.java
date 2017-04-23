package com.aminbenarieb.yandextask.Screens.HomeTranslate;


import java.util.List;

public interface HomeTranslateModel {
    interface TranslateWordCompletionHandler {
        void handle(String translatedWord, Throwable t);
    }
    void translateWord(final String word,
                       final String resultLanguage,
                       final TranslateWordCompletionHandler completionHandler);
}
