package com.grack.rapalll.catalogmovie.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.grack.rapalll.catalogmovie.Data.Movie;
import com.grack.rapalll.catalogmovie.provider.FavColumns;

public class DBHelper extends SQLiteOpenHelper {


    public static String DATABASE_NAME = "Cataloge_Movie";
    public static int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_MOVIE = "create table " + FavColumns.TABLE_NAME +
                " (" + FavColumns.COLUMN_ID + " integer primary key autoincrement, " +
                FavColumns.COLUMN_TITLE + " text not null," +
                FavColumns.COLUMN_BACKDROP + " text not null," +
                FavColumns.COLUMN_POSTER + " text not null," +
                FavColumns.COLUMN_RELEASE_DATE + " text not null," +
                FavColumns.COLUMN_VOTE + " text not null," +
                FavColumns.COLUMN_OVERVIEW + " text not null " +
                ");";

        sqLiteDatabase.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL( "DROP TABLE IF EXISTS " + FavColumns.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

}
