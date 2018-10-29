package com.grack.rapalll.catalogmovie.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient  {


    public static final String LOAD_GAMBAR = "http://image.tmdb.org/t/p/w185/";
    public static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    public static final String BASE_URL_SEARCH_MOVIE = "https://api.themoviedb.org/3/search/";

    public static Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Retrofit getRetrofitSearch() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL_SEARCH_MOVIE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
