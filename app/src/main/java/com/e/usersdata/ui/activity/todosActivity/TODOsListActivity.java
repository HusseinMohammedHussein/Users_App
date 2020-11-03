package com.e.usersdata.ui.activity.todosActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.e.usersdata.R;
import com.e.usersdata.data.remote.APIControl;
import com.e.usersdata.data.remote.CheckConnection;
import com.e.usersdata.ui.adapter.TODOsListAdapter;
import com.e.usersdata.data.remote.response.TODOsInterface;
import com.e.usersdata.data.model.TODOsModel;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TODOsListActivity extends AppCompatActivity {

    @BindView(R.id.rv_todos_list)
    RecyclerView mRvTodosList;
    @BindView(R.id.swipe_todos_list)
    SwipeRefreshLayout mSwipeTodosList;
    private TODOsListAdapter todOsListAdapter;
    private List<TODOsModel> todOsModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_todos_list);
        ButterKnife.bind(this);
        initializeVeiws();
        getTodosList();
        String getActivityName = getIntent().getStringExtra("TODOsActivity");
        Objects.requireNonNull(getSupportActionBar()).setTitle(getActivityName);
    }

    private void initializeVeiws() {
        mRvTodosList.setLayoutManager(new LinearLayoutManager(this));
        mRvTodosList.setHasFixedSize(true);
        mSwipeTodosList.setOnRefreshListener(this::getTodosList);
    }

    private void getTodosList() {
        mSwipeTodosList.setRefreshing(true);

        Call<List<TODOsModel>> call = APIControl.getRetrofit().create(TODOsInterface.class).getTODOsList();
        call.enqueue(new Callback<List<TODOsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TODOsModel>> call, @NonNull Response<List<TODOsModel>> response) {
                mSwipeTodosList.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    todOsModels = new ArrayList<>(response.body());
                    todOsListAdapter = new TODOsListAdapter(TODOsListActivity.this, todOsModels);
                    mRvTodosList.setAdapter(todOsListAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<TODOsModel>> call, @NonNull Throwable t) {
                if (!CheckConnection.isConnection(TODOsListActivity.this)) {
                    mSwipeTodosList.setRefreshing(false);
                    message("Internet Isn't Connection!");
                } else {
                    mSwipeTodosList.setRefreshing(false);
                    message("Error Message: " + t.getLocalizedMessage());
                }
            }
        });
    }
    void message(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
