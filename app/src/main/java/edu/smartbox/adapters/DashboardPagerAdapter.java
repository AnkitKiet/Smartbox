package edu.smartbox.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.smartbox.fragments.AttendanceFragment;
import edu.smartbox.fragments.HomeFragment;
import edu.smartbox.fragments.MarksFragment;
import edu.smartbox.fragments.ViewAttendanceFragment;

/**
 * Created by Ankit on 04/01/17.
 */
public class DashboardPagerAdapter extends FragmentStatePagerAdapter {
    public DashboardPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new MarksFragment();
                break;
            case 2:
                fragment = new ViewAttendanceFragment();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Home";
            case 1:
                return "Marks";
            case 2:
                return "Attendance";

            default:
                return null;
        }
    }


}

