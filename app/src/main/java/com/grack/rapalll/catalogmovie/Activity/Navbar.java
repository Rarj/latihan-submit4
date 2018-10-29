package com.grack.rapalll.catalogmovie.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.grack.rapalll.catalogmovie.API.ApiClient;
import com.grack.rapalll.catalogmovie.API.Apiinterface;
import com.grack.rapalll.catalogmovie.Adapter.MovieAdapter;
import com.grack.rapalll.catalogmovie.Data.Movie;
import com.grack.rapalll.catalogmovie.Data.Result;
import com.grack.rapalll.catalogmovie.FavoriteActivity;
import com.grack.rapalll.catalogmovie.R;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class Navbar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    RecyclerView mView;
    MovieAdapter adapter;
    List<Result> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar()
                .setTitle(getResources()
                        .getString(R.string.catalog_movie));


        mView = (RecyclerView) findViewById(R.id.movieView);
        mView.setLayoutManager(new LinearLayoutManager(Navbar.this));


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        movieLoad("popular");
    }

    private void movieLoad(String value) {
        Apiinterface api = ApiClient.getRetrofit().create(Apiinterface.class);
        retrofit2.Call<Movie> call = null;
        if (value.equals("popular")) {
            call = api.getPopular();
        } else if (value.equals("top_rated"))
            call = api.getTopRated();

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(retrofit2.Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                adapter = new MovieAdapter(results);
                adapter.setData(movie.getResults());
                mView.setAdapter(adapter);
            }

            @Override
            public void onFailure(retrofit2.Call<Movie> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_upcoming) {
            Intent upComing = new Intent(Navbar.this, UpComingActivity.class);
            startActivity(upComing);
            // Handle the camera action
        } else if (id == R.id.nav_nowplaying) {
            Intent nowPlaying = new Intent(Navbar.this, NowPlayingActivity.class);
            startActivity(nowPlaying);

        } else if (id == R.id.nav_search) {
            Intent cari = new Intent(Navbar.this, SearchActivity.class);
            startActivity(cari);


//
        } else if (id == R.id.nav_favorite) {
            Intent favorite = new Intent(Navbar.this, FavoriteActivity.class);
            startActivity(favorite);

        } else if (id == R.id.nav_bahasa) {
            Intent cBahasa = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(cBahasa);
//
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name) + "\n\n" + getString(R.string.share_description));
            startActivity(Intent.createChooser(intent, getResources().getString(R.string.share)));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
