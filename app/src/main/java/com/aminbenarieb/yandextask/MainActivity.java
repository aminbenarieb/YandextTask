package com.aminbenarieb.yandextask;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView bottomNavigation;
    private Fragment mHomeTranslateFragment = new HomeTranslateFragment();
    private Fragment mBookmarksFragment = new BookmarksFragment();
    private FragmentManager fragmentManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            Fragment fragment = null;
            switch (id) {
                case R.id.navigation_home:
                    fragment = mHomeTranslateFragment;
                    break;
                case R.id.navigation_dashboard:
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

    }

    void setContentFragment(Fragment fragmentReplaceWith) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragmentReplaceWith).commit();
    }
}
