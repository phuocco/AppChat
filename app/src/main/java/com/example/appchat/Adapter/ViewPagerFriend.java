package com.example.appchat.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.appchat.Fragment.Friend.FindFriendFragment;
import com.example.appchat.Fragment.Friend.FriendListFragment;
import com.example.appchat.R;

public class ViewPagerFriend extends FragmentPagerAdapter {
    Context mContext;

    public ViewPagerFriend(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new FriendListFragment(); //ChildFragment1 at position 0
            case 1:
                return new FindFriendFragment(); //ChildFragment2 at position 1
        }
        return null; //does not happen
    }

    @Override
    public int getCount() {
        return 2; //two fragments
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.find_friend);
            case 1:
                return mContext.getString(R.string.friend_list);
            default:
                return null;
        }
    }
}
