package com.e.usersdata.ui.activity.usersActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.e.usersdata.R;
import com.e.usersdata.data.remote.MyApplication;
import com.e.usersdata.databinding.ActivityUsersBinding;
import com.e.usersdata.ui.adapter.UsersAdapter;
import com.e.usersdata.utils.SnackBarMessage;

import java.util.Objects;

public class UsersListActivity extends AppCompatActivity {


    private UsersAdapter mUsersAdapter;
    private UsersViewModel usersViewModel;
    private ActivityUsersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_users);
        initializeViews();
        String getActivityName = getIntent().getStringExtra("UsersActivity");
        Objects.requireNonNull(getSupportActionBar()).setTitle(getActivityName);
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        getUsers();

        mUsersAdapter = new UsersAdapter();
    }

    private void initializeViews() {
        binding.rvUserList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvUserList.setHasFixedSize(true);
        binding.swipeUsersList.setOnRefreshListener(this::getUsers);
    }

    private void getUsers() {
        binding.swipeUsersList.setRefreshing(true);
        usersViewModel.usersMutableLiveData().observe(this, usersModels -> {
            if (MyApplication.getInstance().isNetworkAvailable()) {
                if (!usersModels.isEmpty()) {
                    binding.swipeUsersList.setRefreshing(false);
                    mUsersAdapter.setUsersList(usersModels);
                    binding.rvUserList.setAdapter(mUsersAdapter);
                } else {
                    binding.swipeUsersList.setRefreshing(false);
                    SnackBarMessage.putMessage(binding.rvUserList, "EMPTY Response!");
                }
            } else {
                binding.swipeUsersList.setRefreshing(false);
                SnackBarMessage.putMessage(binding.rvUserList, "Network not Available!");
            }
        });

/*        Call<List<UsersModel>> listCall = APIControl.getRetrofit().create(UsersInterface.class).getUsers();
        listCall.enqueue(new Callback<List<UsersModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<UsersModel>> call, @NonNull Response<List<UsersModel>> response) {
                mSwipeUsersList.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    mUsersList = new ArrayList<>(response.body());
                    mUsersAdapter = new UsersAdapter(UsersListActivity.this, mUsersList);
                    mRvUserList.setAdapter(mUsersAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UsersModel>> call, @NonNull Throwable t) {
                if (!CheckConnection.isConnection(UsersListActivity.this)) {
                    mSwipeUsersList.setRefreshing(false);
                    message("Internet Isn't Connection!");
                } else {
                    mSwipeUsersList.setRefreshing(false);
                    message("Error Message: " + t.getLocalizedMessage());
                }
            }
        });*/
    }

/*    void message(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }*/

}
