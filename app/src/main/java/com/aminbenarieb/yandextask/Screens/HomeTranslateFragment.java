package com.aminbenarieb.yandextask.Screens;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aminbenarieb.yandextask.Extensions.ABApplication;
import com.aminbenarieb.yandextask.Model.TranslatedWordModel;
import com.aminbenarieb.yandextask.R;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aminbenarieb on 4/15/17.
 */

public class HomeTranslateFragment extends Fragment {
    private View mContent;
    private TextView mSourceEditText;
    private TextView mResultEditText;
    private String TAG = "HomeTranslateFragment";

    public HomeTranslateFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        mContent = inflater.inflate(R.layout.translate_view, container, false);
        return mContent;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViews(view);
        setupViewsAppearance();
        setupLayout();
        populateViews();
        setupListeners();
    }

    @Override
    public void onResume() {
        super.onResume();

        showKeyboard();
    }

    //region Setup subroutines
    void setupViews(View view) {
        mContent = view.findViewById(R.id.translate_view);
        mSourceEditText = (EditText) view.findViewById(R.id.translate_source);
        mResultEditText = (TextView) view.findViewById(R.id.translate_result);
    }

    void setupViewsAppearance() {

    }

    void setupLayout() {

    }
    //endregion

    void populateViews() {
    }

    private void setupListeners() {
        mSourceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextChangedAction(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //region Actions

    private void onTextChangedAction(String word) {
        //TODO: 1) save to history
        //TODO: 2) toggle hidden of result view

        String apiKey = getString(R.string.api_key);
        ABApplication.getApi().getTranslatedWord(apiKey, word, "en").enqueue(
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
                        mResultEditText.setText( translatedWords.substring(
                                1,
                                translatedWords.length() - 1)
                        );

                    }

                    @Override
                    public void onFailure(retrofit2.Call<TranslatedWordModel> call,
                                          Throwable t) {

                        Toast.makeText(getActivity(), "An error occurred during networking",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }


    void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mSourceEditText, InputMethodManager.SHOW_IMPLICIT);
    }

    //endregion

}
