package com.grack.rapalll.catalogmovie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.grack.rapalll.catalogmovie.DB.FavHelper;

import static com.grack.rapalll.catalogmovie.provider.DBContract.CONTENT_URI;

public class FavProvider extends ContentProvider {

    public static final int FAVORITE = 100;
    public static final int FAVORITE_ID = 101;

    private static final UriMatcher stUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        stUriMatcher.addURI(DBContract.AUTHORITY, FavColumns.TABLE_NAME, FAVORITE);
        stUriMatcher.addURI(DBContract.AUTHORITY, FavColumns.TABLE_NAME + "/#", FAVORITE_ID);
    }

    private FavHelper favHelper;



    @Override
    public boolean onCreate() {
        favHelper = new FavHelper(getContext());
        favHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;
        switch (stUriMatcher.match(uri)){
            case FAVORITE:
                cursor = favHelper.queryProvider();
                break;

            case FAVORITE_ID:
                cursor = favHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
                default:
                    cursor = null;
                    break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long added;
        switch ((stUriMatcher.match(uri))){
            case FAVORITE:
                added = favHelper.insertProvider(contentValues);
                break;
                default:
                    added = 0;
                    break;
        }
        if (added>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted;
        switch (stUriMatcher.match(uri)){
            case FAVORITE_ID:
                deleted= favHelper.deleteProvider(uri.getLastPathSegment());
                break;
                default:
                    deleted=0;
                    break;
        }

        if (deleted>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int updated;
        switch (stUriMatcher.match(uri)){
            case FAVORITE_ID:
                updated = favHelper.updateProvider(uri.getLastPathSegment(),contentValues);
                break;

                default:
                    updated=0;
                    break;
        }

        if (updated > 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return updated;
    }
}
