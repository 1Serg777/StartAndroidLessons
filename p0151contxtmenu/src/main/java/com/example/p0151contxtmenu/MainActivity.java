package com.example.p0151contxtmenu;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final int MENU_COLOR_RED = 1;
    final int MENU_COLOR_GREEN = 2;
    final int MENU_COLOR_BLUE = 3;

    final int MENU_SIZE_22 = 4;
    final int MENU_SIZE_26 = 5;
    final int MENU_SIZE_28 = 6;

    TextView tvColor, tvSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvColor = (TextView) findViewById(R.id.tvColor);
        tvSize = (TextView) findViewById(R.id.tvSize);

        registerForContextMenu(tvColor);
        registerForContextMenu(tvSize);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.tvColor:
                //menu.add(0, MENU_COLOR_RED, 0, "Red");
                //menu.add(0, MENU_COLOR_GREEN, 0, "Green");
                //menu.add(0, MENU_COLOR_BLUE, 0, "Blue");
                getMenuInflater().inflate(R.menu.menucolorsize, menu);
                menu.setGroupVisible(R.id.menuGroupSize, false);
                break;
            case R.id.tvSize:
                //menu.add(0, MENU_SIZE_22, 0, "22");
                //menu.add(0, MENU_SIZE_26, 0, "26");
                //menu.add(0, MENU_SIZE_28, 0, "28");
                getMenuInflater().inflate(R.menu.menucolorsize, menu);
                menu.setGroupVisible(R.id.menuGroupColor, false);
                break;
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.colorRed:
                tvColor.setTextColor(Color.RED);
                tvColor.setText("Text color = red");
                break;
            case R.id.colorGreen:
                tvColor.setTextColor(Color.GREEN);
                tvColor.setText("Text color = green");
                break;
            case R.id.colorBlue:
                tvColor.setTextColor(Color.BLUE);
                tvColor.setText("Text color = blue");
                break;

            case R.id.textSize22:
                tvSize.setTextSize(22);
                tvSize.setText("Text size = 22");
                break;
            case R.id.textSize26:
                tvSize.setTextSize(26);
                tvSize.setText("Text size = 26");
                break;
            case R.id.textSize28:
                tvSize.setTextSize(28);
                tvSize.setText("Text size = 28");
                break;
        }
        return super.onContextItemSelected(item);
    }
}
