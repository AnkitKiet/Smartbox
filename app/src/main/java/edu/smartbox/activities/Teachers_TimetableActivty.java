package edu.smartbox.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import edu.smartbox.R;
import edu.smartbox.adapters.PagerAdaptertteachers;

/**
 * Created by Ankit on 22/01/17.
 */
public class Teachers_TimetableActivty extends AppCompatActivity {

    Toolbar t;
    TabLayout tl;
    ViewPager v;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teachers_timetable);

        t=(Toolbar) findViewById(R.id.toolbar);
        tl=(TabLayout)findViewById(R.id.tabLayout);
        v=(ViewPager)findViewById(R.id.viewpager);



        setSupportActionBar(t);

        PagerAdaptertteachers pagerAdapter = new PagerAdaptertteachers(getSupportFragmentManager());
        v.setAdapter(pagerAdapter);
        tl.setupWithViewPager(v);
        tl.setTabMode(TabLayout.MODE_SCROLLABLE);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("TIMETABLE");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }



}

