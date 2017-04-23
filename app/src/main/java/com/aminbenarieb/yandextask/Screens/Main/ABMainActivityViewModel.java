package com.aminbenarieb.yandextask.Screens.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aminbenarieb on 4/17/17.
 */

public class ABMainActivityViewModel implements MainActivityViewModel {

    private MainActivityModel model;

    public ABMainActivityViewModel(MainActivityModel model) {
        this.model = model;
    }

    public void swapLanguages() {
        String sourceLang = mSourceLanguage.getValue();
        String resultLang = mResultLanguage.getValue();
        mSourceLanguage.setValue(resultLang);
        mResultLanguage.setValue(sourceLang);
    }
    public void loadLanguages(final LoadLanguagersCompetionHandler competionHandler) {
        model.loadLanguages(new MainActivityModel.LanguageListCompletionHandler() {
            @Override
            public void handle(List<String> languageList, Throwable t) {
                if (t != null) {
                    competionHandler.handle(null, t);
                    return;
                }

                competionHandler.handle(languageList, t);
                mLanguagesList.setValue(languageList);
                mSourceLanguage.setValue( languageList.get(0) );
                mResultLanguage.setValue( languageList.get(1) );
            }
        });
    }

    public void setSourceLanguage( String lanuage ) {
        if (mResultLanguage.getValue().equals(lanuage)) {
            swapLanguages();
            return;
        }

        mSourceLanguage.setValue(lanuage);
    }
    public void setResultLanguage( String lanuage ) {
        if (mSourceLanguage.getValue().equals(lanuage)) {
            swapLanguages();
            return;
        }

        mResultLanguage.setValue(lanuage);
    }
}
