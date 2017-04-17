package com.aminbenarieb.yandextask.Services.Language;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aminbenarieb.yandextask.Extensions.ABApplication;
import com.aminbenarieb.yandextask.Extensions.LanguagesMap;
import com.aminbenarieb.yandextask.Model.SupportedLanguagesModel;
import com.aminbenarieb.yandextask.R;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ABLanguage extends Service implements Language {
    private String TAG = "ABLanguage";
    private LanguagesMap languagesMap;
    private Context mContext;
    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public static final ABLanguage INSTANCE = new ABLanguage();

    private ABLanguage() {}

    public void loadLanguages(final LanguageListCompletionHandler completion) {
        final ABLanguage self = this;
        downloadSupportedLanguagesModel(new SupportedLanguagesModelCompletionHandler() {
            @Override
            public void handle(LanguagesMap languagesMap) {

                if (languagesMap == null) {
                    completion.handle(null);
                }

                // Caching value
                self.languagesMap = languagesMap;

                // Returning list of languages
                List<String> languageList = new ArrayList(languagesMap.values());
                completion.handle(languageList);
            }
        });
    }

    public String keyForLanguage(String language) {
        return languagesMap.getKeyByValue(language);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //region Private

    interface SupportedLanguagesModelCompletionHandler {
        void handle(LanguagesMap languagesMap);
    }

    private void downloadSupportedLanguagesModel(final SupportedLanguagesModelCompletionHandler completion) {

        String apiKey = mContext.getString(R.string.api_key);
        ABApplication.getApi().getSupportedLanguages("en", apiKey).enqueue(
                new Callback<SupportedLanguagesModel>() {
                    @Override
                    public void onResponse(Call<SupportedLanguagesModel> call,
                                           Response<SupportedLanguagesModel> response) {


                        if (response.code() != 200 ) {
                            Log.d(TAG, "onResponse - Status : " + response.code());
                            if (response.errorBody() != null) {
                                Log.d(TAG, "onResponse - Status : "
                                        + response.errorBody().toString());
                            }

                            return;
                        }

                        SupportedLanguagesModel mSupportedLanguagesModel = response.body();
                        completion.handle(mSupportedLanguagesModel.getLangs());
                    }

                    @Override
                    public void onFailure(Call<SupportedLanguagesModel> call, Throwable t) {

                    }
                }
        );

    }

    //endregion

}
