package com.e.usersdata.data.remote.response;

import com.e.usersdata.data.model.PostsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostsInterface {

    @GET("posts")
    Call<List<PostsModel>> getPosts();

    @GET("users/{id}/posts")
    Call<List<PostsModel>> getPostsByUserId(@Path("id") int userId);
}
