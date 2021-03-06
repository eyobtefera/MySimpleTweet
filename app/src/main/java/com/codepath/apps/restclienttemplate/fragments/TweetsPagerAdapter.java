package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by eyobtefera on 7/3/17.
 */

public class TweetsPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private MentionsTimelineFragment mtf;
    private HomeTimelineFragment htf;

    private String tableTitle[] = new String[] {"Home", "Mentions"};
    // return the total # of fragment

    public TweetsPagerAdapter(FragmentManager fm, Context Context){
        super(fm);
        mtf = new MentionsTimelineFragment();
        htf = new HomeTimelineFragment();
        this.context = context;
    }
    @Override
    public int getCount() {
        return 2;
    }

    //  return the fragment to use depending on the position

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return htf;
        } else if (position == 1) {
            return mtf;
        } else {
            return null;
        }
     }

    @Override
    public CharSequence getPageTitle(int position) {
        // generate title based on item position
        return tableTitle[position];
    }
    // return title


}
