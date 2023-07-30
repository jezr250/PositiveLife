package com.example.positivelife;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.widget.DatePicker;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class WritePositiveActivity extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private Spinner positiveSpinner1;
    private Spinner positiveSpinner2;
    private Spinner positiveSpinner3;

    private DatabaseHelper dbHelper;

    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_positive);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        selectedDate = intent.getStringExtra("SELECTED_DATE");
        Log.d("WritePositiveActivity", "selectedDate: " + selectedDate); // ログ出力

        editText1 = findViewById(R.id.memoEditText1);
        editText2 = findViewById(R.id.memoEditText2);
        editText3 = findViewById(R.id.memoEditText3);

        positiveSpinner1 = findViewById(R.id.positiveEventSpinner1);
        positiveSpinner2 = findViewById(R.id.positiveEventSpinner2);
        positiveSpinner3 = findViewById(R.id.positiveEventSpinner3);

        setSpinnerInitialValue(positiveSpinner1);
        setSpinnerInitialValue(positiveSpinner2);
        setSpinnerInitialValue(positiveSpinner3);

        // 保存ボタンのリスナーをセット
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToDB();
            }
        });

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

    private void setSpinnerInitialValue(Spinner spinner) {
        List<String> spinnerItems = getHappyEventList();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    private List<String> getHappyEventList() {
        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("Happy Events List");
        spinnerItems.add("笑い合う友達と楽しい時間");
        spinnerItems.add("美味しい食事を楽しむ");
        spinnerItems.add("お気に入りの本に没頭");
        spinnerItems.add("音楽に包まれてリラックス");
        spinnerItems.add("自然を感じる朝の散歩");
        spinnerItems.add("予期せぬ感謝のメッセージ");
        spinnerItems.add("心に響く名言に感動");
        spinnerItems.add("思わず笑う楽しい会話");
        spinnerItems.add("応援に温かさを感じる");
        spinnerItems.add("目標達成の喜びに満たされる");
        return spinnerItems;
    }

    private void saveDataToDB() {
        String data1 = editText1.getText().toString().trim();
        String data2 = editText2.getText().toString().trim();
        String data3 = editText3.getText().toString().trim();
        String spinner1Data = (String) positiveSpinner1.getSelectedItem();
        String spinner2Data = (String) positiveSpinner2.getSelectedItem();
        String spinner3Data = (String) positiveSpinner3.getSelectedItem();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // データの更新・追加処理
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_DATA_1, data1);
        values.put(DatabaseHelper.COLUMN_DATA_2, data2);
        values.put(DatabaseHelper.COLUMN_DATA_3, data3);
        values.put(DatabaseHelper.COLUMN_SPINNER_1, spinner1Data);
        values.put(DatabaseHelper.COLUMN_SPINNER_2, spinner2Data);
        values.put(DatabaseHelper.COLUMN_SPINNER_3, spinner3Data);
        values.put(DatabaseHelper.COLUMN_DATE, selectedDate); // 日付をデータベースに保存

        long newRowId = db.insertWithOnConflict(DatabaseHelper.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

        if (newRowId != -1) {
            Toast.makeText(this, "データを保存しました", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "データの保存に失敗しました", Toast.LENGTH_SHORT).show();
        }
    }

    private String formatDate(long dateInMillis) {
        // 日付をフォーマットするメソッドの実装を仮定しています
        // 実際の実装は、具体的な要件に基づいて行ってください
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date(dateInMillis));
    }
}