package com.aminbenarieb.yandextask.Screens.HomeTranslate;


import com.aminbenarieb.yandextask.Extensions.Dynamic;


public interface HomeTranslateViewModel {

    Dynamic<String> mTranslatedWord = new Dynamic("");

    void translateWord(String word, String resultLanguage);

}
