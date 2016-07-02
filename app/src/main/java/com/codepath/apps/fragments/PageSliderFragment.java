package com.codepath.apps.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.HomeTimelineTweetsPagerAdapter;

public class PageSliderFragment extends Fragment {
    ViewPager viewPager;
    PagerSlidingTabStrip ps;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_slider,container,false);

        viewPager = (ViewPager)view.findViewById(R.id.viewpager);

        final HomeTimelineTweetsPagerAdapter httpa = new HomeTimelineTweetsPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(httpa);
        Log.d("debug","The viewpager here is " + viewPager);
        //set viewpager adapter to viewpager
        ps = (PagerSlidingTabStrip)view.findViewById(R.id.tabs);
        ps.setDividerColor(Color.WHITE);
        ps.setIndicatorColorResource(R.color.twitterblue);
        ps.setUnderlineColor(Color.WHITE);
        //Find pager sliding tab strip
        //Attach tab strip to viewpager
        ps.setViewPager(viewPager);

        ps.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TextView tvTitle = (TextView)getActivity().findViewById(R.id.tvTitle);
                tvTitle.setText(httpa.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }
}
