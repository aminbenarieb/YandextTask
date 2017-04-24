package com.aminbenarieb.yandextask.Screens.HomeTranslate;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aminbenarieb.yandextask.Entity.Word.WordInfo;
import com.aminbenarieb.yandextask.Extensions.ABApplication;
import com.aminbenarieb.yandextask.Entity.TranslatedWordModel;
import com.aminbenarieb.yandextask.R;
import com.aminbenarieb.yandextask.Services.Language.Language;
import com.aminbenarieb.yandextask.Services.Repository.ABRepositoryRequest;
import com.aminbenarieb.yandextask.Services.Repository.ABRepositoryResponse;
import com.aminbenarieb.yandextask.Services.Repository.Repository;
import com.aminbenarieb.yandextask.Services.Repository.RepositoryResponse;


import retrofit2.Callback;
import retrofit2.Response;

public class ABHomeTranslateModel extends Service implements HomeTranslateModel {

    private String TAG = ABHomeTranslateModel.class.getSimpleName();;
    private Language mLanguage;
    private Context mContext;
    private Repository mRepository;

    public ABHomeTranslateModel(Context mContext,
                                Repository mRepository,
                                Language mLanguage) {
        this.mLanguage = mLanguage;
        this.mContext = mContext;
        this.mRepository = mRepository;
    }

    //region HomeTranslateModel interface

    public void translateWord(final String word,
                              final String resultLanguage,
                              final TranslateWordCompletionHandler completionHandler) {
        if (word == null || resultLanguage == null)
            return;

        final String sourceWord = word;

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

                        final WordInfo wordInfo = new WordInfo(sourceWord, translatedWord);
                        mRepository.addWord(new ABRepositoryRequest(wordInfo),
                                new Repository.RepositoryCompletionHandler() {
                            @Override
                            public void handle(RepositoryResponse response) {
                                ABRepositoryResponse mResponse = (ABRepositoryResponse)response;
                                Throwable t = mResponse.getError();
                                completionHandler.handle(wordInfo,t);
                            }
                        });

                        completionHandler.handle(wordInfo, null);
                    }

                    @Override
                    public void onFailure(retrofit2.Call<TranslatedWordModel> call,
                                          Throwable t) {
                        completionHandler.handle(null, t);
                    }
                }
        );
    }

    @Override
    public void addWordToBookmarks(WordInfo wordInfo,
                              final TranslateWordCompletionHandler completionHandler) {
        mRepository.toggleFavoriteWord(new ABRepositoryRequest(wordInfo),
                new Repository.RepositoryCompletionHandler() {
            @Override
            public void handle(RepositoryResponse response) {
                ABRepositoryResponse mResponse = (ABRepositoryResponse)response;
                Throwable t = mResponse.getError();
                completionHandler.handle(null,t);
            }
        });
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
