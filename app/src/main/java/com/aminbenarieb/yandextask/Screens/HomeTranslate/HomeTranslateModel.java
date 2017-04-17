package com.aminbenarieb.yandextask.Screens.HomeTranslate;


import java.util.List;

public interface HomeTranslateModel {
    interface TranslateWordCompletionHandler {
        void handle(String translatedWord);
    }
    void translateWord(String word, String resultLanguage,
                              final TranslateWordCompletionHandler completionHandler);
}
