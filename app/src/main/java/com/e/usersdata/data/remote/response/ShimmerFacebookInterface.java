package com.e.usersdata.data.remote.response;

import com.e.usersdata.data.model.PostsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ShimmerFacebookInterface {
    @GET("posts")
    Call<List<PostsModel>> getPosts();
}
