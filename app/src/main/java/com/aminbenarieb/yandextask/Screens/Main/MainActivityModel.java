package com.aminbenarieb.yandextask.Screens.Main;

import java.util.List;

/**
 * Created by aminbenarieb on 4/17/17.
 */

public interface MainActivityModel {
    interface LanguageListCompletionHandler {
        void handle(List<String> languageList, Throwable t);
    }

    void loadLanguages(final MainActivityModel.LanguageListCompletionHandler completion);
}
