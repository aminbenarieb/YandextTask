package com.aminbenarieb.yandextask.Services.Language;

import java.util.List;

/**
 * Created by aminbenarieb on 4/16/17.
 */


public interface Language {

    interface LanguageListCompletionHandler {
        void handle(List<String> languagesList);
    }

    public void loadLanguages(LanguageListCompletionHandler completion);
    public String keyForLanguage(String language);

}
