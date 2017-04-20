package com.shubham.writeitout;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by hp on 21-Apr-17.
 */

public class databaseHandler extends SQLiteOpenHelper {
    private final static String tablename="data";
    private final static String column_name="word";


    public databaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public databaseHandler(Context context){
        super(context,"wordData.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="create table "+tablename+" ( "
            +column_name+" text );";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void add(String s)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("insert into "+tablename+" values "+" ( \""+s+
                "\" );");


    }

    public ArrayList<String> retreiveAll(){
        ArrayList<String> newWord = new ArrayList<>();
        int i=0;
        SQLiteDatabase db=getWritableDatabase();
        String qr="select * from "+tablename+" ;";

            Cursor c = db.rawQuery(qr, null);
            c.moveToFirst();
            while(!c.isAfterLast()) {
                newWord.add(i,c.getString(0));
                i++;
                c.moveToNext();
            }
            return newWord;
    }

}


