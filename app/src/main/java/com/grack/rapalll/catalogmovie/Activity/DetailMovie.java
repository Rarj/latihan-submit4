package com.grack.rapalll.catalogmovie.Activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.grack.rapalll.catalogmovie.API.ApiClient;
import com.grack.rapalll.catalogmovie.DB.FavHelper;
import com.grack.rapalll.catalogmovie.Data.ResultsItem;
import com.grack.rapalll.catalogmovie.R;
import com.grack.rapalll.catalogmovie.provider.FavColumns;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import static com.grack.rapalll.catalogmovie.provider.DBContract.CONTENT_URI;

public class DetailMovie extends AppCompatActivity {

    public static final String ITEM_MOVIE = "item_movie";


    int a;
    ResultsItem result;
    Toolbar toolbar;
    ImageView imgFav, backdrop, poster;
    TextView rate, releasedate;
    JustifiedTextView deskripsi;

    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgFav = findViewById(R.id.btn_favorite);


        backdrop = findViewById(R.id.img_backdrop);
        poster = findViewById(R.id.posterFilm);
        rate = findViewById(R.id.ratingFilm);
        releasedate = findViewById(R.id.releaseDateFilm);
        deskripsi = findViewById(R.id.deskripsiFilm);
        loadData();
//        loadDataSQLite();

        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite) FavoriteRemove();
                else FavoriteSave();

                isFavorite = !isFavorite;
                favoriteSet();
//                FavoriteSave();
                //untuk cek data sudah dapet atau belum
                Log.e(DetailMovie.class.getSimpleName(), "ID: " + result.getId() + "\n" +
                        "TITLE: " + toolbar.getTitle().toString() + "\n" +
                        "BD: " + result.getBackdropPath() + "\n" +
                        "POSTER: " + result.getPosterPath() + "\n" +
                        "VOTE AVERAGE: " + result.getVoteAverage() + "\n" +
                        "RELEASE DATE: " + result.getReleaseDate() + "\n" +
                        "OVERVIEW: " + result.getOverview() + "\n");

            }
        });
    }


    private void favoriteSet() {
        if (isFavorite) imgFav.setImageResource(R.drawable.ic_star_black_24dp);
        else imgFav.setImageResource(R.drawable.ic_star);
    }

    private void FavoriteSave() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavColumns.COLUMN_ID, result.getId());
        contentValues.put(FavColumns.COLUMN_TITLE, result.getTitle());
        contentValues.put(FavColumns.COLUMN_BACKDROP, result.getBackdropPath());
        contentValues.put(FavColumns.COLUMN_POSTER, result.getPosterPath());
        contentValues.put(FavColumns.COLUMN_RELEASE_DATE, result.getReleaseDate());
        contentValues.put(FavColumns.COLUMN_VOTE, result.getVoteAverage());
        contentValues.put(FavColumns.COLUMN_OVERVIEW, result.getOverview());

//        getContentResolver().insert(CONTENT_URI, contentValues);
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }

    private void FavoriteRemove() {
//        getContentResolver().delete(
//                Uri.parse(CONTENT_URI + "/" + result.getId()),
//                null,
//                null
//        );
        Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
    }

    //
    private void loadData() {
        result = new GsonBuilder().create().fromJson(getIntent().getStringExtra("movie"), ResultsItem.class);
        a = result.getId();
        toolbar.setTitle(result.getTitle());
        Glide.with(DetailMovie.this).load(ApiClient.LOAD_GAMBAR + result.getBackdropPath()).into(backdrop);
        Glide.with(DetailMovie.this).load(ApiClient.LOAD_GAMBAR + result.getPosterPath()).into(poster);
        rate.setText(getResources().getString(R.string.rating) + result.getVoteAverage());
        releasedate.setText(getResources().getString(R.string.release_date) + result.getReleaseDate());
        deskripsi.setText(result.getOverview());
    }


    private void loadDataSQLite(){
        FavHelper favHelper = new FavHelper(this);
        favHelper.open();

        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI + "/" + result.getId()),
                null, null,null,null
        );

        if (cursor != null){
            if (cursor.moveToFirst()) isFavorite = true;
            cursor.close();
        }
//        favoriteSet();

    }
}