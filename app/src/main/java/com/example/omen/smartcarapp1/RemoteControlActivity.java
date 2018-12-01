package com.example.omen.smartcarapp1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

public class RemoteControlActivity extends AppCompatActivity implements View.OnClickListener{


    TextView navHeaderName,navHeaderCar;
    DrawerLayout mDrawerLayout;
    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    boolean swEngine,swDoors;
    Switch switchEngine,switchDoors;

    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);

        sPref = getSharedPreferences("my_account",MODE_PRIVATE);
        editor = sPref.edit();

        btnBack = (ImageButton)findViewById(R.id.btnBack);
        switchDoors = (Switch)findViewById(R.id.switchDoors);
        switchEngine = (Switch)findViewById(R.id.switchEngine);

        btnBack.setOnClickListener(this);
        switchDoors.setOnClickListener(this);
        switchEngine.setOnClickListener(this);


        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navHeaderName = (TextView)headerView.findViewById(R.id.navHeaderName);
        navHeaderCar = (TextView)headerView.findViewById(R.id.navHeaderCar);
        navigationView.setItemIconTintList(null);

        sPref = getSharedPreferences("my_account",MODE_PRIVATE);
        editor = sPref.edit();
        navHeaderName.setText(sPref.getString("name","error"));
        navHeaderCar.setText(sPref.getString("carModel","error"));


        swEngine = sPref.getBoolean("swEngine",false);
        swDoors = sPref.getBoolean("swDoors",false);
        switchEngine.setChecked(swEngine);
        switchDoors.setChecked(swDoors);



        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch(menuItem.getItemId()){
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
                        switch(menuItem.getItemId()){
                            case R.id.nav_main_page:
                                intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);break;
                            case R.id.nav_auto_health:
                                intent = new Intent(getApplicationContext(),AutoHealthActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_remote_control:
                                mDrawerLayout.closeDrawers();
                                break;
                            case R.id.nav_car_location:
                                intent = new Intent(getApplicationContext(),CarLocationActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_safety_score:
                                intent = new Intent(getApplicationContext(),SafetyScoreActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_driving_history:
                                intent = new Intent(getApplicationContext(),DrivingHistoryActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_alerts:
                                intent = new Intent(getApplicationContext(),AlertsActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_my_account:
                                intent = new Intent(getApplicationContext(),MyAccountActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_help_support:
                                intent = new Intent(getApplicationContext(),HelpAndSupportActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_about:
                                intent = new Intent(getApplicationContext(),AboutActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_signout:

                                break;
                            case R.id.nav_dev:
                                intent = new Intent(getApplicationContext(),DeveloperActivity.class);
                                startActivity(intent);
                                break;
                        }


                        mDrawerLayout.closeDrawers();

                        return true;
                    }
                }
        );






    }


    @Override
    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.switchEngine:
                swEngine = switchEngine.isChecked();
                editor.putBoolean("swEngine",swEngine);
                editor.commit();
                break;
            case R.id.switchDoors:
                swDoors = switchDoors.isChecked();
                editor.putBoolean("swDoors",swDoors);
                editor.commit();
                break;
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        swEngine = sPref.getBoolean("swEngine",false);
        swDoors = sPref.getBoolean("swDoors",false);
        switchEngine.setChecked(swEngine);
        switchDoors.setChecked(swDoors);
    }






}
