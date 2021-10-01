package com.hipradeep.recyclerpagination.retrofit;


import com.hipradeep.recyclerpagination.models.ResponseMoviesList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiServices {

    //@GET("?s=007&page=&apikey=5a4eac80")
    @GET()
    Call<ResponseMoviesList>  getMoviesList(@Url String url);
}
