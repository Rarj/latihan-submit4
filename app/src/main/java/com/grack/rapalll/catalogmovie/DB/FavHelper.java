package com.grack.rapalll.catalogmovie.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.grack.rapalll.catalogmovie.provider.FavColumns;

import static android.provider.BaseColumns._ID;

public class FavHelper {

    public static  String TABLE_NAME = FavColumns.TABLE_NAME;

    private Context context;
    private DBHelper dbHelper;

    private SQLiteDatabase database;

    public FavHelper(Context context){
        this.context = context;
    }

    public FavHelper open() throws SQLException{
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public Cursor queryProvider(){
        return database.query(
                TABLE_NAME,null,null,null,null,null, _ID + "DESC"
        );
    }


    public Cursor queryByIdProvider(String id) {
        return database.query(
                TABLE_NAME,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null
        );
    }

    public long insertProvider(ContentValues values) {
        return database.insert(
                TABLE_NAME,
                null,
                values
        );
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(
                TABLE_NAME,
                values,
                _ID + " = ?",
                new String[]{id}
        );
    }

    public int deleteProvider(String id) {
        return database.delete(
                TABLE_NAME,
                _ID + " = ?",
                new String[]{id}
        );
    }
}


