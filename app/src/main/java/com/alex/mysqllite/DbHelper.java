package com.alex.mysqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


/**
 * Created by aasantos4 on 20/09/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    Context context;

    public DbHelper(Context context) {
        super(context, "student.db", null, 1);
        this.context = context;
        Toast.makeText(context, "Constructor executed.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE grades (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fname TEXT, lname TEXT, grade TEXT)";
        db.execSQL(query);
        Toast.makeText(context, "onCreate() executed.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String dropTable = "DROP TABLE IF EXISTS grades";
        db.execSQL(dropTable);
        onCreate(db);
        Toast.makeText(context, "onUpgrade() executed.", Toast.LENGTH_LONG).show();
    }

    public boolean insertData (String fname, String lname, String grade){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fname", fname);
        cv.put("lname", lname);
        cv.put("grade", grade);
        long result = db.insert("grades", null, cv);

        if(result == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM grades",null);

    }

    public boolean updateData (String id, String fname, String lname, String grade){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("fname", fname);
        cv.put("lname", lname);
        cv.put("grade", grade);
        db.update("grades",cv, "_id = ?", new String[]{ id });
        return true;

    }

    public Integer deleteData (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("grades", "_id = ?", new String[]{id});
    }



}
