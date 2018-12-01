package com.example.omen.smartcarapp1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.example.omen.smartcarapp1.SafetyScoreActivity.safScoreWeek;

public class DrivingHistoryActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnBack;


    TextView navHeaderName,navHeaderCar;
    DrawerLayout mDrawerLayout;
    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    RadioButton radioDrive1,radioDrive2,radioDrive3;
    boolean bRadioDrive1,bRadioDrive2,bRadioDrive3;
    Button btnThisText,btnDriveDistance,btnDriveFuel;

    static int[][] driveMeasures = new int[][]{
            {300,50},                               //driveMeasures[0] week, [1] month, [2] year, [][0] distance,[][1] fuel
            {1000,200},
            {10000,1000}};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_history);


        btnBack = (ImageButton)findViewById(R.id.btnBack);
        radioDrive1 = (RadioButton)findViewById(R.id.radioDrive1);
        radioDrive2 = (RadioButton)findViewById(R.id.radioDrive2);
        radioDrive3 = (RadioButton)findViewById(R.id.radioDrive3);
        btnThisText = (Button)findViewById(R.id.btnThisText);
        btnDriveDistance = (Button)findViewById(R.id.btnDriveDistance);
        btnDriveFuel = (Button)findViewById(R.id.btnDriveFuel);

        btnBack.setOnClickListener(this);
        radioDrive1.setOnClickListener(this);
        radioDrive2.setOnClickListener(this);
        radioDrive3.setOnClickListener(this);


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



        bRadioDrive1 = sPref.getBoolean("radioDrive1",false);
        bRadioDrive2 = sPref.getBoolean("radioDrive2",false);
        bRadioDrive3 = sPref.getBoolean("radioDrive3",false);
        radioDrive1.setChecked(bRadioDrive1);
        radioDrive2.setChecked(bRadioDrive2);
        radioDrive3.setChecked(bRadioDrive3);

        if(bRadioDrive1){
            btnThisText.setText(R.string.text_drivingHistory_thisWeek);
            btnDriveDistance.setText(""+driveMeasures[0][0]);
            btnDriveFuel.setText(""+driveMeasures[0][1]);
        }else if(bRadioDrive2){
            btnThisText.setText(R.string.text_drivingHistory_thisMonth);
            btnDriveDistance.setText(""+driveMeasures[1][0]);
            btnDriveFuel.setText(""+driveMeasures[1][1]);
        }else if(bRadioDrive3){
            btnThisText.setText(R.string.text_drivingHistory_thisYear);
            btnDriveDistance.setText(""+driveMeasures[2][0]);
            btnDriveFuel.setText(""+driveMeasures[2][1]);
        }


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
                                startActivity(intent);                                break;
                            case R.id.nav_driving_history:
                                mDrawerLayout.closeDrawers();
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
            case R.id.radioDrive1:
                bRadioDrive1=true;bRadioDrive2=false;bRadioDrive3=false;
                break;
            case R.id.radioDrive2:
                bRadioDrive1=false;bRadioDrive2=true;bRadioDrive3=false;
                break;
            case R.id.radioDrive3:
                bRadioDrive1=false;bRadioDrive2=false;bRadioDrive3=true;
                break;
        }
        editor.putBoolean("radioDrive1",bRadioDrive1);
        editor.putBoolean("radioDrive2",bRadioDrive2);
        editor.putBoolean("radioDrive3",bRadioDrive3);
        editor.commit();
        radioDrive1.setChecked(bRadioDrive1);
        radioDrive2.setChecked(bRadioDrive2);
        radioDrive3.setChecked(bRadioDrive3);

        if(bRadioDrive1){
            btnThisText.setText(R.string.text_drivingHistory_thisWeek);
            btnDriveDistance.setText(""+driveMeasures[0][0]);
            btnDriveFuel.setText(""+driveMeasures[0][1]);
        }else if(bRadioDrive2){
            btnThisText.setText(R.string.text_drivingHistory_thisMonth);
            btnDriveDistance.setText(""+driveMeasures[1][0]);
            btnDriveFuel.setText(""+driveMeasures[1][1]);
        }else if(bRadioDrive3){
            btnThisText.setText(R.string.text_drivingHistory_thisYear);
            btnDriveDistance.setText(""+driveMeasures[2][0]);
            btnDriveFuel.setText(""+driveMeasures[2][1]);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        editor = sPref.edit();

        bRadioDrive1 = sPref.getBoolean("radioDrive1",false);
        bRadioDrive2 = sPref.getBoolean("radioDrive2",false);
        bRadioDrive3 = sPref.getBoolean("radioDrive3",false);
        radioDrive1.setChecked(bRadioDrive1);
        radioDrive2.setChecked(bRadioDrive2);
        radioDrive3.setChecked(bRadioDrive3);

        if(bRadioDrive1){
            btnThisText.setText(R.string.text_drivingHistory_thisWeek);
            btnDriveDistance.setText(""+driveMeasures[0][0]);
            btnDriveFuel.setText(""+driveMeasures[0][1]);
        }else if(bRadioDrive2){
            btnThisText.setText(R.string.text_drivingHistory_thisMonth);
            btnDriveDistance.setText(""+driveMeasures[1][0]);
            btnDriveFuel.setText(""+driveMeasures[1][1]);
        }else if(bRadioDrive3){
            btnThisText.setText(R.string.text_drivingHistory_thisYear);
            btnDriveDistance.setText(""+driveMeasures[2][0]);
            btnDriveFuel.setText(""+driveMeasures[2][1]);
        }
    }
}