package com.aminbenarieb.yandextask.History;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aminbenarieb.yandextask.R;

/**
 * Created by aminbenarieb on 4/6/17.
 */

public class HomeTranslateFragment extends Fragment {
    private View mContent;
    private TextView mTranslateSourceTextView;
    private TextView mTranslateResultTextView;


    public HomeTranslateFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    //region Setup subroutines
    void setupViews(View view) {
        mContent = view.findViewById(R.id.translate_view);
        mTranslateSourceTextView = (TextView) view.findViewById(R.id.translate_source);
        mTranslateResultTextView = (TextView) view.findViewById(R.id.translate_result);
    }

    void setupViewsAppearance() {

    }

    void setupLayout() {

    }
    //endregion

    void populateViews() {
    }

}
