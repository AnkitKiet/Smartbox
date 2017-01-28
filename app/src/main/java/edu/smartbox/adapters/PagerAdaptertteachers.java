package edu.smartbox.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.smartbox.fragments.Teachers_TimetableFragment;

/**
 * Created by Ankit on 22/01/17.
 */
public class PagerAdaptertteachers extends FragmentStatePagerAdapter {

    public PagerAdaptertteachers(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Teachers_TimetableFragment();
                break;
            case 1:
                fragment = new Teachers_TimetableFragment();
                break;
            case 2:
                fragment = new Teachers_TimetableFragment();
                break;
            case 3:
                ;
                fragment = new Teachers_TimetableFragment();
                break;


            case 4:
                fragment = new Teachers_TimetableFragment();
                break;
            case 5:
                fragment = new Teachers_TimetableFragment();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "MONDAY";
            case 1:
                return "TUESDAY";
            case 2:
                return "WEDNESDAY";
            case 3:
                return "THURSDAY";
            case 4:
                return "FRIDAY";
            case 5:
                return "SATURDAY";


            default:
                return null;
        }
    }


}
