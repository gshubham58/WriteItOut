package com.shubham.writeitout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class noteDetails extends AppCompatActivity {
    String text;
    int index;
    TextView txt1;
    Button delbt,editbt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        Intent intent=getIntent();
        text=intent.getStringExtra("data");
        index=intent.getIntExtra("index",-1);
        txt1=(TextView)findViewById(R.id.txt1);
        txt1.setText(text);
        delbt=(Button)findViewById(R.id.del);
        editbt=(Button)findViewById(R.id.edit);
        delbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHandler db = new databaseHandler(noteDetails.this);
                db.delete(index);
                Toast.makeText(noteDetails.this,"Delete Successful",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(noteDetails.this,Notes.class);
                startActivity(i);
                noteDetails.this.finish();
            }
        });
        editbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(noteDetails.this,TextDetect.class);
                i.putExtra("index",index);
                i.putExtra("text",text);
                startActivity(i);
            }
        });
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      this.finish();
    }
}
