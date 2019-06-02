package com.example.appchat.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.appchat.Fragment.Friend.FindFriendFragment;
import com.example.appchat.Fragment.Friend.FriendListFragment;
import com.example.appchat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends Fragment {


    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend, container, false);

        final TabLayout tabLayoutFriend = view.findViewById(R.id.tabLayoutFriend);
        FrameLayout frameLayoutFriend = view.findViewById(R.id.frameLayoutFriend);

        tabLayoutFriend.addTab(tabLayoutFriend.newTab().setText("Find friend"));
        tabLayoutFriend.addTab(tabLayoutFriend.newTab().setText("List friend"));
        tabLayoutFriend.setTabGravity(TabLayout.GRAVITY_FILL);

        Fragment defaultFragment = new FindFriendFragment();
        setFragment(defaultFragment);


        tabLayoutFriend.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tabLayoutFriend.getSelectedTabPosition() == 0){
                    Fragment findFriend = new FindFriendFragment();
                    setFragment(findFriend);
                }
                if (tabLayoutFriend.getSelectedTabPosition() == 1) {
                    Fragment friendList = new FriendListFragment();
                    setFragment(friendList);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }

    public void setFragment(Fragment mFragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutFriend, mFragment);
        fragmentTransaction.commit();
    }

}
