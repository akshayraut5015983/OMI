package com.omi.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.omi.app.models.NotificationModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notification.db";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NotificationModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + NotificationModel.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }


    public long insertNote(String title, String body) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(NotificationModel.COLUMN_Title, title);
        values.put(NotificationModel.COLUMN_Body, body);

        // insert row
        long id = db.insert(NotificationModel.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }


    public List<NotificationModel> getAllNotes() {
        List<NotificationModel> arrNotificationModels = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + NotificationModel.TABLE_NAME + " ORDER BY " +
                NotificationModel.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NotificationModel notificationModel = new NotificationModel();
                notificationModel.setId(cursor.getInt(cursor.getColumnIndex(NotificationModel.COLUMN_ID)));
                notificationModel.setTitle(cursor.getString(cursor.getColumnIndex(NotificationModel.COLUMN_Title)));
                notificationModel.setBody(cursor.getString(cursor.getColumnIndex(NotificationModel.COLUMN_Body)));
                notificationModel.setTimestamp(cursor.getString(cursor.getColumnIndex(NotificationModel.COLUMN_TIMESTAMP)));

                arrNotificationModels.add(notificationModel);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return arrNotificationModels;
    }

    public int getNotificationCount() {
        String countQuery = "SELECT  * FROM " + NotificationModel.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }


    public void deleteNotification(NotificationModel notificationModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NotificationModel.TABLE_NAME, NotificationModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(notificationModel.getId())});
        db.close();
    }

}
