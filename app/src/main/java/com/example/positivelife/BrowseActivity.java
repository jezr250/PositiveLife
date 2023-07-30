package com.example.positivelife;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class BrowseActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        dbHelper = new DatabaseHelper(this);

        // MainActivityから選択した日付を受け取る
        Intent intent = getIntent();
        String selectedDate = intent.getStringExtra("SELECTED_DATE");

        // データベースから該当するデータを取得し、TextViewとEditTextに表示
        displayDataFromDB(selectedDate);

        // "＜ カレンダー" テキストのクリックリスナーをセット
        TextView calendarLink = findViewById(R.id.calendarLink);
        calendarLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // "＜ カレンダー" テキストがクリックされたときに、Activityを終了して元の画面に戻る
                finish();
            }
        });
    }

    private void displayDataFromDB(String selectedDate) {
        // データベースから該当するデータを取得
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DatabaseHelper.COLUMN_DATA_1,
                DatabaseHelper.COLUMN_DATA_2,
                DatabaseHelper.COLUMN_DATA_3,
                DatabaseHelper.COLUMN_SPINNER_1,
                DatabaseHelper.COLUMN_SPINNER_2,
                DatabaseHelper.COLUMN_SPINNER_3
        };
        String selection = DatabaseHelper.COLUMN_DATE + " = ?";
        String[] selectionArgs = {selectedDate};
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // データが存在する場合はTextViewとEditTextに表示
        if (cursor != null && cursor.moveToFirst()) {
            String data1 = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATA_1));
            String data2 = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATA_2));
            String data3 = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATA_3));
            String spinner1Data = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SPINNER_1));
            String spinner2Data = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SPINNER_2));
            String spinner3Data = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SPINNER_3));

            // データを表示するTextViewとEditTextを取得し、データをセット
            TextView positiveEventTextView1 = findViewById(R.id.positiveEventTextView1);
            EditText memoEditText1 = findViewById(R.id.memoEditText1);
            positiveEventTextView1.setText(spinner1Data);
            memoEditText1.setText(data1);

            TextView positiveEventTextView2 = findViewById(R.id.positiveEventTextView2);
            EditText memoEditText2 = findViewById(R.id.memoEditText2);
            positiveEventTextView2.setText(spinner2Data);
            memoEditText2.setText(data2);

            TextView positiveEventTextView3 = findViewById(R.id.positiveEventTextView3);
            EditText memoEditText3 = findViewById(R.id.memoEditText3);
            positiveEventTextView3.setText(spinner3Data);
            memoEditText3.setText(data3);


            cursor.close();
        }

        db.close();
    }
}