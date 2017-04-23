package com.aminbenarieb.yandextask.Screens.HomeTranslate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aminbenarieb.yandextask.Extensions.Dynamic;
import com.aminbenarieb.yandextask.R;
import com.aminbenarieb.yandextask.Services.Language.ABLanguage;
import com.aminbenarieb.yandextask.Services.Repository.ABRepository;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by aminbenarieb on 4/15/17.
 */

public class ABHomeTranslateFragment extends Fragment implements HomeTranslateFragment {
    private View mContent;
    private TextView mSourceEditText;
    private TextView mResultEditText;
    private String mResultLanguage;


    // Finish textview edition timer
    private Timer timer = new Timer();
    private final long DELAY = 1000;

    public HomeTranslateViewModel viewModel;

    public void setResultLanguage(String language) {
        this.mResultLanguage = language;
    }

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
        setupListeners();
        setupBinding();

        viewModel = new ABHomeTranslateViewModel(
                new ABHomeTranslateModel(
                        getActivity(),
                        new ABRepository(getActivity()),
                        ABLanguage.INSTANCE
                ));

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

    void setupBinding() {
        viewModel.mTranslatedWord.bindAndFire(new Dynamic.Listener() {
            @Override
            public void onResponse(Object value) {
                if (value == null)
                    return;

                mResultEditText.setText((String)value);
            }
        });
    }

    //endregion

    private void setupListeners() {
        mSourceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable s) {
                onTextChangedAction(s.toString());
            }
        });
    }

    //region Actions

    private void onTextChangedAction(final String word) {

        if (word.length() < 2)
            return;

        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                viewModel.translateWord(word, mResultLanguage,
                        new HomeTranslateViewModel.HomeTranslateCompetionHandler() {
                            @Override
                            public void handle(Throwable t) {
                                if (t != null) {
                                    String msg = t.getLocalizedMessage();
                                    Toast.makeText(getActivity().getBaseContext(), msg, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }

        }, DELAY);

    }

    void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mSourceEditText, InputMethodManager.SHOW_IMPLICIT);
    }

    //endregion

}
