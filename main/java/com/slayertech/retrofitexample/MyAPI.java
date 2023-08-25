package com.slayertech.retrofitexample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface MyAPI {
    @GET
    Call<List<Modelclass>> getmodels(@Url String url);
}
