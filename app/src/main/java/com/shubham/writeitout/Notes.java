package com.shubham.writeitout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Notes extends AppCompatActivity {
    ArrayList<String> note;
    TextView txt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        databaseHandler db=new databaseHandler(this);
        txt1=(TextView)findViewById(R.id.txtvw);
        note=db.retreiveAll();
        ArrayAdapter<String> ans=
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,note);
        ListView listView = (ListView) findViewById(R.id.lstvw);
        listView.setAdapter(ans);
    }
}
