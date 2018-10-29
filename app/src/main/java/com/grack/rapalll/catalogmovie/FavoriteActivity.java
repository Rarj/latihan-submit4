package com.grack.rapalll.catalogmovie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grack.rapalll.catalogmovie.Adapter.FavAdapter;

import static com.grack.rapalll.catalogmovie.provider.DBContract.CONTENT_URI;

public class FavoriteActivity extends AppCompatActivity {


    //    View view;
    Context context;
    Cursor list;
    FavAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = (RecyclerView) findViewById(R.id.movieView);

        adapter = new FavAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        setupList();
        new loadDataAsync();

    }

    @SuppressLint("StaticFieldLeak")
    private class loadDataAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return null;
        }
//            return context.getContentResolver().query(
//                    CONTENT_URI,
//                    null,
//                    null,
//                    null,
//                    null
//            );


        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            list = cursor;
            adapter.replaceAll(list);
        }
    }

    private void setupList() {
        adapter = new FavAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        new loadDataAsync().execute();
    }
}



