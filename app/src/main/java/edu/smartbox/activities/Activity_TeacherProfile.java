package edu.smartbox.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;

/**
 * Created by Ankit on 28/01/17.
 */
public class Activity_TeacherProfile extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.txtName)
    TextView txtName;
    @Bind(R.id.txtClass)
    TextView txtClass;
    @Bind(R.id.txtSection)
    TextView txtSection;
    @Bind(R.id.txtSSN)
    TextView txtSsn;
    @Bind(R.id.txtPhone)
    TextView txtPhone;
    SharedPreferences pref;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");
        pref = getApplication().getSharedPreferences("Options", MODE_PRIVATE);
        String tname = pref.getString("tname", "");
        String tmobile = pref.getString("tmobile", "");
        String tclass = pref.getString("tclass", "");
        String tsec = pref.getString("tsec", "");
        String tssn = pref.getString("tssnno", "");
        txtName.setText(tname);
        txtPhone.setText(tmobile);
        txtClass.setText(tclass);
        txtSection.setText(tsec);
        txtSsn.setText(tssn);

    }
}
