package com.aminbenarieb.yandextask.Screens.Main;

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

    private FragmentManager fragmentManager;
    private MainActivityViewModel viewModel = new ABMainActivityViewModel(
            new ABMainActivityModel(
                    ABLanguage.INSTANCE
            )
    );

    private Fragment mBookmarksFragment;
    private HomeTranslateFragment mHomeTranslateFragment;
    private Intent mLanguageChooseIntent;

    private ImageButton mButtonSwapLanguages;
    private Button mButtonChooseSourceLanguage;
    private Button mButtonChooseResultLanguage;


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
        setupButtonLanguages();
        setupListeners();
        setupBinding();

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
                    fragment = (Fragment)mHomeTranslateFragment;
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
        actionBar.setCustomView(R.layout.view_language_chooser);
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
    }

    private void setupListeners() {
        mButtonSwapLanguages.setOnClickListener(this);
        mButtonChooseSourceLanguage.setOnClickListener(this);
        mButtonChooseResultLanguage.setOnClickListener(this);
    }

    private void setupBinding() {
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
