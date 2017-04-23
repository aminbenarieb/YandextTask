package com.aminbenarieb.yandextask.Screens.Main;

import com.aminbenarieb.yandextask.Extensions.Dynamic;

import java.util.ArrayList;
import java.util.List;

public interface MainActivityViewModel {

    Dynamic<String> mSourceLanguage = new Dynamic("");
    Dynamic<String> mResultLanguage = new Dynamic("");
    Dynamic<List<String>> mLanguagesList = new Dynamic( new ArrayList<String>()  );

    interface LoadLanguagersCompetionHandler {
        void handle(List<String> languagesList, Throwable t);
    }

    void swapLanguages();
    void loadLanguages(final LoadLanguagersCompetionHandler competionHandler);

    void setSourceLanguage( String lanuage );
    void setResultLanguage( String lanuage );
}
