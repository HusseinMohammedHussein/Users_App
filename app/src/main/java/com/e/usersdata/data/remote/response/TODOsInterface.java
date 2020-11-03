package com.e.usersdata.data.remote.response;

import com.e.usersdata.data.model.TODOsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface TODOsInterface {
    @GET("todos")
    Call<List<TODOsModel>> getTODOsList();

    @GET
    Call<List<TODOsModel>> getTODOsByUserId(@Url String url);
}
