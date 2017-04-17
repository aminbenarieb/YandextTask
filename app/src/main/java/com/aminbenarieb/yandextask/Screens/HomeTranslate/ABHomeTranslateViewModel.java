package com.aminbenarieb.yandextask.Screens.HomeTranslate;


public class ABHomeTranslateViewModel implements HomeTranslateViewModel {

    private HomeTranslateModel model;

    public ABHomeTranslateViewModel(HomeTranslateModel model) {
        this.model = model;
    }

    @Override
    public void translateWord(String word, String resultLanguage) {
        model.translateWord(word, resultLanguage,
                new HomeTranslateModel.TranslateWordCompletionHandler() {
            @Override
            public void handle(String translatedWord) {
                mTranslatedWord.setValue(translatedWord);
            }
        });
    }

}
