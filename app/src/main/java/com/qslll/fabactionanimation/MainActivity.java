package com.qslll.fabactionanimation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qslll.fabactionanimation.fragments.JumpFragment;
import com.qslll.fabactionanimation.fragments.ShakeFragment;
import com.qslll.fabactionanimation.fragments.SimpleBarFragment;
import com.qslll.library.fabs.QsJumpFab;
import com.qslll.library.fabs.QsShakeFab;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            getSupportActionBar().setElevation(0);
        }
        TabLayout tabLayout = new TabLayout(this);
        if (tabLayout != null && getSupportActionBar() != null) {
            android.support.v7.app.ActionBar.LayoutParams layoutParams = new android.support.v7.app.ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
            getSupportActionBar().setCustomView(tabLayout, layoutParams);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        List<Fragment> fragments = new ArrayList<>();

        Bundle barBundle = new Bundle();
        barBundle.putString("title", "bar");
        SimpleBarFragment barFragment = new SimpleBarFragment();
        barFragment.setArguments(barBundle);

        Bundle shakeBundle = new Bundle();
        shakeBundle.putString("title", "shake");
        ShakeFragment shakeFragment = new ShakeFragment();
        shakeFragment.setArguments(shakeBundle);

        Bundle jumpBundle = new Bundle();
        jumpBundle.putString("title", "jump");
        JumpFragment jumpFragment = new JumpFragment();
        jumpFragment.setArguments(jumpBundle);

        fragments.add(barFragment);
        fragments.add(shakeFragment);
        fragments.add(jumpFragment);

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragments));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(viewPager.getAdapter());

    }
}
