package com.example.omen.smartcarapp1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

public class AlertsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnBack;


    TextView navHeaderName,navHeaderCar;
    DrawerLayout mDrawerLayout;
    SharedPreferences sPref;
    SharedPreferences.Editor editor;

    Switch switchSpeedAlert,switchBoundaryAlert;
    boolean swSpeed,swBound;

    EditText editSpeed,editBound;

    String stringSpeed="enter here",stringBound="enter here";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        btnBack = (ImageButton)findViewById(R.id.btnBack);
        switchSpeedAlert = (Switch)findViewById(R.id.switchSpeedAlert);
        switchBoundaryAlert = (Switch)findViewById(R.id.switchBoundaryAlert);
        editSpeed = (EditText)findViewById(R.id.editSpeed);
        editBound = (EditText)findViewById(R.id.editBound);

        btnBack.setOnClickListener(this);
        switchSpeedAlert.setOnClickListener(this);
        switchBoundaryAlert.setOnClickListener(this);


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


        swSpeed = sPref.getBoolean("swSpeed",false);
        swBound = sPref.getBoolean("swBound",false);
        switchSpeedAlert.setChecked(swSpeed);
        switchBoundaryAlert.setChecked(swBound);

        stringSpeed = sPref.getString("stringSpeed","enter here");
        stringBound = sPref.getString("stringBound","enter here");
        editSpeed.setText(stringSpeed);
        editBound.setText(stringBound);


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
                                intent = new Intent(getApplicationContext(),RemoteControlActivity.class);
                                startActivity(intent);
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
                                mDrawerLayout.closeDrawers();
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

        editSpeed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editor.putString("stringSpeed",editSpeed.getText().toString());
                editor.commit();
            }
            @Override
            public void afterTextChanged(Editable s) {
                editSpeed.setHint(sPref.getString("name","error").toString());
            }
        });

        editBound.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editor.putString("stringBound",editBound.getText().toString());
                editor.commit();
            }
            @Override
            public void afterTextChanged(Editable s) {
                editBound.setHint(sPref.getString("carModel","error").toString());
            }
        });




    }









    @Override
    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.switchSpeedAlert:
                swSpeed = switchSpeedAlert.isChecked();
                editor.putBoolean("swSpeed",swSpeed);
                editor.commit();
                break;
            case R.id.switchBoundaryAlert:
                swBound= switchBoundaryAlert.isChecked();
                editor.putBoolean("swBound",swBound);
                editor.commit();
                break;
        }
    }



    @Override
    public void onResume(){
        super.onResume();
        swSpeed = sPref.getBoolean("swSpeed",false);
        swBound = sPref.getBoolean("swBound",false);
        switchSpeedAlert.setChecked(swSpeed);
        switchBoundaryAlert.setChecked(swBound);

    }
}