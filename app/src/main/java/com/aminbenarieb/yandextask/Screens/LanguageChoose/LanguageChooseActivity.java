package com.aminbenarieb.yandextask.Screens.LanguageChoose;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.aminbenarieb.yandextask.R;

import java.util.List;

/**
 * Created by aminbenarieb on 4/9/17.
 */

public class LanguageChooseActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LanguageChoose mAdapter;
    private List<String> mLanguageList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_language_recycleview);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_language_chooser);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mLanguageList = getIntent().getStringArrayListExtra("LANGUAGES");
        if (mAdapter == null)
        {
            mAdapter = new LanguageChoose(mLanguageList, this);
            mRecyclerView.setAdapter(mAdapter);
            return;
        }

        mAdapter.notifyDataSetChanged();
    }
}
