package com.shubham.writeitout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.myscript.atk.math.widget.MathWidgetApi;

public class math extends AppCompatActivity {

    private MathWidgetApi mWidget;
    boolean var=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);
        mWidget = (MathWidgetApi) findViewById(R.id.mathWidget);
        Log.e("widget ",mWidget+"");
        var=mWidget.registerCertificate(com.shubham.writeitout.MyCertificate.getBytes());
        Log.e("print",var+"");
        if (!mWidget.registerCertificate(com.shubham.writeitout.MyCertificate.getBytes()))
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

        mWidget.clearSearchPath();
        mWidget.addSearchDir("zip://" + getPackageCodePath() + "!/assets/conf/");
        mWidget.configure("math", "standard");

        // Configure clear button
        final View clearButton = findViewById(R.id.action_clear);
        if (clearButton != null)
        {
            clearButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(final View view)
                {
                    mWidget.clear(true);
                }
            });
        }
    }

    @Override
    protected void onDestroy()
    {
        if (mWidget != null)
        {
            mWidget.release();
            mWidget = null;
        }

        super.onDestroy();
    }

}

