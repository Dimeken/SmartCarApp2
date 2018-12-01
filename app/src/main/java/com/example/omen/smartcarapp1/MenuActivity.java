package com.example.omen.smartcarapp1;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnMyAccount;
    SharedPreferences sPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onResume(){
        editor = sPref.edit();
        btnMyAccount.setText(sPref.getString("name","error")+", "+sPref.getString("carModel","error"));
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnMyAccount = (Button)findViewById(R.id.btnMyAccount);
        btnMyAccount.setOnClickListener(this);

        sPref = getSharedPreferences("my_account",MODE_PRIVATE);
        editor = sPref.edit();
        btnMyAccount.setText(sPref.getString("name","error")+", "+sPref.getString("carModel","error"));



    }
    @Override
    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.btnMyAccount:
                intent = new Intent(this,MyAccountActivity.class);
                startActivity(intent);
                break;

        }
    }



    public void onClickAutoHealth(View view){
        Intent intent2 = new Intent(this, AutoHealthActivity.class);
        startActivity(intent2);
    }
    public void onClickRemoteControl(View view){
        Intent intent2 = new Intent(this, RemoteControlActivity.class);
        startActivity(intent2);
    }
    public void onClickCarLocation(View view){
        Intent intent2 = new Intent(this, CarLocationActivity.class);
        startActivity(intent2);
    }
    public void onClickSafetyScore(View view){
        Intent intent2 = new Intent(this, SafetyScoreActivity.class);
        startActivity(intent2);
    }
    public void onClickDrivingHistory(View view){
        Intent intent2 = new Intent(this, DrivingHistoryActivity.class);
        startActivity(intent2);
    }
    public void onClickAlerts(View view){
        Intent intent2 = new Intent(this, AlertsActivity.class);
        startActivity(intent2);
    }
    public void onClickMyAccount(View view){
        Intent intent2 = new Intent(this, MyAccountActivity.class);
        startActivity(intent2);
    }
    public void onClickHelpAndSupport(View view){
        Intent intent2 = new Intent(this, HelpAndSupportActivity.class);
        startActivity(intent2);
    }
    public void onClickAbout(View view){
        Intent intent2 = new Intent(this, AboutActivity.class);
        startActivity(intent2);
    }
    public void onClickSignOut(View view){
    }
}
