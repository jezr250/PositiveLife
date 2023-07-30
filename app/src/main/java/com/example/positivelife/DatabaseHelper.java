package com.example.positivelife;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "positive_life.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_NAME = "positive_events";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DATA_1 = "data_1";
    public static final String COLUMN_DATA_2 = "data_2";
    public static final String COLUMN_DATA_3 = "data_3";
    public static final String COLUMN_SPINNER_1 = "spinner_1";
    public static final String COLUMN_SPINNER_2 = "spinner_2";
    public static final String COLUMN_SPINNER_3 = "spinner_3";


    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_DATE + " TEXT PRIMARY KEY," +
                    COLUMN_DATA_1 + " TEXT," +
                    COLUMN_DATA_2 + " TEXT," +
                    COLUMN_DATA_3 + " TEXT," +
                    COLUMN_SPINNER_1 + " TEXT," +
                    COLUMN_SPINNER_2 + " TEXT," +
                    COLUMN_SPINNER_3 + " TEXT" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // データベースのアップグレード処理をここに実装する
        if (oldVersion == 1 && newVersion == 2) {
            // バージョン1から2へのアップグレード処理を実行する例
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_SPINNER_1 + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_SPINNER_2 + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_SPINNER_3 + " TEXT");
        }
    }
}