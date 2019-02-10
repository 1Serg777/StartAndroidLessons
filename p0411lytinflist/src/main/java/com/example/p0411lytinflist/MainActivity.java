package com.example.p0411lytinflist;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String[] name = {"Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь", "Сергей",
            "Андрей", "Дмитрий"};
    String[] position = {"Программер", "Бухгалтер", "Программер", "Программер", "Бухгалтер",
            "Директор", "Программер", "Охранник", "Программер", "Директор", "Бухгалтер",};
    int[] salary = {13000, 10000, 13000, 13000, 10000, 15000, 13000, 8000, 13000, 15000, 10000};

    int[] colors = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colors[0] = Color.parseColor("#559966CC");
        colors[1] = Color.parseColor("#55336699");

        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);

        LayoutInflater layoutInflater = getLayoutInflater();

        for (int i = 0; i < name.length; i++) {
            Log.d("myLogs", "i = " + i);

            View item = layoutInflater.inflate(R.layout.item, linearLayout1, false);

            TextView tvName = (TextView) item.findViewById(R.id.tvName);
            tvName.setText(name[i]);

            TextView tvPosition = (TextView) item.findViewById(R.id.tvPosition);
            tvPosition.setText(new StringBuilder("Должность: " + position[i]));

            TextView tvSalary = (TextView) item.findViewById(R.id.tvSalary);
            tvSalary.setText(new StringBuilder("Оклад: " + String.valueOf(salary[i])));

            item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            item.setBackgroundColor(colors[i % 2]);
            linearLayout1.addView(item);
        }
    }
}
