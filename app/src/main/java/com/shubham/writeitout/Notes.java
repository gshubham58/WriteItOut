package com.shubham.writeitout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Notes extends AppCompatActivity {
    ArrayList<String> note;
    GridView grd;
    ArrayList<Integer> index;
    int i = 0;
    databaseModel dbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        databaseHandler db = new databaseHandler(this);
        dbm= db.retreiveAll();
        if(dbm.getNum().size()<1){

            Toast.makeText(this,"No Saved Notes Found",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
            this.finish();
        }
        note=dbm.getNewWord();
        index=dbm.getNum();

        int [] prgmImages={R.drawable.image1};

        grd = (GridView) findViewById(R.id.grdvw);

        grd.setAdapter(new CustomAdapter(this,index,prgmImages));

        grd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(Notes.this,noteDetails.class);
                i.putExtra("index",index.get(position));
                i.putExtra("data",note.get(position));
                startActivity(i);

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Notes.this,MainActivity.class);
        startActivity(i);

    }
}


