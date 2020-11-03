package com.e.usersdata.data.remote.response;

import com.e.usersdata.data.model.PhotosModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PhotosInterface {
    @GET("photos")
    Call<List<PhotosModel>> getPhotosList();

    @GET("albums/{id}/photos")
    Call<List<PhotosModel>> getAlbums(@Path("id") int albumId);
}
