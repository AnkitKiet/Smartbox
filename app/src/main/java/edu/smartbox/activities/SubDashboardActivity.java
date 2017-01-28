package edu.smartbox.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;
import edu.smartbox.fragments.TimetableFragment;
import edu.smartbox.fragments.ViewAssignmentFragment;
import edu.smartbox.fragments.ViewAssignmentPicFragment;
import edu.smartbox.fragments.ViewNoticeFragment;

/**
 * Created by Ankit on 08/01/17.
 */
public class SubDashboardActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    String position;
    @Bind(R.id.title)
    TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subdashboard);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        position = extras.getString("content");
        //Toast.makeText(SubDashboardActivity.this, position, Toast.LENGTH_SHORT).show();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (position) {
            case "two":
                title.setText("Look Your Timetable");
                TimetableFragment timetable = new TimetableFragment();
                fragmentTransaction.replace(R.id.frame, timetable);
                fragmentTransaction.commit();
                getSupportActionBar().setTitle((R.string.timetable));
                break;
            case "one":
                title.setText("Look Notices");
                ViewNoticeFragment noticeFragment = new ViewNoticeFragment();
                fragmentTransaction.replace(R.id.frame, noticeFragment);
                fragmentTransaction.commit();

                getSupportActionBar().setTitle((R.string.notice));
                break;
            case "four":
                title.setText("Look Your Assignment");
                ViewAssignmentFragment assignment = new ViewAssignmentFragment();
                fragmentTransaction.replace(R.id.frame, assignment);
                fragmentTransaction.commit();
                getSupportActionBar().setTitle((R.string.assignment));
                break;

            case "three":
                title.setText("Look Your Assignment");
                ViewAssignmentPicFragment assignmen = new ViewAssignmentPicFragment();
                fragmentTransaction.replace(R.id.frame, assignmen);
                fragmentTransaction.commit();
                getSupportActionBar().setTitle((R.string.assignment));
                break;

            default:
                Toast.makeText(SubDashboardActivity.this, "sorry", Toast.LENGTH_SHORT).show();
        }

    }
}
