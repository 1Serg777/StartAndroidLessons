package com.example.p0401layoutinfl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater lInflater = getLayoutInflater();

        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        View view1 = lInflater.inflate(R.layout.text, linearLayout1, true);
        ViewGroup.LayoutParams lp1 = view1.getLayoutParams();

        Log.d(LOG_TAG, "Class of view1: " + view1.getClass().toString());
        Log.d(LOG_TAG, "Class layoutParams of view1 is null: " + lp1.getClass().toString());

        RelativeLayout relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout1);
        View view2 = lInflater.inflate(R.layout.text, relativeLayout1, true);
        ViewGroup.LayoutParams lp2 = view2.getLayoutParams();

        Log.d(LOG_TAG, "Class of view2: " + view2.getClass().toString());
        Log.d(LOG_TAG, "Class of layoutParams of view2 is null: " + lp2.getClass().toString());
    }
}
