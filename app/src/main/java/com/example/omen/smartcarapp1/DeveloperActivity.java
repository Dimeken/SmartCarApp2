package com.example.omen.smartcarapp1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

public class DeveloperActivity extends Activity
        implements View.OnClickListener {

    private ArrayAdapter<String> adapter;

    DrawerLayout mDrawerLayout;
    ImageButton btnBack;

    public static String TABLE_NAME = "mytable";
    public static String MODEL = "model";
    public static String ERROR_CODE = "errorcode";
    public static String PROBLEM = "problem";

    String FILENAME = "file";


    Button btnRead, btnWrite, btnShowSQL, btnClearSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnRead = (Button) findViewById(R.id.btnRead);
        btnWrite = (Button) findViewById(R.id.btnWrite);
        btnShowSQL = (Button) findViewById(R.id.btnShowSQL);
        btnClearSQL = (Button) findViewById(R.id.btnClearSQL);
        btnRead.setOnClickListener(this);
        btnWrite.setOnClickListener(this);
        btnShowSQL.setOnClickListener(this);
        btnClearSQL.setOnClickListener(this);

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
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnBack:
                finish();
                break;
        }

    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_NAME + " (" +
                    "id integer primary key autoincrement, " +
                    MODEL + " text, " +
                    ERROR_CODE + " text, " +
                    PROBLEM + " text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }



}

