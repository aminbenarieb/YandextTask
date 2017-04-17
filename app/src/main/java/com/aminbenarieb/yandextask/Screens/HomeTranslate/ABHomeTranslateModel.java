package com.aminbenarieb.yandextask.Screens.HomeTranslate;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aminbenarieb.yandextask.Extensions.ABApplication;
import com.aminbenarieb.yandextask.Model.TranslatedWordModel;
import com.aminbenarieb.yandextask.R;
import com.aminbenarieb.yandextask.Services.Language.Language;


import retrofit2.Callback;
import retrofit2.Response;

public class ABHomeTranslateModel extends Service implements HomeTranslateModel {

    private String TAG = ABHomeTranslateModel.class.getSimpleName();;
    private Language mLanguage;
    private Context mContext;

    public ABHomeTranslateModel(Context mContext,
                                Language mLanguage) {
        this.mLanguage = mLanguage;
        this.mContext = mContext;
    }

    //region HomeTranslateModel interface

    public void translateWord(String word,
                              String resultLanguage,
                              final TranslateWordCompletionHandler completionHandler) {
        String apiKey = mContext.getString(R.string.api_key);
        String languageKey = mLanguage.keyForLanguage(resultLanguage);
        ABApplication.getApi().getTranslatedWord(apiKey, word, languageKey).enqueue(
                new Callback<TranslatedWordModel>() {
                    @Override
                    public void onResponse(retrofit2.Call<TranslatedWordModel> call,
                                           Response<TranslatedWordModel> response) {

                        if (response.code() != 200 ) {
                            Log.d(TAG, "onResponse - Status : " + response.code());
                            if (response.errorBody() != null) {
                                Log.d(TAG, "onResponse - Status : "
                                        + response.errorBody().toString());
                            }

                            return;
                        }

                        String translatedWords = response.body().getText().toString();
                        String translatedWord = translatedWords.substring(1,  translatedWords.length() - 1);
                        completionHandler.handle(translatedWord);
                    }

                    @Override
                    public void onFailure(retrofit2.Call<TranslatedWordModel> call,
                                          Throwable t) {
                        completionHandler.handle(null);
                    }
                }
        );
    }

    //endregion

    //region Service

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //endregion
}
