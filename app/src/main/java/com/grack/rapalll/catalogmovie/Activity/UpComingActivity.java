package com.grack.rapalll.catalogmovie.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.grack.rapalll.catalogmovie.API.ApiClient;
import com.grack.rapalll.catalogmovie.API.Apiinterface;
import com.grack.rapalll.catalogmovie.Adapter.MovieAdapter;
import com.grack.rapalll.catalogmovie.Data.Movie;
import com.grack.rapalll.catalogmovie.Data.Result;
import com.grack.rapalll.catalogmovie.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpComingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    ProgressDialog progressDialog;
    List<Result> movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        recyclerView = findViewById(R.id.movieView);
        progressDialog = new ProgressDialog(this);

        setupList();
        loadData();
    }

    private void setupList() {
        adapter = new MovieAdapter(movie);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setItemViewCacheSize(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadData() {
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Apiinterface api = ApiClient.getRetrofit().create(Apiinterface.class);
        Call<Movie> call = api.getUpComing();
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()){
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

    private void loadFailed() {
        Toast.makeText(this, R.string.err_load_failed, Toast.LENGTH_SHORT).show();
    }
}