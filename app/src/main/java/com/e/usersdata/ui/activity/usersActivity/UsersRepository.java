package com.e.usersdata.ui.activity.usersActivity;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.e.usersdata.data.model.UsersModel;
import com.e.usersdata.data.remote.MyApplication;
import com.e.usersdata.data.remote.response.UsersInterface;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class UsersRepository {


    public MutableLiveData<List<UsersModel>> getUsers (){
        MutableLiveData<List<UsersModel>> holdUsersData = new MutableLiveData<>();

        Call<List<UsersModel>> responseUsers = MyApplication.getRetrofitClint().create(UsersInterface.class).getUsers();
        responseUsers.enqueue(new Callback<List<UsersModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<UsersModel>> call, @NotNull Response<List<UsersModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    holdUsersData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<UsersModel>> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
        return holdUsersData;
    }
}
