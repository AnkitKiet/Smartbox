package edu.smartbox.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;
import edu.smartbox.adapters.DashboardPagerAdapter;
import edu.smartbox.global.AppConfig;
import edu.smartbox.ui.CustomTypeFace;
import edu.smartbox.ui.SnackBar;

/**
 * Created by Ankit on 04/01/17.
 */
public class DashboardActivity extends AppCompatActivity {
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.drawer)
    DrawerLayout drawerLayout;

    private MenuItem previousMenuItem;
    private View header;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        populate();

    }

    private void populate() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        //nav
        overridePendingTransition(0, 0);
        header = navigationView.getHeaderView(0);
        //TextView txtWelcome = (TextView) header.findViewById(R.id.txtWelcome);
        TextView txtName = (TextView) header.findViewById(R.id.txtName);
        Typeface typeface = CustomTypeFace.getTypeface(this);
        // txtWelcome.setTypeface(typeface);
        txtName.setTypeface(typeface);
        txtName.setText("SmartBox");
        //Picasso.with(DashboardActivity.this).load("http://tr3.cbsistatic.com/fly/bundles/techrepubliccore/images/icons/standard/icon-user-default.png").into(profilePhoto);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (previousMenuItem != null)
                    previousMenuItem.setChecked(false);

                menuItem.setCheckable(true);
                menuItem.setChecked(true);

                previousMenuItem = menuItem;

                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.profile:
                        Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.calender:
                        Intent in = new Intent(DashboardActivity.this, AcademicCalenderActivity.class);
                        startActivity(in);
                        return true;


                    case R.id.complain:
                        Intent i = new Intent(DashboardActivity.this, ComplainActivity.class);
                        startActivity(i);
                        return true;

                    case R.id.rateus:
                        return true;
                    case R.id.contact_us:
                        Intent inte = new Intent(DashboardActivity.this, Contactus.class);
                        startActivity(inte);
                        return true;
                    case R.id.privacy:
                        Intent i1 = new Intent(DashboardActivity.this, PrivacyPolicyActivity.class);
                        startActivity(i1);

                        return true;

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        //tab
        DashboardPagerAdapter pagerAdapter = new DashboardPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menulog) {

            logout();

        }


        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(DashboardActivity.this);
        final MaterialDialog dialog = builder.build();
        builder.title(R.string.logout).content(R.string.logout_message).positiveText(R.string.logout).negativeText(R.string.cancel).typeface("robo_font_bold.otf", "robo_font.otf");
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                dialog.dismiss();
                try {

                    AppConfig.logout(DashboardActivity.this);
                    Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);

                } catch (Exception e) {
                    SnackBar.show(DashboardActivity.this, e.toString());
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
