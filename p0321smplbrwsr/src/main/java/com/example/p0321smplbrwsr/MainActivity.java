package com.example.p0321smplbrwsr;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (findViewById(R.id.btnWeb)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://ya.ru"))); // http не поддерживается
                //startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse("http://ya.ru")), "Choose Application"));
            }
        });
    }
}
