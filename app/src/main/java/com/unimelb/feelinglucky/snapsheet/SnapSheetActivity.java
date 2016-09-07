package com.unimelb.feelinglucky.snapsheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
//import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.unimelb.feelinglucky.snapsheet.Camera.CameraFragment;
import com.unimelb.feelinglucky.snapsheet.Camera.CameraPageViewerFragment;
import com.unimelb.feelinglucky.snapsheet.Chat.ChatFragment;
import com.unimelb.feelinglucky.snapsheet.Chatroom.ChatRoomFragment;
import com.unimelb.feelinglucky.snapsheet.Discover.DiscoverFragment;
import com.unimelb.feelinglucky.snapsheet.Story.StoryFragment;
import com.unimelb.feelinglucky.snapsheet.View.CustomizedViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leveyleonhardt on 8/11/16.
 */
public class SnapSheetActivity extends AppCompatActivity {

    private CustomizedViewPager mViewPager;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        mViewPager = (CustomizedViewPager) findViewById(R.id.activity_fragment_view_pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        loadFragments();
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        mViewPager.setCurrentItem(2);
    }

    private void loadFragments() {
        if (fragments.size() == 0) {
            fragments.add(new ChatRoomFragment());
            fragments.add(new ChatFragment());
            fragments.add(new CameraPageViewerFragment());
            fragments.add(new StoryFragment());
            fragments.add(new DiscoverFragment());
        }
    }

}
