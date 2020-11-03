package com.e.usersdata.data.remote.response;


import com.e.usersdata.data.model.UsersModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UsersInterface {
    @GET("users")
    Call<List<UsersModel>> getUsers();
}
