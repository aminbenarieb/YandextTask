package com.aminbenarieb.yandextask.Services.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import com.aminbenarieb.yandextask.PostModel;

/**
 * Created by aminbenarieb on 4/12/17.
 */

public interface ABYandexTranslateAPI {

    @GET("/api/v1.5/tr.json/translate")
    Call<PostModel> getTranslatedWord(@Query("key") String key,
                                      @Query("text") String word,
                                      @Query("lang") String lang);

}
