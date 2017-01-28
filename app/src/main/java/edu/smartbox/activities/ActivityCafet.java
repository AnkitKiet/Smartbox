package edu.smartbox.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;

/**
 * Created by Ankit on 25/01/17.
 */
public class ActivityCafet extends AppCompatActivity {
        @Bind(R.id.toolbar)
        Toolbar t;
        @Bind(R.id.bottomview)
        ImageView i;
        @Bind(R.id.bottom_navigation)
        BottomNavigationView bottomNavigationView;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_stucafet);
            ButterKnife.bind(this);
            setSupportActionBar(t);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Cafet");




            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {

                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                            switch (item.getItemId()) {

                                case R.id.action_canteen:
                                    Picasso.with(ActivityCafet.this).load("http://postermywall.com.s3.amazonaws.com/posterpreviews/old-vintage-restaurant-food-menu-poster-template-9d6679a92fde370444cc27a7c8fd5a23_screen.jpg?ts=1455836137").into(i);
                                    return true;

                                case R.id.action_mess:
                                    Picasso.with(ActivityCafet.this).load("http://www.aisv.lt/uploads/PRE-K%20menu%20(1).png").into(i);
                                    return true;
                                case R.id.action_fastfood:
                                    Picasso.with(ActivityCafet.this).load("http://www.signagecreator.com/assets/template/admin/2484.jpg").into(i);
                                    return true;
                            }
                            return true;
                        }
                    });

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);



        }

    }
