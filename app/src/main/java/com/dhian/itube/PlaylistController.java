package com.dhian.itube;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


public class PlaylistController {
    private DataBaseHelper dataBaseHelper;
    private String TABLE_NAME = "playlist";

    public PlaylistController(Context contexto) {
        dataBaseHelper = new DataBaseHelper(contexto);
    }



    public long newListItem(ListItem listItem) {
        SQLiteDatabase dataBase = dataBaseHelper.getWritableDatabase();
        ContentValues valuesForInsert = new ContentValues();
        valuesForInsert.put("url", listItem.getUrl());
        valuesForInsert.put("userId", listItem.getIdUser());
        return dataBase.insert(TABLE_NAME, null, valuesForInsert);
    }



    public ArrayList<ListItem> getPlayList(String userId) {
        ArrayList<ListItem> listItems = new ArrayList<>();
        SQLiteDatabase baseDeDatos = dataBaseHelper.getReadableDatabase();
        String[] fields = {"url", "id", "userId"};
        String selection = "userId = ?";
        String[] selectionArgs = {userId};
        Cursor cursor = baseDeDatos.query(
                TABLE_NAME,
                fields,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor == null) {
            return listItems;

        }
        if (!cursor.moveToFirst()) return listItems;


        do {
            String url = cursor.getString(0);
            long id = cursor.getLong(1);
            String userIdGet = cursor.getString(2);
            ListItem listItemGetFromDB = new ListItem(url, id, userIdGet);
            listItems.add(listItemGetFromDB);
        } while (cursor.moveToNext());

        cursor.close();
        return listItems;
    }
}