package com.example.appchat.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appchat.Adapter.ViewPagerFriend;
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

        ViewPager viewPagerFriend = view.findViewById(R.id.viewPagerFriend);
        TabLayout tabLayoutFriend = view.findViewById(R.id.tabLayoutFriend);

        viewPagerFriend.setAdapter(new ViewPagerFriend(getChildFragmentManager(), this.getContext()));
        tabLayoutFriend.setupWithViewPager(viewPagerFriend);


        return view;
    }

}
