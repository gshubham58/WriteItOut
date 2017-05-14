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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
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
    databaseHandler db;
    ImageButton imgbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        db = new databaseHandler(this);
        imgbtn=(ImageButton)findViewById(R.id.deleteimg);
        dbm= db.retreiveAll();
        if(dbm.getNum().size()<1){

            Toast.makeText(this,"No Saved Notes Found",Toast.LENGTH_SHORT).show();
            imgbtn.setVisibility(View.INVISIBLE);
           }
        note=dbm.getNewWord();
        index=dbm.getNum();
        int [] prgmImages={R.drawable.img};

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
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAll();
                Toast.makeText(Notes.this,"Delete Successful",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Notes.this,MainActivity.class);
                startActivity(i);
                Notes.this.finish();
            }
        });
    }
    public void AddNewButton(View view){
        Intent i=new Intent(Notes.this,TextDetect.class);
        startActivity(i);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Notes.this,MainActivity.class);
        startActivity(i);

    }
}


