package com.shubham.writeitout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class searchResult extends AppCompatActivity {
    String SearchWord = "";
    String finWord[] = new String[10];
    String lan = "en";
    static int count = 0;
    TextView rslt1, rslt2, rslt3,ttle;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent intent = getIntent();
        SearchWord = intent.getStringExtra("word");
        rslt1 = (TextView) findViewById(R.id.rslt1);
        rslt2 = (TextView) findViewById(R.id.rslt2);
        rslt3 = (TextView) findViewById(R.id.rslt3);
        ttle=(TextView)findViewById(R.id.ttle);

        rslt1.setText("");
        rslt2.setText("");
        rslt3.setText("");


        if (SearchWord.length() > 0) {
            if (SearchWord.contains(" "))
                finWord = SearchWord.split(" ");
            else
                finWord[0] = SearchWord;
        } else {
            Toast.makeText(searchResult.this, "Incorrect word", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(searchResult.this, TextDetect.class);
            startActivity(i);
            this.finish();
        }
        if (isonline()) {

            ttle.setText("Searching For "+finWord[0]);
            try {

                new CallbackTask().execute(dictionaryEntries(finWord[0], lan));
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }
            try {
                new CallbackTask().execute(dictionaryEntriessyn(finWord[0], lan));
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }
            try {
                new CallbackTask().execute(dictionaryEntriesant(finWord[0], lan));
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();

        }
        progressDialog.dismiss();
    } else {

        Toast.makeText(searchResult.this, "Network Unavaliable", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
        this.finish();
    }


    }

    private String dictionaryEntries(String word, String lan) {
        final String language = lan.toLowerCase();
        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required
        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id;
    }

    private String dictionaryEntriessyn(String word, String lan) {
        final String language = lan.toLowerCase();
        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required
        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id + "/synonyms";
    }

    private String dictionaryEntriesant(String word, String lan) {
        final String language = lan.toLowerCase();
        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required
        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id + "/antonyms";
    }

    public boolean isonline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }


    class CallbackTask extends AsyncTask<String, Integer, String> {


        protected String doInBackground(String... params) {

            //TODO: replace with your own app id and app key ,this is for demo
            final String app_id = "d4218d25";
            final String app_key = "387bb3182a49099fb7633766279c0f97";
            try {
                URL url = new URL(params[0]);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("app_id", app_id);
                urlConnection.setRequestProperty("app_key", app_key);

                // read the output from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                return stringBuilder.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return null;

            }
        }

        protected void onPostExecute(String result) {
            if (count == 0) {

                try {
                    model obj = JsonParser.parsefeed(result);
                    count = 1;
                    rslt1.setText("Definition : " + obj.getDefinitions() + "\n\n" + "Example : " + obj.getExamples() + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(searchResult.this, "No data available", Toast.LENGTH_SHORT).show();
                    count = 0;
                    progressDialog.dismiss();
                    searchResult.this.finish();
                }
            } else if (count == 1) {
                try {
                    model obj1 = JsonParserSyn.parsefeed(result);
                    count = 2;
                    rslt2.setText("Synonyms : " + obj1.getSynonym() + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(searchResult.this, "Synonym not available", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            } else if (count == 2) {
                try {
                    model obj2 = JsonParserAnt.parsefeed(result);
                    count = 0;
                    rslt3.setText("Antonyms : " + obj2.getAntonym());

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(searchResult.this, "Antonym not available", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            } else {
            }
        progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(searchResult.this);
            progressDialog.setTitle("Fetching Data...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }
    }
}

