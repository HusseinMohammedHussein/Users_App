package com.e.usersdata.data.remote.response;

import com.e.usersdata.data.model.AlbumsModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface AlbumsInterface {
    @GET("albums")
    Call<List<AlbumsModel>> getAlbumsList();

    @GET("albums")
    Call<List<AlbumsModel>> getAlbumsByUserId(@QueryMap Map<String, String> parameters);

}
