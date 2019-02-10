package com.example.p0391sqllupgrdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    final String DB_NAME = "staff";
    final int DB_VERSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbh = new DBHelper(this);
        SQLiteDatabase db = dbh.getWritableDatabase();
        Log.d(LOG_TAG, " --- Staff db v." + db.getVersion() + " --- ");
        writeStaff(db);
        dbh.close();
    }

    private void writeStaff(SQLiteDatabase db) {
        Cursor c = db.rawQuery("SELECT * FROM people;", null);
        logCursor(c, "Table people");
        c.close();

        c = db.rawQuery("SELECT * FROM position;", null);
        logCursor(c, "Table position");
        c.close();

        String sqlQuery = "SELECT PL.name as Name, PS.name as Position, salary as Salary " +
                "FROM people as PL " +
                "INNER JOIN position as PS " +
                "ON PL.posid = PS.id ";
        c = db.rawQuery(sqlQuery, null);
        logCursor(c, "INNER JOIN");
        c.close();
    }

    void logCursor(Cursor c, String title) {
        if (c != null) {
            if (c.moveToFirst()) {
                Log.d(LOG_TAG, title + ". " + c.getCount() + " rows");
                StringBuilder sb = new StringBuilder();
                do {
                    sb.setLength(0);
                    for (String cn : c.getColumnNames()) {
                        sb.append(cn + " = " + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, sb.toString());
                } while (c.moveToNext());
            }
        } else {
            Log.d(LOG_TAG, title + ". Cursor is null");
        }
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");

            String[] people_name = {"Иван", "Марья", "Петр", "Антон",
                    "Даша", "Борис", "Костя", "Игорь"};
            int[] people_posid = {2, 3, 2, 2, 3, 1, 2, 4};

            int[] position_id = {1, 2, 3, 4};
            String[] position_name = {"Программер", "Бухгалтер", "Программер", "Программер",
                    "Бухгалтер", "Директор", "Программер", "Охранник"};
            int[] position_salary = {15000, 13000, 10000, 8000};

            ContentValues cv = new ContentValues();

            db.execSQL("CREATE TABLE position (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name TEXT, " +
                    "salary INTEGER" + ");");
            for (int i = 0; i < position_id.length; i++) {
                cv.clear();
                cv.put("id", position_id[i]);
                cv.put("name", position_name[i]);
                cv.put("salary", position_salary[i]);
                db.insert("position", null, cv);
            }

            db.execSQL("CREATE TABLE people (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "posid INTEGER" + ");");
            for (int i = 0; i < people_name.length; i++) {
                cv.clear();
                cv.put("name", people_name[i]);
                cv.put("posid", people_posid[i]);
                db.insert("people", null, cv);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(LOG_TAG, " --- onUpgrade database from " + oldVersion +
                    " to " + newVersion + " version --- ");
            if (oldVersion == 1 && newVersion == 2) {
                ContentValues cv = new ContentValues();

                int[] position_id = {1, 2, 3, 4};
                String[] position_name = {"Директор", "Программер", "Бухгалтер", "Охранник"};
                int[] position_salary = {15000, 13000, 10000, 8000};

                db.beginTransaction();
                try {
                    db.execSQL("CREATE TABLE position (" +
                            "id INTEGER PRIMARY KEY, " +
                            "name TEXT, " +
                            "salary INTEGER" + ");");
                    for (int i = 0; i < position_id.length; i++) {
                        cv.clear();
                        cv.put("id", position_id[i]);
                        cv.put("name", position_name[i]);
                        cv.put("salary", position_salary[i]);
                        db.insert("position", null, cv);
                    }

                    db.execSQL("ALTER TABLE people " +
                            "ADD COLUMN posid INTEGER" + ";");
                    for (int i = 0; i < position_id.length; i++) {
                        cv.clear();
                        cv.put("posid", position_id[i]);
                        db.update("people", cv, "position = ?", new String[]{position_name[i]});
                    }

                    db.execSQL("CREATE TEMPORARY TABLE people_tmp (" +
                            "id INTEGER," +
                            "name TEXT," +
                            "position TEXT," +
                            "posid INTEGER" + ");");

                    db.execSQL("INSERT INTO people_tmp " +
                            "SELECT id, name, position, posid " +
                            "FROM people");

                    db.execSQL("DROP TABLE people;");

                    db.execSQL("CREATE TABLE people (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "name TEXT, " +
                            "posid INTEGER" + ");");

                    db.execSQL("INSERT INTO people " +
                            "SELECT id, name, posid " +
                            "FROM people" + ";");
                    db.execSQL("DROP TABLE people_tmp" + ";");

                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            }
        }
    }
}
