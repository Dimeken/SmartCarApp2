package com.example.omen.smartcarapp1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omen.smartcarapp1.dbHelper.MyAccountDetails;

public class MyAccountActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editName;
    EditText editCarModel;
    Button btnSignOut;
    MyAccountDetails myAccount;
    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    ImageButton btnBack;
    TextView navHeaderName,navHeaderCar;
    DrawerLayout mDrawerLayout;
    Button btnFindModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        editName = (EditText)findViewById(R.id.editName);
        editCarModel = (EditText)findViewById(R.id.editCarModel);
        btnSignOut = (Button)findViewById(R.id.btnSignOut);
        btnBack = (ImageButton)findViewById(R.id.btnBack);
        btnFindModel = (Button)findViewById(R.id.btnFindModel);

        btnBack.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
        btnFindModel.setOnClickListener(this);

        myAccount = new MyAccountDetails();


        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navHeaderName = (TextView)headerView.findViewById(R.id.navHeaderName);
        navHeaderCar = (TextView)headerView.findViewById(R.id.navHeaderCar);
        navigationView.setItemIconTintList(null);

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
                                mDrawerLayout.closeDrawers();
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
        sPref = getSharedPreferences("my_account",MODE_PRIVATE);
        editor = sPref.edit();
        String name = sPref.getString("name","Azamat");
        String carModel = sPref.getString("carModel","Toyota Camry 50");
        editName.setHint(name);
        editCarModel.setHint(carModel);

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editor.putString("name",editName.getText().toString());
                editor.commit();
            }
            @Override
            public void afterTextChanged(Editable s) {
                editName.setHint(sPref.getString("name","error").toString());
            }
        });

        editCarModel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editor.putString("carModel",editCarModel.getText().toString());
                editor.commit();
            }
            @Override
            public void afterTextChanged(Editable s) {
                editCarModel.setHint(sPref.getString("carModel","error").toString());
            }
        });


    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnFindModel:
                Toast.makeText(this, "car found", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnSignOut:
                Toast.makeText(this, "you signed out", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}

