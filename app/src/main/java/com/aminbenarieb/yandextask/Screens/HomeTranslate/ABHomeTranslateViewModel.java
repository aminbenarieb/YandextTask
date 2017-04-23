package com.aminbenarieb.yandextask.Screens.HomeTranslate;


import android.support.annotation.NonNull;

public class ABHomeTranslateViewModel implements HomeTranslateViewModel {

    private HomeTranslateModel model;

    public ABHomeTranslateViewModel(HomeTranslateModel model) {
        this.model = model;
    }

    @Override
    public void translateWord(@NonNull String word,
                              @NonNull String resultLanguage,
                              final @NonNull HomeTranslateCompetionHandler competionHandler) {
        model.translateWord(word, resultLanguage,
                new HomeTranslateModel.TranslateWordCompletionHandler() {
                    @Override
                    public void handle(String translatedWord, Throwable t) {
                        if (t != null) {
                            competionHandler.handle(t);
                            return;
                        }

                        mTranslatedWord.setValue(translatedWord);
                    }
                });
    }
}
