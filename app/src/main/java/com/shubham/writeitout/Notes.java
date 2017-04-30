package com.shubham.writeitout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Notes extends AppCompatActivity {
    ArrayList<String> note;
    GridView grd;
    TextView txt1;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        databaseHandler db = new databaseHandler(this);
        txt1 = (TextView) findViewById(R.id.txtvw);
        note = db.retreiveAll();
        ArrayList<String> index = new ArrayList<>();
        while (i < note.size())
        {
            int a=i+1;
            index.add(i, ""+a);
            i++;
        }
        ArrayAdapter<String> ans =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, index);
        grd = (GridView) findViewById(R.id.grdvw);
        grd.setAdapter(ans);
    grd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            txt1.setText(note.get(position));

        }
    });
    }


}


