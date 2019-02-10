package com.example.p0911asnctskrtt;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    MyTask mt;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG, "create MainActivity: " + this.hashCode());

        tv = (TextView) findViewById(R.id.tv);

        mt = (MyTask) getLastCustomNonConfigurationInstance();
        if (mt == null) {
            mt = new MyTask();
            mt.execute();
        }
        mt.link(this);

        Log.d(LOG_TAG, "create MyTask: " + mt.hashCode());
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        mt.unlink();
        return mt;
    }

    static class MyTask extends AsyncTask<String, Integer, Void> {

        MainActivity activity;

        void link(MainActivity activity) {
            this.activity = activity;
        }

        void unlink() {
            activity = null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            activity.tv.setText("i = " + values[0]);
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                for (int i = 1; i <= 10; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    publishProgress(i);
                    Log.d(activity.LOG_TAG, "i = " + i + ", MyTask: " + this.hashCode() +
                            ", MainActivity: " + activity.hashCode());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
