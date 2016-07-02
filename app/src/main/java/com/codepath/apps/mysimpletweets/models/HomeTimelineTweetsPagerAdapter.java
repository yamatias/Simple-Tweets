package com.codepath.apps.mysimpletweets.models;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.fragments.HomeTimelineFragment;
import com.codepath.apps.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.R;

public class HomeTimelineTweetsPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider{
    //return order of fragments in ViewPager
    private String tabTitles[] = {"Home", "Mentions"};
    private int tabIcons[] = {R.drawable.ic_home_icon, R.drawable.ic_mentions_icon};

    public HomeTimelineTweetsPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    //Associates a position with a Fragment
    @Override
    public Fragment getItem(int position) {
        //This returns the fragment at that position
        if (position == 0) {
            return new HomeTimelineFragment();
        } else if (position == 1) {
            return new MentionsTimelineFragment();
        } else {
            return null;
        }
    }

    //Returns the Tab Title
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


    //Tells how many fragments there are
    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public int getPageIconResId(int position) {
        return tabIcons[position];
    }
}
