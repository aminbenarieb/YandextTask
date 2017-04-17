package com.aminbenarieb.yandextask.Screens.Main;

import com.aminbenarieb.yandextask.Services.Language.Language;

import java.util.List;

public class ABMainActivityModel implements MainActivityModel {
    private Language mLanguage;
    public ABMainActivityModel(Language mLanguage) {
        this.mLanguage = mLanguage;
    }

    public void loadLanguages(final MainActivityModel.LanguageListCompletionHandler completion) {
        mLanguage.loadLanguages(new Language.LanguageListCompletionHandler() {
            @Override
            public void handle(List<String> languagesList) {
                completion.handle(languagesList);
            }
        });
    }
}
