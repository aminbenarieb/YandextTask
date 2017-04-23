package com.aminbenarieb.yandextask.Screens.Main;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.aminbenarieb.yandextask.Extensions.Dynamic;
import com.aminbenarieb.yandextask.R;
import com.aminbenarieb.yandextask.Screens.History.HistoryListFragment;
import com.aminbenarieb.yandextask.Screens.HomeTranslate.ABHomeTranslateFragment;
import com.aminbenarieb.yandextask.Screens.HomeTranslate.HomeTranslateFragment;
import com.aminbenarieb.yandextask.Screens.LanguageChoose.LanguageChooseActivity;
import com.aminbenarieb.yandextask.Services.Language.ABLanguage;


public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private BottomNavigationView bottomNavigation;
    private ActionBar actionBar;
    private int mLanguageSwitcher = R.layout.view_language_chooser;
    private int mTabPager = R.layout.view_history_bookmarks_tab;
    private FragmentManager fragmentManager;

    private HistoryListFragment mBookmarksFragment;
    private HomeTranslateFragment mHomeTranslateFragment;
    private Intent mLanguageChooseIntent;

    private ImageButton mButtonSwapLanguages;
    private Button mButtonChooseSourceLanguage;
    private Button mButtonChooseResultLanguage;

    private MainActivityViewModel viewModel = new ABMainActivityViewModel(
            new ABMainActivityModel(
                    ABLanguage.INSTANCE
            )
    );
    private Boolean schouldShowClearHistoryButton = false;

    //region LanguageChooseActivity Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Setting up
        setContentView(R.layout.activity_main);

        bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentManager = getSupportFragmentManager();

        setupActionBar();
        setupFragments();
        setupLanguageSwitcher();

        // Setup context
        ABLanguage.INSTANCE.setContext(MainActivity.this);

        // Initial fragment
        setContentFragment((Fragment) mHomeTranslateFragment);

        // Load languages
        viewModel.loadLanguages(new MainActivityViewModel.LoadLanguagersCompetionHandler() {
            @Override
            public void handle(List<String> languagesList, Throwable t) {
                if (t != null) {
                    String msg = t.getLocalizedMessage();
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
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
                    hideHistoryCleanButton();
                    fragment = (Fragment)mHomeTranslateFragment;
                    break;
                case R.id.navigation_dashboard:
                    setupTabPager();
                    showHistoryCleanButton();
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

    //region History Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (schouldShowClearHistoryButton) {


            Drawable icon = ContextCompat.getDrawable(this, R.drawable.delete);
            icon.setTint(Color.WHITE);
            menu.add(0, 0, 0, R.string.history_clear).setIcon(icon)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selectedItem = item.getItemId();

        switch (selectedItem) {
            case 0:
                mBookmarksFragment.didTapOnCleanHistoryButton();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideHistoryCleanButton() {
        updateHistoryCleanButtonState(false);
    }
    private void showHistoryCleanButton() {
        updateHistoryCleanButtonState(true);
    }
    private void updateHistoryCleanButtonState(Boolean schouldShow) {
        schouldShowClearHistoryButton = schouldShow;
        invalidateOptionsMenu();
    }

    //

    //region Setup subroutines

    private void setupFragments() {
        mBookmarksFragment = new HistoryListFragment();
        mHomeTranslateFragment = new ABHomeTranslateFragment();
        mLanguageChooseIntent = new Intent(MainActivity.this,
                LanguageChooseActivity.class);
    }

    private void setupActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
    }

    private void setupLanguageSwitcher() {
        actionBar.setCustomView(mLanguageSwitcher);
        setupButtonLanguages();
        setupButtonLanguagesBinding();
        setupButtonLanguagesListeners();
    }


    private void setupButtonLanguages() {
        mButtonChooseSourceLanguage = (Button) findViewById(R.id.source_language);
        mButtonSwapLanguages = (ImageButton) findViewById(R.id.language_swap);
        mButtonChooseResultLanguage = (Button) findViewById(R.id.result_language);
    }

    private void setupButtonLanguagesListeners() {
        mButtonSwapLanguages.setOnClickListener(this);
        mButtonChooseSourceLanguage.setOnClickListener(this);
        mButtonChooseResultLanguage.setOnClickListener(this);
    }

    private void setupButtonLanguagesBinding() {
        viewModel.mLanguagesList.bindAndFire(new Dynamic.Listener() {
            @Override
            public void onResponse(Object value) {


            }
        });

        viewModel.mSourceLanguage.bindAndFire(new Dynamic.Listener() {
            @Override
            public void onResponse(Object value) {
                mButtonChooseSourceLanguage.setText((String)value);
            }
        });

        viewModel.mResultLanguage.bindAndFire(new Dynamic.Listener() {
            @Override
            public void onResponse(Object value) {
                mButtonChooseResultLanguage.setText((String)value);
                mHomeTranslateFragment.setResultLanguage( (String)value );
            }
        });
    }



    private void setupTabPager() {
        actionBar.setCustomView(mTabPager);

        View v = actionBar.getCustomView();
        final TabLayout tabLayout = (TabLayout)v.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_history));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_bookmarks));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tabLayout.getSelectedTabPosition()) {
                    case 0:
                        mBookmarksFragment.didTapOnHistoryPage();
                        break;
                    case 1:
                        mBookmarksFragment.didTapOnBookmarksPage();
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
    //endregion

    //region Actions

    @Override
    public void onClick(View view) {
        int clickedButtonId = view.getId();
        switch (clickedButtonId)
        {
            case R.id.source_language:
                showLanguageChooseActivity(0);
                break;
            case R.id.language_swap:
                viewModel.swapLanguages();
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
                viewModel.setSourceLanguage(selectedLanguage);
                break;
            case 1:
                viewModel.setResultLanguage(selectedLanguage);
                break;
        }
    }

    private void showLanguageChooseActivity(int actionIdx) {
        mLanguageChooseIntent.putStringArrayListExtra("LANGUAGES",
                (ArrayList<String>)viewModel.mLanguagesList.getValue());
        startActivityForResult(mLanguageChooseIntent, actionIdx);
    }


    //endregion

}
