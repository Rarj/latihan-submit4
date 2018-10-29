package com.grack.rapalll.catalogmovie.Activity;


import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.grack.rapalll.catalogmovie.API.ApiClient;
import com.grack.rapalll.catalogmovie.API.Apiinterface;
import com.grack.rapalll.catalogmovie.Adapter.MovieAdapter;
import com.grack.rapalll.catalogmovie.Data.Movie;
import com.grack.rapalll.catalogmovie.Data.Result;
import com.grack.rapalll.catalogmovie.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    Toolbar toolbar;
    MaterialSearchView searchView;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    MovieAdapter adapter;
    List<Result> movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        recyclerView = findViewById(R.id.movieView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new MovieAdapter(movie);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setItemViewCacheSize(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);
        searchView = findViewById(R.id.search_material);
        searchQuery();
    }

    private void searchQuery() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                cariMovie(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
    }

    private void cariMovie(String query) {
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Apiinterface apiSearch = ApiClient.getRetrofitSearch().create(Apiinterface.class);

        Call<Movie> call = apiSearch.getSearchMovie(query);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    Movie movies = response.body();
                    adapter.setData(movies.getResults());
                    recyclerView.setAdapter(adapter);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.cari);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();

        }
    }
}
