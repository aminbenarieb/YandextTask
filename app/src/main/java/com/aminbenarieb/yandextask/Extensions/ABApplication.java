package com.aminbenarieb.yandextask.Extensions;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.aminbenarieb.yandextask.R;
import com.aminbenarieb.yandextask.Services.Network.ABYandexTranslateAPI;

import android.app.Application;

/**
 * Created by aminbenarieb on 4/15/17.
 */

public class ABApplication extends Application {

    private static ABYandexTranslateAPI translateAPI;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.request_base_url))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        translateAPI = retrofit.create(ABYandexTranslateAPI.class);
    }

    public static ABYandexTranslateAPI getApi() {
        return translateAPI;
    }

}
