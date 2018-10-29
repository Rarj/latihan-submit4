package com.grack.rapalll.catalogmovie.Adapter;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.grack.rapalll.catalogmovie.API.ApiClient;
import com.grack.rapalll.catalogmovie.Activity.DetailMovie;
import com.grack.rapalll.catalogmovie.Data.ResultsItem;
import com.grack.rapalll.catalogmovie.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    private Cursor mList;

    public FavAdapter(Cursor items){
        replaceAll(items);
    }

    public void replaceAll(Cursor items){
        mList = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate
                        (R.layout.list_movie, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        if (mList == null) return 0;
        return mList.getCount();
    }

    private ResultsItem getItem(int position){
        if(!mList.moveToPosition(position)){
            throw new IllegalStateException("Position Valid");
        }
        return new ResultsItem(mList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.poster)
        ImageView poster;

        @BindView(R.id.text_judul)
        TextView judul;

        @BindView(R.id.text_deskripsi)
        TextView deskripsi;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final ResultsItem item){
            judul.setText(item.getTitle());
            deskripsi.setText(item.getOverview());

            Picasso.with(itemView.getContext())
                    .load(ApiClient.LOAD_GAMBAR + item.getPosterPath())
                    .into(poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(itemView.getContext(), DetailMovie.class);
                    i.putExtra("detailmovie", new Gson().toJson(item));
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }

}
