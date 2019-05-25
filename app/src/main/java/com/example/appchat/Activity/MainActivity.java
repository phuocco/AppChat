package com.example.appchat.Activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.appchat.Adapter.ViewPagerMain;
import com.example.appchat.Fragment.FriendFragment;
import com.example.appchat.Fragment.MessageFragment;
import com.example.appchat.Fragment.ProfileFragment;
import com.example.appchat.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNav;
    private FrameLayout mMainFrame;
    private ViewPager viewPagerFragment;
    MenuItem menuItemBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNav = findViewById(R.id.bottom_nav);
        mMainFrame = findViewById(R.id.main_frame);
        viewPagerFragment = findViewById(R.id.viewPagerFragment);

        viewPagerFragment.setAdapter(new ViewPagerMain(getSupportFragmentManager()));

        Fragment messageFragment = new MessageFragment();
        setFragment(messageFragment);

        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_message:
                        Fragment messageFragment = new MessageFragment();
                        setFragment(messageFragment);
                        viewPagerFragment.setCurrentItem(0);
                        break;
                    case R.id.nav_friends:
                        Fragment friendFragment = new FriendFragment();
                        setFragment(friendFragment);
                        viewPagerFragment.setCurrentItem(1);
                        break;
                    case R.id.nav_profile:
                        Fragment profileFragment = new ProfileFragment();
                        setFragment(profileFragment);
                        viewPagerFragment.setCurrentItem(2);
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        viewPagerFragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItemBottomNav != null) {
                    menuItemBottomNav.setChecked(false);
                } else {
                    mBottomNav.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                mBottomNav.getMenu().getItem(position).setChecked(true);
                menuItemBottomNav = mBottomNav.getMenu().getItem(position);

            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void setFragment(Fragment mFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, mFragment);
        fragmentTransaction.commit();
    }
}
