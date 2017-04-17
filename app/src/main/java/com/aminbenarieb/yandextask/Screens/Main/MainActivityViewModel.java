package com.aminbenarieb.yandextask.Screens.Main;

import com.aminbenarieb.yandextask.Extensions.Dynamic;

import java.util.ArrayList;
import java.util.List;

public interface MainActivityViewModel {

    Dynamic<String> mSourceLanguage = new Dynamic("");
    Dynamic<String> mResultLanguage = new Dynamic("");
    Dynamic<List<String>> mLanguagesList = new Dynamic( new ArrayList<String>()  );

    void swapLanguages();
    void loadLanguages();

    void setSourceLanguage( String lanuage );
    void setResultLanguage( String lanuage );
}
