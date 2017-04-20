package com.shubham.writeitout;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.myscript.atk.sltw.SingleLineWidget;
import com.myscript.atk.sltw.SingleLineWidgetApi;

public class line extends AppCompatActivity implements
        SingleLineWidgetApi.OnConfiguredListener,
        SingleLineWidgetApi.OnTextChangedListener {
    private static final String TAG = "LineDemo";

    private SingleLineWidgetApi widget;
    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        edt=(EditText)findViewById(R.id.edt1);
        widget = (SingleLineWidget) findViewById(R.id.widget);
        if (!widget.registerCertificate(MyCertificate.getBytes()))
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Please use a valid certificate.");
            dlgAlert.setTitle("Invalid certificate");
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    //dismiss the dialog
                }
            });
            dlgAlert.create().show();
            return;
        }

        widget.setOnConfiguredListener(this);
        widget.setOnTextChangedListener(this);

        // references assets directly from the APK to avoid extraction in application
        // file system
        widget.addSearchDir("zip://" + getPackageCodePath() + "!/assets/conf");

        // The configuration is an asynchronous operation. Callbacks are provided to
        // monitor the beginning and end of the configuration process and update the UI
        // of the input method accordingly.
        //
        // "en_US" references the en_US bundle name in conf/en_US.conf file in your assets.
        // "cur_text" references the configuration name in en_US.conf
        widget.configure("en_US", "cur_text");
    }

    @Override
    protected void onDestroy()
    {
        widget.setOnTextChangedListener(null);
        widget.setOnConfiguredListener(null);

        super.onDestroy();
    }

    @Override
    public void onConfigured(SingleLineWidgetApi widget, boolean success)
    {
        if(!success)
        {
            Toast.makeText(getApplicationContext(), widget.getErrorString(), Toast.LENGTH_LONG).show();
            Log.e(TAG, "Unable to configure the Single Line Widget: " + widget.getErrorString());
            return;
        }
        Toast.makeText(getApplicationContext(), "Single Line Widget Configured", Toast.LENGTH_SHORT).show();
        if(BuildConfig.DEBUG)
            Log.d(TAG, "Single Line Widget configured!");
    }

    @Override
    public void onTextChanged(SingleLineWidgetApi widget, String s, boolean intermediate)
    {
     //   Toast.makeText(getApplicationContext(), "Recognition update", Toast.LENGTH_SHORT).show();
        if(BuildConfig.DEBUG)
        {
            Log.d(TAG, "Single Line Widget recognition: " + widget.getText());
            edt.setText(widget.getText());
        }
    }
    }

