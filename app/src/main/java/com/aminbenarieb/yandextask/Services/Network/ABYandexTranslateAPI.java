package com.aminbenarieb.yandextask.Services.Network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import com.aminbenarieb.yandextask.Entity.TranslatedWordModel;
import com.aminbenarieb.yandextask.Entity.SupportedLanguagesModel;

/**
 * Created by aminbenarieb on 4/12/17.
 */

public interface ABYandexTranslateAPI {

    @GET("translate")
    Call<TranslatedWordModel> getTranslatedWord(@Query("key") String key,
                                                @Query("text") String word,
                                                @Query("lang") String lang);

    @GET("getLangs")
    Call<SupportedLanguagesModel> getSupportedLanguages(@Query("ui") String ui,
                                                        @Query("key") String key);

}
