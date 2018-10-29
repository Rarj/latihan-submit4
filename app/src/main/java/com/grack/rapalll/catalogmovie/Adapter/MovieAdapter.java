package com.grack.rapalll.catalogmovie.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.grack.rapalll.catalogmovie.Activity.DetailMovie;
import com.grack.rapalll.catalogmovie.Data.Result;
import com.grack.rapalll.catalogmovie.R;
import com.squareup.picasso.Picasso;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    List<Result> results;

    public MovieAdapter(List<Result> results) {
        this.results = results;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_movie, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieHolder holder, @SuppressLint("RecyclerView") final int position) {

        String judul = results.get(position).getTitle();
        String deskripsi = results.get(position).getOverview();

        Picasso.with(holder.itemView.getContext())
                .load("http://image.tmdb.org/t/p/w185/" + results.get(position).getPosterPath())
                .into(holder.Poster);

        holder.Judul.setText(judul);
        holder.Deskripsi.setText(deskripsi);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Result data = results.get(position);
                Intent i = new Intent(holder.itemView.getContext(), DetailMovie.class);
                i.putExtra("movie", new GsonBuilder().create().toJson(data));
                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    public void setData(List<Result> results) {
        this.results = results;
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {
        ImageView Poster;
        TextView Judul;
        JustifiedTextView Deskripsi;

        MovieHolder(View itemView) {
            super(itemView);
            Poster = itemView.findViewById(R.id.poster);
            Judul = itemView.findViewById(R.id.text_judul);
            Deskripsi = itemView.findViewById(R.id.text_deskripsi);
        }
    }
}
