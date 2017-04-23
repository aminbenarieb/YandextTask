package com.aminbenarieb.yandextask.Services.Language;

import android.content.Context;

import java.util.List;

/**
 * Created by aminbenarieb on 4/16/17.
 */


public interface Language {

    interface LanguageListCompletionHandler {
        void handle(List<String> languagesList, Throwable t);
    }

    public void loadLanguages(LanguageListCompletionHandler completion);
    public String keyForLanguage(String language);

    void setContext(Context mContext);
}
