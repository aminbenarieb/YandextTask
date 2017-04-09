package com.aminbenarieb.yandextask;

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
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity   {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigation;
    private Fragment mHomeTranslateFragment = new HomeTranslateFragment();
    private Fragment mBookmarksFragment = new HistoryListFragment();
    private FragmentManager fragmentManager;
    private ActionBar actionBar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting up bottom navigation
        bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Init fragment manager
        fragmentManager = getSupportFragmentManager();

        // Set initial fragment
        setContentFragment(mHomeTranslateFragment);

        // Setup action bar
        setupActionBar();

        // Set language switcher to action bar
        setupLanguageSwitcher();

    }

    void setContentFragment(Fragment fragmentReplaceWith) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragmentReplaceWith).commit();
    }

    // Setup action bar
    void setupActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
    }

    // Language switcher in action bar
    void setupLanguageSwitcher() {
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

    void setupTabPager() {
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

}
