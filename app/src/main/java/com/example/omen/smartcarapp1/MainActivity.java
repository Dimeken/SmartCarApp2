package com.example.omen.smartcarapp1;

import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.service.autofill.CharSequenceTransformation;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

import static com.example.omen.smartcarapp1.SafetyScoreActivity.safScoreAbs;
import static com.example.omen.smartcarapp1.SafetyScoreActivity.safScoreMonth;
import static com.example.omen.smartcarapp1.SafetyScoreActivity.safScoreWeek;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {


    ImageButton btnMenu,imBtnMap;
    Button btnMyAccount;
    LinearLayout linLayDiagnostics,linLayMaintenance,linSafScore;
    TextView btnAutoHealth,btnDiagnostics;
    ImageButton btnDiagnostics2;
    TextView btnMaintenance;
    ImageButton btnMaintenance2;
    Button btnRemoteControl;
    Button btnCarLocation;
    ImageButton btnRefresh;
    Button btnAlerts;
    Button btnSafetyScoreNumber;
    Button btnDriveDistance,btnDriveFuel;
    LinearLayout linDrivingHistory;

    RadioButton radio1,radio2,radio3,radioDrive1,radioDrive2,radioDrive3;
    boolean bRadio1,bRadio2,bRadio3,bRadioDrive1,bRadioDrive2,bRadioDrive3;

    SharedPreferences sPref;
    SharedPreferences.Editor editor;

    TextView navHeaderName,navHeaderCar;
    DrawerLayout mDrawerLayout;

    boolean swEngine,swDoors;
    Switch switchEngine,switchDoors;

    Switch switchSpeedAlert,switchBoundaryAlert;
    boolean swSpeed,swBound;

    static int[][] driveMeasures = new int[][]{
            {300,50},                               //driveMeasures[0] week, [1] month, [2] year, [][0] distance,[][1] fuel
            {1000,200},
            {10000,1000}};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMenu = (ImageButton)findViewById(R.id.btnMenu);
        btnMyAccount = (Button)findViewById(R.id.btnMyAccount);
        linLayDiagnostics = (LinearLayout)findViewById(R.id.linLayDiagnostics);
        linLayMaintenance = (LinearLayout)findViewById(R.id.linLayMaintenance);
        btnAutoHealth = (Button)findViewById(R.id.btnAutoHealth);
        btnDiagnostics = (TextView)findViewById(R.id.btnDiagnostics);
        btnDiagnostics2 = (ImageButton)findViewById(R.id.btnDiagnostics2);
        btnMaintenance = (TextView)findViewById(R.id.btnMaintenance);
        btnMaintenance2 = (ImageButton)findViewById(R.id.btnMaintenance2);
        btnRemoteControl = (Button)findViewById(R.id.btnRemoteControl);
        btnCarLocation = (Button)findViewById(R.id.btnCarLocation);
        imBtnMap = (ImageButton)findViewById(R.id.imBtnMap);
        btnRefresh = (ImageButton)findViewById(R.id.btnRefresh);
        linSafScore = (LinearLayout)findViewById(R.id.linSafScore);
        btnAlerts = (Button)findViewById(R.id.btnAlerts);
        switchEngine = (Switch)findViewById(R.id.switchEngine);
        switchDoors = (Switch)findViewById(R.id.switchDoors);
        btnSafetyScoreNumber = (Button)findViewById(R.id.btnSafetyScoreNumber);
        btnDriveDistance = (Button)findViewById(R.id.btnDriveDistance);
        btnDriveFuel = (Button)findViewById(R.id.btnDriveFuel);
        linDrivingHistory = (LinearLayout)findViewById(R.id.linDrivingHistory);
        switchSpeedAlert = (Switch)findViewById(R.id.switchSpeedAlert);
        switchBoundaryAlert = (Switch)findViewById(R.id.switchBoundaryAlert);

        btnMenu.setOnClickListener(this);
        btnMyAccount.setOnClickListener(this);
        linLayDiagnostics.setOnClickListener(this);
        linLayMaintenance.setOnClickListener(this);
        btnAutoHealth.setOnClickListener(this);
        btnDiagnostics.setOnClickListener(this);
        btnDiagnostics2.setOnClickListener(this);
        btnMaintenance.setOnClickListener(this);
        btnMaintenance2.setOnClickListener(this);
        btnRemoteControl.setOnClickListener(this);
        btnCarLocation.setOnClickListener(this);
        imBtnMap.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);
        linSafScore.setOnClickListener(this);
        btnAlerts.setOnClickListener(this);
        btnSafetyScoreNumber.setOnClickListener(this);
        linDrivingHistory.setOnClickListener(this);
        switchEngine.setOnClickListener(this);
        switchDoors.setOnClickListener(this);
        switchSpeedAlert.setOnClickListener(this);
        switchBoundaryAlert.setOnClickListener(this);

        radio1 = (RadioButton)findViewById(R.id.radio1);
        radio2 = (RadioButton)findViewById(R.id.radio2);
        radio3 = (RadioButton)findViewById(R.id.radio3);
        radio1.setOnClickListener(this);
        radio2.setOnClickListener(this);
        radio3.setOnClickListener(this);

        radioDrive1 = (RadioButton)findViewById(R.id.radioDrive1);
        radioDrive2 = (RadioButton)findViewById(R.id.radioDrive2);
        radioDrive3 = (RadioButton)findViewById(R.id.radioDrive3);
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
        btnMyAccount.setText(sPref.getString("name","Azamat")+", "+sPref.getString("carModel","Toyota Camry 50"));
        navHeaderName.setText(sPref.getString("name","error"));
        navHeaderCar.setText(sPref.getString("carModel","error"));


        bRadio1 = sPref.getBoolean("radio1",false);
        bRadio2 = sPref.getBoolean("radio2",false);
        bRadio3 = sPref.getBoolean("radio3",false);
        radio1.setChecked(bRadio1);
        radio2.setChecked(bRadio2);
        radio3.setChecked(bRadio3);

        bRadioDrive1 = sPref.getBoolean("radioDrive1",false);
        bRadioDrive2 = sPref.getBoolean("radioDrive2",false);
        bRadioDrive3 = sPref.getBoolean("radioDrive3",false);
        radioDrive1.setChecked(bRadioDrive1);
        radioDrive2.setChecked(bRadioDrive2);
        radioDrive3.setChecked(bRadioDrive3);

        if(bRadio1){
            btnSafetyScoreNumber.setBackgroundColor(getResources().getColor(R.color.colorGreenOpen));
            btnSafetyScoreNumber.setText(""+safScoreWeek);
        }else if(bRadio2){
            btnSafetyScoreNumber.setBackgroundColor(getResources().getColor(R.color.colorYellow));
            btnSafetyScoreNumber.setText(""+safScoreMonth);
        }else if(bRadio3){
            btnSafetyScoreNumber.setBackgroundColor(getResources().getColor(R.color.colorRed));
            btnSafetyScoreNumber.setText(""+safScoreAbs);
        }
        if(bRadioDrive1){
            btnDriveDistance.setText(""+driveMeasures[0][0]);
            btnDriveFuel.setText(""+driveMeasures[0][1]);
        }else if(bRadioDrive2){
            btnDriveDistance.setText(""+driveMeasures[1][0]);
            btnDriveFuel.setText(""+driveMeasures[1][1]);
        }else if(bRadioDrive3){
            btnDriveDistance.setText(""+driveMeasures[2][0]);
            btnDriveFuel.setText(""+driveMeasures[2][1]);
        }

        swEngine = sPref.getBoolean("swEngine",false);
        swDoors = sPref.getBoolean("swDoors",false);
        switchEngine.setChecked(swEngine);
        switchDoors.setChecked(swDoors);

        swSpeed = sPref.getBoolean("swSpeed",false);
        swBound = sPref.getBoolean("swBound",false);
        switchSpeedAlert.setChecked(swSpeed);
        switchBoundaryAlert.setChecked(swBound);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        //set item as selected to persist highlight
                        menuItem.setChecked(true);
                        //close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        //Add code here to update the UI based on the itme selected
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
                                mDrawerLayout.closeDrawers();
                                break;
                            case R.id.nav_bluetooth:
                                intent = new Intent(getApplicationContext(),BluetoothActivity.class);
                                startActivity(intent);
                                break;
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
            case R.id.btnMenu:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.btnMyAccount:
                intent = new Intent(this,MyAccountActivity.class);
                startActivity(intent);
                break;
            case R.id.btnAutoHealth:
                intent = new Intent(this,AutoHealthActivity.class);
                startActivity(intent);
                break;
            case R.id.btnDiagnostics:
            case R.id.btnDiagnostics2:
                intent = new Intent(this,DiagnosticsActivity.class);
                startActivity(intent);
                break;
            case R.id.btnMaintenance:
            case R.id.btnMaintenance2:
                intent = new Intent(this,MaintenanceActivity.class);
                startActivity(intent);
                break;
            case R.id.btnRemoteControl:
                intent = new Intent(this,RemoteControlActivity.class);
                startActivity(intent);
                break;
            case R.id.btnCarLocation:
                intent = new Intent(this,CarLocationActivity.class);
                startActivity(intent);
                break;
            case R.id.imBtnMap:
                intent = new Intent(this,CarLocationActivity.class);
                startActivity(intent);
                break;
            case R.id.btnRefresh:
                break;
            case R.id.linSafScore:
                intent = new Intent(this,SafetyScoreActivity.class);
                startActivity(intent);
                break;
            case R.id.linDrivingHistory:
                intent = new Intent(this,DrivingHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.btnAlerts:
                intent = new Intent(this,AlertsActivity.class);
                startActivity(intent);
                break;
            case R.id.radioGroup:

                break;
            case R.id.switchEngine:
                swEngine = switchEngine.isChecked();
                Log.d("myLogs",""+swEngine);
                editor.putBoolean("swEngine",swEngine);
                editor.commit();
                break;
            case R.id.switchDoors:
                swDoors = switchDoors.isChecked();
                Log.d("myLogs",""+swDoors);
                editor.putBoolean("swDoors",swDoors);
                editor.commit();
                break;
            case R.id.switchSpeedAlert:
                swSpeed = switchSpeedAlert.isChecked();
                Log.d("myLogs",""+swSpeed);
                editor.putBoolean("swSpeed",swSpeed);
                editor.commit();
                break;
            case R.id.switchBoundaryAlert:
                swBound= switchBoundaryAlert.isChecked();
                Log.d("myLogs",""+swBound);
                editor.putBoolean("swBound",swBound);
                editor.commit();
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
        editor.putBoolean("radio1",bRadio1);
        editor.putBoolean("radio2",bRadio2);
        editor.putBoolean("radio3",bRadio3);
        editor.commit();
        radio1.setChecked(bRadio1);
        radio2.setChecked(bRadio2);
        radio3.setChecked(bRadio3);

        editor.putBoolean("radioDrive1",bRadioDrive1);
        editor.putBoolean("radioDrive2",bRadioDrive2);
        editor.putBoolean("radioDrive3",bRadioDrive3);
        editor.commit();
        radioDrive1.setChecked(bRadioDrive1);
        radioDrive2.setChecked(bRadioDrive2);
        radioDrive3.setChecked(bRadioDrive3);

        if(bRadio1){
            btnSafetyScoreNumber.setBackgroundColor(getResources().getColor(R.color.colorGreenOpen));
            btnSafetyScoreNumber.setText(""+safScoreWeek);
        }else if(bRadio2){
            btnSafetyScoreNumber.setBackgroundColor(getResources().getColor(R.color.colorYellow));
            btnSafetyScoreNumber.setText(""+safScoreMonth);
        }else if(bRadio3){
            btnSafetyScoreNumber.setBackgroundColor(getResources().getColor(R.color.colorRed));
            btnSafetyScoreNumber.setText(""+safScoreAbs);
        }
        if(bRadioDrive1){
            btnDriveDistance.setText(""+driveMeasures[0][0]);
            btnDriveFuel.setText(""+driveMeasures[0][1]);
        }else if(bRadioDrive2){
            btnDriveDistance.setText(""+driveMeasures[1][0]);
            btnDriveFuel.setText(""+driveMeasures[1][1]);
        }else if(bRadioDrive3){
            btnDriveDistance.setText(""+driveMeasures[2][0]);
            btnDriveFuel.setText(""+driveMeasures[2][1]);
        }

    }


    @Override
    protected void onResume(){
        editor = sPref.edit();
        btnMyAccount.setText(sPref.getString("name","error")+", "+sPref.getString("carModel","error"));
        navHeaderName.setText(sPref.getString("name","error"));
        navHeaderCar.setText(sPref.getString("carModel","error"));
        super.onResume();

        swEngine = sPref.getBoolean("swEngine",false);
        swDoors = sPref.getBoolean("swDoors",false);
        switchEngine.setChecked(swEngine);
        switchDoors.setChecked(swDoors);

        swSpeed = sPref.getBoolean("swSpeed",false);
        swBound = sPref.getBoolean("swBound",false);
        switchSpeedAlert.setChecked(swSpeed);
        switchBoundaryAlert.setChecked(swBound);

        bRadio1 = sPref.getBoolean("radio1",false);
        bRadio2 = sPref.getBoolean("radio2",false);
        bRadio3 = sPref.getBoolean("radio3",false);
        radio1.setChecked(bRadio1);
        radio2.setChecked(bRadio2);
        radio3.setChecked(bRadio3);
        bRadioDrive1 = sPref.getBoolean("radioDrive1",false);
        bRadioDrive2 = sPref.getBoolean("radioDrive2",false);
        bRadioDrive3 = sPref.getBoolean("radioDrive3",false);
        radioDrive1.setChecked(bRadioDrive1);
        radioDrive2.setChecked(bRadioDrive2);
        radioDrive3.setChecked(bRadioDrive3);

        if(bRadio1){
            btnSafetyScoreNumber.setBackgroundColor(getResources().getColor(R.color.colorGreenOpen));
            btnSafetyScoreNumber.setText(""+safScoreWeek);
        }else if(bRadio2){
            btnSafetyScoreNumber.setBackgroundColor(getResources().getColor(R.color.colorYellow));
            btnSafetyScoreNumber.setText(""+safScoreMonth);
        }else if(bRadio3){
            btnSafetyScoreNumber.setBackgroundColor(getResources().getColor(R.color.colorRed));
            btnSafetyScoreNumber.setText(""+safScoreAbs);
        }
        if(bRadioDrive1){
            btnDriveDistance.setText(""+driveMeasures[0][0]);
            btnDriveFuel.setText(""+driveMeasures[0][1]);
        }else if(bRadioDrive2){
            btnDriveDistance.setText(""+driveMeasures[1][0]);
            btnDriveFuel.setText(""+driveMeasures[1][1]);
        }else if(bRadioDrive3){
            btnDriveDistance.setText(""+driveMeasures[2][0]);
            btnDriveFuel.setText(""+driveMeasures[2][1]);
        }
    }



}




