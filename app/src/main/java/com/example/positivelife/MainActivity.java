package com.example.positivelife;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private String selectedDate = ""; // 初期値を空の文字列で設定

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);

        Log.d("MainActivity", "Selected date: " + selectedDate);

        // カレンダーの日付選択リスナーを設定
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // 1を加えて正しい月を取得
                int correctMonth = month + 1;

                // デバッグログで選択された日付を出力
                selectedDate = formatDate(year, correctMonth, dayOfMonth); // ここでview.getDate()を使用
                Log.d("MainActivity", "CalendarView date: " + selectedDate);
                Log.d("MainActivity", "Year: " + year + ", Month: " + correctMonth + ", Day: " + dayOfMonth);

                // 他の処理を行う（例：ログ出力など）
                Log.d("MainActivity", "Selected date: " + selectedDate);

                // カレンダーの日付変更時にトースト表示
                showToast(selectedDate);
            }
        });
    }

    // カレンダーの日付を文字列にフォーマットするメソッド
    public String formatDate(int year, int month, int dayOfMonth) {
        return String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, dayOfMonth);
    }

    // トースト表示を行うメソッド
    public void showToast(String message) {
        Toast.makeText(this, "選択された日付: " + message, Toast.LENGTH_SHORT).show();
    }

    // 閲覧ボタンのクリックリスナー（XMLで定義したonClick属性のメソッド）
    public void onViewButtonClick(View view) {
        // 選択した日付を取得
        String selectedDateForIntent = selectedDate;
        // BrowseActivityに遷移
        Intent intent = new Intent(MainActivity.this, BrowseActivity.class);
        intent.putExtra("SELECTED_DATE", selectedDateForIntent);
        startActivity(intent);
    }

    // 登録ボタンのクリックリスナー（XMLで定義したonClick属性のメソッド）
    public void onRegisterButtonClick(View view) {
        // 選択した日付を取得
        String selectedDateForIntent = selectedDate;
        // WritePositiveActivityに遷移
        Intent intent = new Intent(MainActivity.this, WritePositiveActivity.class);
        intent.putExtra("SELECTED_DATE", selectedDateForIntent);
        startActivity(intent);
    }
}