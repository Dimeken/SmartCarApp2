package com.example.omen.smartcarapp1;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


public class NoteOil {
    //private static final String JSON_TITLE = "title";
    //private static final String JSON_DESCRIPTION = "description";
    private static final String JSON_TITLE = "title";
    private static final String JSON_DESCRIPTION = "description";

    private String mTitle,mDescription;
    private boolean mIdea,mTodo,mImportant;

    //Constructor
    //Only used when new is called with a JSONObject
    public NoteOil(JSONObject jo) throws JSONException {
        mTitle = jo.getString(JSON_TITLE);
        mDescription = jo.getString(JSON_DESCRIPTION);
    }
    //Now we must provide an empty default constructor
    //for when we create a Note as we provide a
    //specialized constructor that must be used
    public NoteOil(){

    }

    public JSONObject convertToJSON() throws JSONException{
        JSONObject jo = new JSONObject();

        jo.put(JSON_TITLE,mTitle);
        jo.put(JSON_DESCRIPTION,mDescription);
        return jo;
    }

    static class JSONSerializer{
        private String mFilename;
        private Context mContext;
        //All the rest of the code fo the class goes here

        public JSONSerializer (String fn, Context con){
            mFilename = fn;
            mContext = con;
        }
        public void save(List<NoteOil> notes)throws IOException,JSONException{
            //Make an array in JSON format
            JSONArray jArray = new JSONArray();
            //And load it with the notes
            for(NoteOil n:notes) jArray.put(n.convertToJSON());
            //Now write it to the private disk space of our app
            Writer writer = null;
            try{
                OutputStream out = mContext.openFileOutput(mFilename,mContext.MODE_PRIVATE);
                writer = new OutputStreamWriter(out);
                writer.write(jArray.toString());
            }finally{
                if(writer!=null){
                    writer.close();
                }
            }

        }

        public ArrayList<NoteOil> load()throws IOException, JSONException{
            ArrayList<NoteOil> noteList = new ArrayList<NoteOil>();
            BufferedReader reader = null;
            try{
                InputStream in = mContext.openFileInput(mFilename);
                reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder jsonString = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null){
                    jsonString.append(line);
                }
                JSONArray jArray = (JSONArray) new JSONTokener(
                        jsonString.toString()).nextValue();
                for(int i=0; i<jArray.length(); i++){
                    noteList.add(new NoteOil(jArray.getJSONObject(i)));
                }

            }catch(FileNotFoundException e){
                //we will ignore this one, since it happens
                //when we start fresh. You could add a log here
            }finally{
                //This will always run
                if(reader!=null) reader.close();
            }
            return noteList;
        }




    }//End of class

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setIdea(boolean mIdea) {
        this.mIdea = mIdea;
    }

    public void setTodo(boolean mTodo) {
        this.mTodo = mTodo;
    }

    public void setImportant(boolean mImportant) {
        this.mImportant = mImportant;
    }

    public String getOilModel() {
        return mTitle;
    }

    public String getOilDate() {
        return mDescription;
    }

    public boolean isIdea() {
        return mIdea;
    }

    public boolean isTodo() {
        return mTodo;
    }

    public boolean isImportant() {
        return mImportant;
    }
}
