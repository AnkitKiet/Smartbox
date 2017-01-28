package edu.smartbox.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;

/**
 * Created by Ankit on 04/01/17.
 */
public class ProfileActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.expandedImage)
    ImageView img;
    SharedPreferences pref;
    String photo;
    @Bind(R.id.txtAdd)
    TextView txtAdd;
    @Bind(R.id.txtSname)
    TextView txtSname;
    @Bind(R.id.txtSage)
    TextView txtSage;
    @Bind(R.id.txtSgen)
    TextView txtSgen;
    @Bind(R.id.txtSclass)
    TextView txtSclass;
    @Bind(R.id.txtSlib)
    TextView txtSlib;
    @Bind(R.id.txtSsec)
    TextView txtSsec;
    @Bind(R.id.txtPemail)
    TextView txtPemail;
    @Bind(R.id.txtPname)
    TextView txtPname;
    @Bind(R.id.txtSphn)
    TextView sphone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.profile);
        pref = getApplication().getSharedPreferences("Options", MODE_PRIVATE);
        photo = pref.getString("profile_photo", "http://tr3.cbsistatic.com/fly/bundles/techrepubliccore/images/icons/standard/icon-user-default.png");
        String sname = pref.getString("sname", "");
        String sage = pref.getString("sage", "");
        String sclass = pref.getString("sclass", "");
        String ssec = pref.getString("ssec", "");
        String sgender = pref.getString("sgen", "");
        String pname = pref.getString("pname", "");
        String pmobile = pref.getString("pmobile", "");
        String pemail= pref.getString("pemail", "");
        String address = pref.getString("address", "");
        String libid = pref.getString("libid", "");
        Picasso.with(ProfileActivity.this).load(photo).into(img);
        txtAdd.setText(address);
        txtPemail.setText(pemail);
        txtPname.setText(pname);
        txtSage.setText(sage);
        txtSclass.setText(sclass);
        txtSlib.setText(libid);
        txtSsec.setText(ssec);
        sphone.setText(pmobile);
        if(sgender.equalsIgnoreCase("M")){
            txtSgen.setText("MALE");
        }
        else {
            txtSgen.setText("Female");

        }
        txtSname.setText(sname);
        getSupportActionBar().setTitle(sname);

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
