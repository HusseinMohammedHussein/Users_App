/*
 *   @Copyright Hussein Mohamed
 *   Uploaded at 11/5/2020
 *   Email: memailbusiness@gmail.com
 */
package com.e.usersdata.ui.activity.todosActivity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.e.usersdata.R;
import com.e.usersdata.data.model.TODOsModel;
import com.e.usersdata.data.remote.APIControl;
import com.e.usersdata.data.remote.CheckConnection;
import com.e.usersdata.data.remote.response.TODOsInterface;
import com.e.usersdata.ui.adapter.TODOsListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TODOsByUserIdActivity extends AppCompatActivity {

    @BindView(R.id.rv_todos_byUserId)
    RecyclerView mRvTodosByUserid;
    @BindView(R.id.swipe_todos_byUserId)
    SwipeRefreshLayout mSwipeTodosByUserId;
    private TODOsListAdapter todosAdapter;
    private List<TODOsModel> todosList;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_todos_userid);
        ButterKnife.bind(this);
        buildRecycleVew();
        userId = getIntent().getStringExtra("user/id/todos");
        getTODOsByUserId(userId);
        Objects.requireNonNull(getSupportActionBar()).setTitle("TODOs for User: " + userId);
    }

    private void buildRecycleVew() {
        mRvTodosByUserid.setHasFixedSize(true);
        mRvTodosByUserid.setLayoutManager(new LinearLayoutManager(this));
//        mSwipeTodosByUserId.setOnRefreshListener(this::getTODOsByUserId);
        mSwipeTodosByUserId.setOnRefreshListener(() -> getTODOsByUserId(userId));
    }

    private void getTODOsByUserId(String id) {
        mSwipeTodosByUserId.setRefreshing(true);
        Call<List<TODOsModel>> call = APIControl.getRetrofit().create(TODOsInterface.class).getTODOsByUserId("users/" + id + "/todos");
        call.enqueue(new Callback<List<TODOsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TODOsModel>> call, @NonNull Response<List<TODOsModel>> response) {
                mSwipeTodosByUserId.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    todosList = new ArrayList<>(response.body());
                    todosAdapter = new TODOsListAdapter(TODOsByUserIdActivity.this, todosList);
                    mRvTodosByUserid.setAdapter(todosAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<TODOsModel>> call, @NonNull Throwable t) {
                if (!CheckConnection.isConnection(TODOsByUserIdActivity.this)) {
                    mSwipeTodosByUserId.setRefreshing(false);
                    message("Internet Isn't Connection!");
                } else {
                    mSwipeTodosByUserId.setRefreshing(false);
                    message("Error Message: " + t.getLocalizedMessage());
                }
            }
        });
    }

    void message(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
