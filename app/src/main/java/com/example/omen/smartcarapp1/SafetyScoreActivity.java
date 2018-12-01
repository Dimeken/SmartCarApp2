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

public class SafetyScoreActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnBack;


    TextView navHeaderName,navHeaderCar;
    DrawerLayout mDrawerLayout;
    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    RadioButton radio1,radio2,radio3;
    boolean bRadio1,bRadio2,bRadio3;
    Button btnSafetyScoreNumber,btnThisText,measure1,measure2,measure3,measure4,measure5;

    static int safScoreWeek=95,safScoreMonth=85,safScoreAbs=70;
    static int[][] safScoreMeasures = new int[][]{
            {0,1,0,2,1},                               //safScoreMeasures[0] week, [1] month, [2] abs
            {1,2,1,3,2},
            {3,5,5,5,6} };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_score);


        btnBack = (ImageButton)findViewById(R.id.btnBack);
        radio1 = (RadioButton)findViewById(R.id.radio1);
        radio2 = (RadioButton)findViewById(R.id.radio2);
        radio3 = (RadioButton)findViewById(R.id.radio3);
        btnSafetyScoreNumber = (Button)findViewById(R.id.btnSafetyScoreNumber);
        btnThisText = (Button)findViewById(R.id.btnThisText);
        measure1 = (Button)findViewById(R.id.measure1);
        measure2 = (Button)findViewById(R.id.measure2);
        measure3 = (Button)findViewById(R.id.measure3);
        measure4 = (Button)findViewById(R.id.measure4);
        measure5 = (Button)findViewById(R.id.measure5);

        btnBack.setOnClickListener(this);
        radio1.setOnClickListener(this);
        radio2.setOnClickListener(this);
        radio3.setOnClickListener(this);
        btnSafetyScoreNumber.setOnClickListener(this);


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



        bRadio1 = sPref.getBoolean("radio1",false);
        bRadio2 = sPref.getBoolean("radio2",false);
        bRadio3 = sPref.getBoolean("radio3",false);
        radio1.setChecked(bRadio1);
        radio2.setChecked(bRadio2);
        radio3.setChecked(bRadio3);

        if(bRadio1){
            btnSafetyScoreNumber.setBackgroundColor(getResources().getColor(R.color.colorGreenOpen));
            btnSafetyScoreNumber.setText(""+safScoreWeek);
            btnThisText.setText(R.string.text_drivingHistory_thisWeek);
            measure1.setText(""+safScoreMeasures[0][0]);
            measure2.setText(""+safScoreMeasures[0][1]);
            measure3.setText(""+safScoreMeasures[0][2]);
            measure4.setText(""+safScoreMeasures[0][3]);
            measure5.setText(""+safScoreMeasures[0][4]);

        }else if(bRadio2){
            btnSafetyScoreNumber.setBackgroundColor(getResources().getColor(R.color.colorYellow));
            btnSafetyScoreNumber.setText(""+safScoreMonth);
            btnThisText.setText(R.string.text_drivingHistory_thisMonth);
            measure1.setText(""+safScoreMeasures[1][0]);
            measure2.setText(""+safScoreMeasures[1][1]);
            measure3.setText(""+safScoreMeasures[1][2]);
            measure4.setText(""+safScoreMeasures[1][3]);
            measure5.setText(""+safScoreMeasures[1][4]);
        }else if(bRadio3){
            btnSafetyScoreNumber.setBackgroundColor(getResources().getColor(R.color.colorRed));
            btnSafetyScoreNumber.setText(""+safScoreAbs);
            btnThisText.setText(R.string.text_drivingHistory_thisYear);
            measure1.setText(""+safScoreMeasures[2][0]);
            measure2.setText(""+safScoreMeasures[2][1]);
            measure3.setText(""+safScoreMeasures[2][2]);
            measure4.setText(""+safScoreMeasures[2][3]);
            measure5.setText(""+safScoreMeasures[2][4]);
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
                                mDrawerLayout.closeDrawers();
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
            case R.id.radio1:
                bRadio1=true;bRadio2=false;bRadio3=false;
                break;
            case R.id.radio2:
                bRadio1=false;bRadio2=true;bRadio3=false;
                break;
            case R.id.radio3:
                bRadio1=false;bRadio2=false;bRadio3=true;
                break;
        }
        editor.putBoolean("radio1",bRadio1);
        editor.putBoolean("radio2",bRadio2);
        editor.putBoolean("radio3",bRadio3);
        editor.commit();
        radio1.setChecked(bRadio1);
        radio2.setChecked(bRadio2);
        radio3.setChecked(bRadio3);

        if(bRadio1){
            btnSafetyScoreNumber.setBackgroundColor(getResources().getColor(R.color.colorGreenOpen));
            btnSafetyScoreNumber.setText(""+safScoreWeek);
            btnThisText.setText(R.string.text_drivingHistory_thisWeek);
            measure1.setText(""+safScoreMeasures[0][0]);
            measure2.setText(""+safScoreMeasures[0][1]);
            measure3.setText(""+safScoreMeasures[0][2]);
            measure4.setText(""+safScoreMeasures[0][3]);
            measure5.setText(""+safScoreMeasures[0][4]);

        }else if(bRadio2){
            btnSafetyScoreNumber.setBackgroundColor(getResources().getColor(R.color.colorYellow));
            btnSafetyScoreNumber.setText(""+safScoreMonth);
            btnThisText.setText(R.string.text_drivingHistory_thisMonth);
            measure1.setText(""+safScoreMeasures[1][0]);
            measure2.setText(""+safScoreMeasures[1][1]);
            measure3.setText(""+safScoreMeasures[1][2]);
            measure4.setText(""+safScoreMeasures[1][3]);
            measure5.setText(""+safScoreMeasures[1][4]);
        }else if(bRadio3){
            btnSafetyScoreNumber.setBackgroundColor(getResources().getColor(R.color.colorRed));
            btnSafetyScoreNumber.setText(""+safScoreAbs);
            btnThisText.setText(R.string.text_drivingHistory_thisYear);
            measure1.setText(""+safScoreMeasures[2][0]);
            measure2.setText(""+safScoreMeasures[2][1]);
            measure3.setText(""+safScoreMeasures[2][2]);
            measure4.setText(""+safScoreMeasures[2][3]);
            measure5.setText(""+safScoreMeasures[2][4]);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        sPref = getSharedPreferences("my_account",MODE_PRIVATE);
        editor = sPref.edit();

        bRadio1 = sPref.getBoolean("radio1",false);
        bRadio2 = sPref.getBoolean("radio2",false);
        bRadio3 = sPref.getBoolean("radio3",false);
        radio1.setChecked(bRadio1);
        radio2.setChecked(bRadio2);
        radio3.setChecked(bRadio3);

        if(bRadio1){
            btnSafetyScoreNumber.setBackgroundColor(getResources().getColor(R.color.colorGreenOpen));
            btnSafetyScoreNumber.setText(""+safScoreWeek);
            btnThisText.setText(R.string.text_drivingHistory_thisWeek);
            measure1.setText(""+safScoreMeasures[0][0]);
            measure2.setText(""+safScoreMeasures[0][1]);
            measure3.setText(""+safScoreMeasures[0][2]);
            measure4.setText(""+safScoreMeasures[0][3]);
            measure5.setText(""+safScoreMeasures[0][4]);

        }else if(bRadio2){
            btnSafetyScoreNumber.setBackgroundColor(getResources().getColor(R.color.colorYellow));
            btnSafetyScoreNumber.setText(""+safScoreMonth);
            btnThisText.setText(R.string.text_drivingHistory_thisMonth);
            measure1.setText(""+safScoreMeasures[1][0]);
            measure2.setText(""+safScoreMeasures[1][1]);
            measure3.setText(""+safScoreMeasures[1][2]);
            measure4.setText(""+safScoreMeasures[1][3]);
            measure5.setText(""+safScoreMeasures[1][4]);
        }else if(bRadio3){
            btnSafetyScoreNumber.setBackgroundColor(getResources().getColor(R.color.colorRed));
            btnSafetyScoreNumber.setText(""+safScoreAbs);
            btnThisText.setText(R.string.text_drivingHistory_thisYear);
            measure1.setText(""+safScoreMeasures[2][0]);
            measure2.setText(""+safScoreMeasures[2][1]);
            measure3.setText(""+safScoreMeasures[2][2]);
            measure4.setText(""+safScoreMeasures[2][3]);
            measure5.setText(""+safScoreMeasures[2][4]);
        }
    }
}