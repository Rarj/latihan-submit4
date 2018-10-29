package com.grack.rapalll.catalogmovie.provider;

import android.database.Cursor;
import android.net.Uri;

public class DBContract {

    public static final String AUTHORITY = "com.grack.rapalll.catalogmovie";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(FavColumns.TABLE_NAME)
            .build();

    public static String getColumns(Cursor cursor, String columnsName){
        return cursor.getString(cursor.getColumnIndex(columnsName));
    }
    public static int getColumnsInt(Cursor cursor, String columnsName){
        return cursor.getInt(cursor.getColumnIndex(columnsName));
    }
    public static double getColumnsDouble(Cursor cursor, String columnsName){
        return cursor.getDouble(cursor.getColumnIndex(columnsName));
    }
    public static long getColumnsLong(Cursor cursor, String columnsName){
        return cursor.getLong(cursor.getColumnIndex(columnsName));
    }

}
