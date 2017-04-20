package com.shubham.writeitout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class searchResult extends AppCompatActivity {
    String SearchWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent intent=getIntent();
        SearchWord=intent.getStringExtra("word");
    }
}
