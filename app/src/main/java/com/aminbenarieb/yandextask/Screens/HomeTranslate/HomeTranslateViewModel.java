package com.aminbenarieb.yandextask.Screens.HomeTranslate;


import android.support.annotation.NonNull;

import com.aminbenarieb.yandextask.Extensions.Dynamic;

public interface HomeTranslateViewModel {

    Dynamic<String> mTranslatedWord = new Dynamic("");

    interface HomeTranslateCompetionHandler {
        void handle(Throwable t);
    }

    void translateWord(@NonNull String word,
                       @NonNull String resultLanguage,
                       final @NonNull HomeTranslateCompetionHandler competionHandler);

}
