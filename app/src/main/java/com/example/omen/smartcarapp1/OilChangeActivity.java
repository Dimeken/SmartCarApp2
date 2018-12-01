package com.example.omen.smartcarapp1;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class OilChangeActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton btnBack,btnAdd;
    Switch swReminder;
    RelativeLayout relReminder;
    EditText editDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    static ArrayList<Note> arrayListOil;
    public static NoteAdapter adapter;
    DBHelper dbHelper;
    SQLiteDatabase db;

    SharedPreferences sPref;
    SharedPreferences.Editor editor;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_change);

        sPref = getSharedPreferences("my_account", MODE_PRIVATE);
        editor = sPref.edit();



        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        swReminder = (Switch) findViewById(R.id.swReminder);
        relReminder = (RelativeLayout) findViewById(R.id.relReminder);
        editDate = (EditText) findViewById(R.id.editDate);
        lv = (ListView)findViewById(R.id.listOil);

        btnBack.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        swReminder.setOnClickListener(this);
        editDate.setOnClickListener(this);


        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        arrayListOil = new ArrayList<Note>();
        adapter = new NoteAdapter(this, arrayListOil);
        lv.setAdapter(adapter);

        Cursor c = db.query("mytable",null,null,null,null,null,null);
        if(db.getMaximumSize() == 0){
            cv.put("oil","масло Mobil");
            cv.put("date","20 Авг 2018");
            db.insert("mytable",null,cv);
            cv.put("oil","масло КазМунайГаз");
            cv.put("date","20 Сент 2018");
            db.insert("mytable",null,cv);
        }

        if(c.moveToFirst()){
            int idColIndex = c.getColumnIndex("id");
            int oilColIndex = c.getColumnIndex("oil");
            int dateColIndex = c.getColumnIndex("date");
            do{
                int intID = c.getInt(idColIndex);
                String strOil = c.getString(oilColIndex);
                String strDate = c.getString(dateColIndex);
                Note n = new Note(strOil,strDate);
                adapter.insert(n,0);
            }while(c.moveToNext());
        }





        boolean sw = sPref.getBoolean("swReminder", false);
        if (sw) {
            swReminder.setChecked(true);
            relReminder.setVisibility(View.VISIBLE);
        } else {
            swReminder.setChecked(false);
            relReminder.setVisibility(View.GONE);
        }


        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(OilChangeActivity.this,
                        android.R.style.Theme_Black,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String date = dayOfMonth + "/" + month + "/" + year;
                editDate.setText(date);
                editor.putInt("day", dayOfMonth);
                editor.putInt("month", month);
                editor.putInt("year", year);
                editor.commit();
            }

            ;

        };









    }//end of onCreate()

    @Override
    public void onResume(){
        super.onResume();
        sPref = getSharedPreferences("my_account", MODE_PRIVATE);
        editor = sPref.edit();
        boolean sw = sPref.getBoolean("swReminder", false);
        if (sw) {
            swReminder.setChecked(true);
            relReminder.setVisibility(View.VISIBLE);
        } else {
            swReminder.setChecked(false);
            relReminder.setVisibility(View.GONE);
        }
        int dayOfMonth,month,year;
        dayOfMonth = sPref.getInt("day",-1);
        month = sPref.getInt("month",-1);
        year = sPref.getInt("year",-1);
        String date = dayOfMonth + "/" + month + "/" + year;
        editDate.setText(date);

    }


    @Override
    public void onClick(View v){
        Intent intent;
        boolean sw;
        switch (v.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnAdd:
                DialogNewNote note = new DialogNewNote();
                note.show(getSupportFragmentManager(),"123");
                break;
            case R.id.swReminder:
                sw = swReminder.isChecked();
                editor.putBoolean("swReminder",sw);
                editor.commit();
                if(sw){
                    relReminder.setVisibility(View.VISIBLE);
                }else{
                    relReminder.setVisibility(View.GONE);
                }
                break;
        }
    }

    public void addNewNote(String oil, String date){
        /*LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.note_oil,null,false);
        Button btnOil = (Button)view.findViewById(R.id.btnOil);
        Button btnDate = (Button)view.findViewById(R.id.btnDate);
        btnOil.setText(oil);
        btnDate.setText(date);*/



        adapter.insert(new Note(oil,date),0);
        adapter.notifyDataSetChanged();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("oil",oil);
        cv.put("date",date);
        db.insert("mytable",null,cv);

    }









    public class Note{
        public String oil;
        public String date;

        public Note(String oil, String date){
            this.oil = oil;
            this.date = date;
        }
    }

    public class NoteAdapter extends ArrayAdapter<Note>{
        public NoteAdapter(Context context, ArrayList<Note> notes){
            super(context,0,notes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            //Get the data item for this position
            Note note = getItem(position);
            //Check if an existing view is being reused, otherwise inflate the view
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_oil,parent,false);

            }
            //Lookup view for data population
            Button btnOil = (Button)convertView.findViewById(R.id.btnOil);
            Button btnDate = (Button)convertView.findViewById(R.id.btnDate);
            //Populate the data into the template view using the data object
            btnOil.setText(note.oil);
            btnDate.setText(note.date);
            //Return the completed view to render on screen
            return convertView;
        }


        @Override
        public void add(OilChangeActivity.Note object) {
            super.add(object);
        }
    }


    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context){
            super(context,"myDB",null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL("create table mytable(id integer primary key autoincrement,oil text, date text);");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        }







    }


}


