package com.dhian.itube;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


public class UserController {
    private DataBaseHelper dataBaseHelper;
    private String TABLE_NAME = "users";

    public UserController(Context contexto) {
        dataBaseHelper = new DataBaseHelper(contexto);
    }


    public long newUser(User user) {
        SQLiteDatabase dataBase = dataBaseHelper.getWritableDatabase();
        ContentValues valuesForInsert = new ContentValues();
        valuesForInsert.put("fullname", user.getFullname());
        valuesForInsert.put("username", user.getUsername());
        valuesForInsert.put("password", user.getPassword());
        return dataBase.insert(TABLE_NAME, null, valuesForInsert);
    }


    public ArrayList<User> getUsers() {
        ArrayList<User> list = new ArrayList<>();
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
        String[] fields = {"fullname", "username", "id", "password"};
        Cursor cursor = database.query(
                TABLE_NAME,
                fields,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor == null) {
            return list;

        }
        if (!cursor.moveToFirst()) return list;


        do {
            String title = cursor.getString(0);
            String description = cursor.getString(1);
            long id = cursor.getLong(2);
            String dueDate = cursor.getString(3);
            User taskGetFromDB = new User(title, description, id, dueDate);
            list.add(taskGetFromDB);
        } while (cursor.moveToNext());

        cursor.close();
        return list;
    }

    public int login(User user) {
        ArrayList<User> list = new ArrayList<>();
        SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
        String[] fields = {"fullname", "username", "id", "password"};
        Cursor cursor = database.query(
                TABLE_NAME,
                fields,
                " username = '" + user.getUsername() + "'",
                null,
                null,
                null,
                null
        );

        if (cursor == null) {
            return -1;

        }

        if (cursor.getCount() != 0) {
            if (!cursor.moveToFirst()) return -1;

            String password = cursor.getString(3);
            long id = cursor.getLong(2);
            cursor.close();
            if (password.equals(user.getPassword())) {
                return Integer.valueOf(String.valueOf(id));
            } else {
                return -1;
            }
        } else {
            cursor.close();
            return -1;
        }
    }
}