package edu.smartbox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;
import edu.smartbox.fragments.AttendanceFragment;
import edu.smartbox.fragments.Teachers_HomeFragment;
import edu.smartbox.global.AppConfig;
import edu.smartbox.ui.SnackBar;

/**
 * Created by Ankit on 21/01/17.
 */
public class Activity_TeachersDashboard extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.dashboard);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        Teachers_HomeFragment teachers_homeFragment = new Teachers_HomeFragment();
        fragmentTransaction.replace(R.id.frame, teachers_homeFragment);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle((R.string.dashboard));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                switch (item.getItemId()) {
                    case R.id.action_home:
                        Teachers_HomeFragment teachers_homeFragment = new Teachers_HomeFragment();
                        fragmentTransaction.replace(R.id.frame, teachers_homeFragment);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle((R.string.dashboard));
                        return true;

                    case R.id.action_marks:
                        Toast.makeText(Activity_TeachersDashboard.this, "Under Maintainance", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_attendance:
                        AttendanceFragment attendance = new AttendanceFragment();
                        fragmentTransaction.replace(R.id.frame, attendance);
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle((R.string.attendance));
                        return true;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.profilebar:
                Intent in = new Intent(Activity_TeachersDashboard.this, Activity_TeacherProfile.class);
                startActivity(in);

                return true;
            case R.id.timetablebar:
                Intent i = new Intent(Activity_TeachersDashboard.this, Teachers_TimetableActivty.class);
                startActivity(i);
                return true;

            case R.id.logoutbar:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void logout() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(Activity_TeachersDashboard.this);
        final MaterialDialog dialog = builder.build();
        builder.title(R.string.logout).content(R.string.logout_message).positiveText(R.string.logout).negativeText(R.string.cancel).typeface("robo_font_bold.otf", "robo_font.otf");
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                dialog.dismiss();
                try {

                    AppConfig.logoutteacher(Activity_TeachersDashboard.this);
                    Intent i = new Intent(Activity_TeachersDashboard.this, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);

                } catch (Exception e) {
                    SnackBar.show(Activity_TeachersDashboard.this, e.toString());
                    e.printStackTrace();
                }
            }
        });
        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}
