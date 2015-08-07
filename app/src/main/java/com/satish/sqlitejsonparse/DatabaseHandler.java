package com.satish.sqlitejsonparse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satish on 1/7/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "user_database";
    private final static String TABLE_NAME = "userdata";
    private final static int DATABASE_VERSION = 1;
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "name";
    private static final String Email="email";
    private static final String PHONE = "contact_number";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + USER_ID + " TEXT PRIMARY KEY," + USER_NAME + " TEXT," +Email+ " TEXT UNIQUE ,"
                + PHONE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS" + TABLE_NAME);
        onCreate(db);

    }

    public void addUsers(ListDataModel userData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, userData.getId());
        values.put(USER_NAME, userData.getName());
        values.put(PHONE, userData.getPhone());
        values.put(Email,userData.getEmail());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public List<ListDataModel> getAllUsers() {
        List<ListDataModel> userList = new ArrayList<ListDataModel>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ListDataModel data = new ListDataModel();
                data.setId(cursor.getString(0));
                data.setName(cursor.getString(1));
                data.setEmail(cursor.getString(2));
                data.setPhone(cursor.getString(3));
                // Adding contact to list
                userList.add(data);
            } while (cursor.moveToNext());
        }

        return userList;
    }
}
