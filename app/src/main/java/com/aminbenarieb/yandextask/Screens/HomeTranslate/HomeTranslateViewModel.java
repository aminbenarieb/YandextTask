package com.aminbenarieb.yandextask.Screens.HomeTranslate;


import android.support.annotation.NonNull;

import com.aminbenarieb.yandextask.Entity.Word.WordInfo;
import com.aminbenarieb.yandextask.Extensions.Dynamic;

public interface HomeTranslateViewModel {

    Dynamic<String> mTranslatedWord = new Dynamic("");
    Dynamic<Boolean> isFavorite = new Dynamic<>(false);

    interface HomeTranslateCompetionHandler {
        void handle(Throwable t);
    }

    void translateWord(@NonNull String word,
                       @NonNull String resultLanguage,
                       final @NonNull HomeTranslateCompetionHandler competionHandler);

    void cleanWord();

    void addWordToBookmarks(final @NonNull HomeTranslateCompetionHandler competionHandler);

}
