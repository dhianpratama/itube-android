package com.dhian.itube;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String NAME_DATA_BASE = "itube_db",
            NAME_TABLE_USER = "users",
            NAME_TABLE_PLAYLIST = "playlist";
    private static final int VERSION_DATA_BASE = 1;

    public DataBaseHelper(Context context) {
        super(context, NAME_DATA_BASE, null, VERSION_DATA_BASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s(id integer primary key autoincrement," +
                " fullname text, username text, password text)", NAME_TABLE_USER));
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s(id integer primary key autoincrement," +
                " url text, userId text)", NAME_TABLE_PLAYLIST));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
