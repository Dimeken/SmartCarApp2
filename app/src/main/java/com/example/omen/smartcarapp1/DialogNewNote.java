package com.example.omen.smartcarapp1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DialogNewNote extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_dialog_new_note,null);

        final EditText editOil = (EditText)dialogView.findViewById(R.id.editOil);
        final EditText editDate = (EditText)dialogView.findViewById(R.id.editDate);
        Button btnOk = (Button)dialogView.findViewById(R.id.btnOk);
        Button btnCancel = (Button)dialogView.findViewById(R.id.btnCancel);

        builder.setView(dialogView).setMessage("add a new note");

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String oil = editOil.getText().toString();
                String date = editDate.getText().toString();
                OilChangeActivity callingActivity = (OilChangeActivity)getActivity();
                callingActivity.addNewNote(oil,date);
                dismiss();
            }
        });

        return builder.create();
    }
}
