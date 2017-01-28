package edu.smartbox.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;

/**
 * Created by Ankit on 22/01/17.
 */
public class Teachers_TimetableFragment  extends android.support.v4.app.Fragment {

    private View parentView;

    public Teachers_TimetableFragment(){


    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView=inflater.inflate(R.layout.teachers_timetable_tabs, container, false);
        ButterKnife.bind(this,parentView);
        return parentView;
    }

}