package com.kasai.stadium.tv.activity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kasai.stadium.tv.R;
import com.kasai.stadium.tv.adapter.SectionsPagerAdapter;
import com.kasai.stadium.tv.fragment.BaseFragment;
import com.kasai.stadium.tv.fragment.ImageFragment;
import com.kasai.stadium.tv.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, BaseFragment.FragmentChangeListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        viewPager = findViewById(R.id.view_pager);
        viewPager.addOnPageChangeListener(this);

        ImageFragment imageFragment = ImageFragment.newInstance();
        fragments.add(imageFragment);

        VideoFragment videoFragment = VideoFragment.newInstance();
        fragments.add(videoFragment);

        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(fragments.size());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        index = position;
        Log.e(TAG, "index : " + index);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onNext() {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        index++;
        if (index > fragments.size() - 1) {
            index = index % (fragments.size() - 1);
        }
        viewPager.setCurrentItem(index, false);
    }
}
