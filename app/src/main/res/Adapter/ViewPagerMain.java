package com.example.appchat.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.appchat.Fragment.FriendFragment;
import com.example.appchat.Fragment.MessageFragment;
import com.example.appchat.Fragment.ProfileFragment;

public class ViewPagerMain extends FragmentPagerAdapter {



    public ViewPagerMain(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new MessageFragment(); //ChildFragment1 at position 0
            case 1:
                return new FriendFragment(); //ChildFragment2 at position 1
            case 2:
                return new ProfileFragment(); //ChildFragment3 at position 2
        }
        return null; //does not happen
    }

    @Override
    public int getCount() {
        return 3; //three fragments
    }

}

