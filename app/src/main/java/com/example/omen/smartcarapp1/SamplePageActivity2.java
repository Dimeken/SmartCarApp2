package com.example.omen.smartcarapp1;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.omen.smartcarapp1.AboutActivity;
import com.example.omen.smartcarapp1.AlertsActivity;
import com.example.omen.smartcarapp1.AutoHealthActivity;
import com.example.omen.smartcarapp1.CarLocationActivity;
import com.example.omen.smartcarapp1.DrivingHistoryActivity;
import com.example.omen.smartcarapp1.HelpAndSupportActivity;
import com.example.omen.smartcarapp1.MyAccountActivity;
import com.example.omen.smartcarapp1.R;
import com.example.omen.smartcarapp1.RemoteControlActivity;
import com.example.omen.smartcarapp1.SafetyScoreActivity;

public class SamplePageActivity2 extends AppCompatActivity
        implements View.OnClickListener {
    DrawerLayout mDrawerLayout;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_page2);

        btnBack = (ImageButton)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        //set item as selected to persist highlight
                        menuItem.setChecked(true);
                        //close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        //Add code here to update the UI based on the itme selected
                        switch (menuItem.getItemId()) {
                            case R.id.nav_auto_health:

                                break;
                        }

                        return true;
                    }
                }
        );

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        Intent intent;
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.nav_auto_health:
                                intent = new Intent(getApplicationContext(), AutoHealthActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_remote_control:
                                intent = new Intent(getApplicationContext(), RemoteControlActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_car_location:
                                intent = new Intent(getApplicationContext(), CarLocationActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_safety_score:
                                intent = new Intent(getApplicationContext(), SafetyScoreActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_driving_history:
                                intent = new Intent(getApplicationContext(), DrivingHistoryActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_alerts:
                                intent = new Intent(getApplicationContext(), AlertsActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_my_account:
                                intent = new Intent(getApplicationContext(), MyAccountActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_help_support:
                                intent = new Intent(getApplicationContext(), HelpAndSupportActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_about:
                                intent = new Intent(getApplicationContext(), AboutActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_signout:

                                break;
                        }


                        mDrawerLayout.closeDrawers();

                        return true;
                    }
                }
        );



    }
    @Override
    public void onClick (View v){
        Intent intent;
        switch (v.getId()) {
            case R.id.btnBack:
                finish();
                break;
        }
    }
}

