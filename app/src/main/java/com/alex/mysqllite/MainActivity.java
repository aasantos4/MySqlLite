package com.alex.mysqllite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DbHelper helper;
    EditText etFirstname, etLastname, etGrade, etId;
    SQLiteDatabase dbase;
    Button btnSaveRecord;
    Button btnSaveUpdate;
    Cursor res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DbHelper(this);
        dbase = helper.getWritableDatabase();
        etFirstname = (EditText) findViewById(R.id.et_firstname);
        etLastname = (EditText) findViewById(R.id.et_surname);
        etGrade = (EditText)findViewById(R.id.et_grade);
        etId = (EditText) findViewById(R.id.et_id);
        btnSaveRecord = (Button) findViewById(R.id.btn_save);
        btnSaveUpdate = (Button) findViewById(R.id.btn_save_update);
        res = helper.getAllData();
        disableInput();
        //showFirstRecord();

    }

    public void showFirstRecord(View view) {

        if(res.getCount() > 0){
            //Toast.makeText(this, "Records found", Toast.LENGTH_LONG).show();
            res.moveToFirst();
            disableInput();
            displayRecord();
            Toast.makeText(this, "First Record!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No records found!", Toast.LENGTH_SHORT).show();
        }
    }

    public void showNextRecord (View view){
        if(res.getPosition() < (res.getCount()-1)){
            res.moveToNext();
            disableInput();
        }
        else {
            res.moveToLast();
            disableInput();
            Toast.makeText(this, "Last record!", Toast.LENGTH_SHORT).show();
        }
        displayRecord();
    }

    public void showPreviousRecord (View view){
        if(res.getPosition() > 0){
            res.moveToPrevious();
            disableInput();
        }
        else {
            res.moveToFirst();
            disableInput();
            Toast.makeText(this, "First Record!", Toast.LENGTH_SHORT).show();
        }
        displayRecord();
    }

    public void showLastRecord(View view){
        res = helper.getAllData();
        res.moveToLast();
        disableInput();
        displayRecord();
        Toast.makeText(this, "Last record!", Toast.LENGTH_SHORT).show();
    }

    private void displayRecord(){
        etId.setText(res.getString(0));
        etFirstname.setText(res.getString(1));
        etLastname.setText(res.getString(2));
        etGrade.setText(res.getString(3));

    }

    public void saveNewRecord (View view){
        String fname, lname, grade;
        fname = etFirstname.getText().toString();
        lname = etLastname.getText().toString();
        grade = etGrade.getText().toString();
        boolean isInserted = helper.insertData(fname, lname, grade);
        if(isInserted==true)
            Toast.makeText(this, "Data inserted!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Data insertion failed!", Toast.LENGTH_SHORT).show();
       // res.close();
      //  res = helper.getAllData();
      //  res.move(res.getCount()-1);

    }

    public void addRecord(View view){
        enableInput();
        etId.setText(""+(res.getCount()+1));
        etLastname.setText("");
        etFirstname.setText("");
        etGrade.setText("");
    }

    private void disableInput(){
        etId.setEnabled(false);
        etFirstname.setEnabled(false);
        etLastname.setEnabled(false);
        etGrade.setEnabled(false);
        btnSaveRecord.setClickable(false);
        btnSaveRecord.setTextColor(Color.GRAY);
        btnSaveUpdate.setClickable(false);
        btnSaveUpdate.setTextColor(Color.GRAY);
    }

    private void enableInput(){
        etId.setEnabled(true);
        etFirstname.setEnabled(true);
        etLastname.setEnabled(true);
        etGrade.setEnabled(true);
        btnSaveRecord.setClickable(true);
        btnSaveRecord.setTextColor(Color.BLACK);
        btnSaveUpdate.setClickable(true);
        btnSaveUpdate.setTextColor(Color.BLACK);

    }

    public void editRecord(View view){
        enableInput();
    }

    public void saveUpdate(View view){
        String id, fname, lname, grade;
        id = etId.getText().toString();
        fname = etFirstname.getText().toString();
        lname = etLastname.getText().toString();
        grade = etGrade.getText().toString();

        helper.updateData(id, fname, lname, grade);
        disableInput();
        res.close();
        res = helper.getAllData();
        res.move(Integer.parseInt(id));


    }

    public void deleteRecord (View view){
        String id = etId.getText().toString();
        Integer deleted = helper.deleteData(id);
        if(deleted > 0){
            Toast.makeText(this, "ID No. " + id + "was deleted!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Deletion Failed!", Toast.LENGTH_SHORT).show();
        }
        res.close();
        res = helper.getAllData();
        res.moveToLast();
        displayRecord();

    }

}
