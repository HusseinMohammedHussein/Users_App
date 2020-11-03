package com.e.usersdata.data.remote.response;

import com.e.usersdata.data.model.CommentsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface CommentsInterface {
    //TODO: getComments by @URL RETROFIT
    @GET
    Call<List<CommentsModel>> getComments(@Url String url);

    @GET("comments")
    Call<List<CommentsModel>> getComments(@Query("postId") int postId);
}
