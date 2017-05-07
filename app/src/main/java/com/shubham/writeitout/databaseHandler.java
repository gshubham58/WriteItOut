package com.shubham.writeitout;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by hp on 21-Apr-17.
 */

public class databaseHandler extends SQLiteOpenHelper {
    private final static String tablename = "data";
    private final static String column2= "word";
    private final static String ind = "ind";
    static int indval = 0;

    public databaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public databaseHandler(Context context) {
        super(context, "wordData.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + tablename + " ( " +ind + " integer , "+
                column2 + " text );";
        db.execSQL(query);
        //db.execSQL("create table data(ind integer,word text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void add(String s) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("insert into " + tablename + " values " + " ( " + indval +", \"" + s +
                "\" );");
        Log.e("database index",""+indval);
        indval++;



    }

    public databaseModel retreiveAll() {
        ArrayList<String> newWord = new ArrayList<>();
        ArrayList<Integer>num=new ArrayList<>();
        int i = 0;
        SQLiteDatabase db = getWritableDatabase();
        String qr = "select * from " + tablename + " ;";

        Cursor c = db.rawQuery(qr, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            newWord.add(i, c.getString(c.getColumnIndex(column2)));
            num.add(i,c.getInt(c.getColumnIndex(ind)));
            i++;
            c.moveToNext();
        }
        databaseModel dbm=new databaseModel();
        dbm.setNewWord(newWord);
        dbm.setNum(num);
        return dbm;
    }

    public void delete(int index) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "delete from " + tablename + " where " + ind + " = " + index + " ;";
        db.execSQL(query);

    }

}


