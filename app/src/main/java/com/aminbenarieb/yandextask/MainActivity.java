package com.aminbenarieb.yandextask;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Arrays;

import com.aminbenarieb.yandextask.UI.History.HistoryListFragment;
import com.aminbenarieb.yandextask.UI.HomeTranslateFragment;

/**
 * Created by aminbenarieb on 4/6/17.
 */


public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigation;
    private Fragment mHomeTranslateFragment = new HomeTranslateFragment();
    private ImageButton mButtonSwapLanguages;
    private Button mButtonChooseSourceLanguage;
    private Button mButtonChooseResultLanguage;
    private Fragment mBookmarksFragment = new HistoryListFragment();
    private FragmentManager fragmentManager;
    private ActionBar actionBar;

    //region Temp
    //TODO Put in model layer
    private ArrayList<String> getTempLangs() {
        return new ArrayList<String>(Arrays.asList(
                "Русский",
                "English",
                "العربية",
                "Deutch"
        ));
    }

    //endregion

    //region Activity Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();

        setContentFragment(mHomeTranslateFragment);

        setupActionBar();
        setupLanguageSwitcher();
        setupButtonLanguages();
        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    //endregion

    //region BottomNavigation Selection Listener
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            Fragment fragment = null;
            switch (id) {
                case R.id.navigation_home:
                    setupLanguageSwitcher();
                    fragment = mHomeTranslateFragment;
                    break;
                case R.id.navigation_dashboard:
                    setupTabPager();
                    fragment = mBookmarksFragment;
                    break;
            }

            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content, fragment).commit();
            return true;
        }

    };

    void setContentFragment(Fragment fragmentReplaceWith) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragmentReplaceWith).commit();
    }

    //endregion

    //region Setup subroutines

    private void setupActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
    }

    private void setupLanguageSwitcher() {
        actionBar.setCustomView(R.layout.view_language_chooser);

        View v = actionBar.getCustomView();
        ImageButton imageButton= (ImageButton)v.findViewById(R.id.language_swap);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Switched lang.");
            }
        });
    }

    private void setupTabPager() {
        actionBar.setCustomView(R.layout.view_history_bookmarks_tab);

        View v = actionBar.getCustomView();
        final TabLayout tabLayout = (TabLayout)v.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_history));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_bookmarks));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

             @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Log.i("TAG", "onTabReselected: " + tab.getPosition());

                switch (tabLayout.getSelectedTabPosition()) {
                    case 0:
                        break;
                    case 1:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setupButtonLanguages() {
        mButtonChooseSourceLanguage = (Button) findViewById(R.id.source_language);
        mButtonSwapLanguages = (ImageButton) findViewById(R.id.language_swap);
        mButtonChooseResultLanguage = (Button) findViewById(R.id.result_language);

        ArrayList<String> langs = getTempLangs();
        mButtonChooseSourceLanguage.setText(langs.get(0));
        mButtonChooseResultLanguage.setText(langs.get(1));
    }

    private void setupListeners() {
        mButtonSwapLanguages.setOnClickListener(this);
        mButtonChooseSourceLanguage.setOnClickListener(this);
        mButtonChooseResultLanguage.setOnClickListener(this);
    }

    //endregion

    //region Actions

    void showLanguageChooseActivity(int actionIdx) {
        Intent intent = new Intent(MainActivity.this, com.aminbenarieb.yandextask.UI.LanguageChoose.Activity.class);
        intent.putStringArrayListExtra("LANGUAGES", getTempLangs());
        startActivityForResult(intent, actionIdx);
    }

    void swapLanguages() {
        //TODO Put in model layer
        ArrayList<String> langs = getTempLangs();
        String sourceLang = mButtonChooseSourceLanguage.getText().toString();
        String resultLang = mButtonChooseResultLanguage.getText().toString();
        mButtonChooseSourceLanguage.setText(resultLang);
        mButtonChooseResultLanguage.setText(sourceLang);
    }

    @Override
    public void onClick(View view) {
        int clickedButtonId = view.getId();
        switch (clickedButtonId)
        {
            case R.id.source_language:
                showLanguageChooseActivity(0);
                break;
            case R.id.language_swap:
                swapLanguages();
                break;
            case R.id.result_language:
                showLanguageChooseActivity(1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Guard for data
        if (data == null) {
            return;
        }

        String selectedLanguage = data.getStringExtra("LANGUAGE");
        switch (requestCode)
        {
            case 0:
                updateLanguageButton(selectedLanguage, mButtonChooseSourceLanguage);
                break;
            case 1:
                updateLanguageButton(selectedLanguage, mButtonChooseResultLanguage);
                break;
        }
    }

    void updateLanguageButton(String selectedLanguage, Button buttonLanguage) {
        buttonLanguage = buttonLanguage == mButtonChooseSourceLanguage
                ? mButtonChooseResultLanguage
                : mButtonChooseSourceLanguage;

        if (buttonLanguage.getText().equals(selectedLanguage)) {
            swapLanguages();
            return;
        }

        buttonLanguage.setText(selectedLanguage);
    }

    //endregion

}
