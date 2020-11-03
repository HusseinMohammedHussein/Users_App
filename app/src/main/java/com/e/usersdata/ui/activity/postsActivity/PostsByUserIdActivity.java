/*
 *   @Copyright Hussein Mohamed
 *   Uploaded at 11/5/2020
 *   Email: memailbusiness@gmail.com
 */
package com.e.usersdata.ui.activity.postsActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.e.usersdata.R;
import com.e.usersdata.data.remote.APIControl;
import com.e.usersdata.data.remote.CheckConnection;
import com.e.usersdata.data.remote.response.PostsInterface;
import com.e.usersdata.ui.adapter.PostsAdapter;
import com.e.usersdata.data.model.PostsModel;


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

public class PostsByUserIdActivity extends AppCompatActivity {


    @BindView(R.id.rv_posts_byUsersId)
    RecyclerView mRvPostsByUsersId;
    @BindView(R.id.swipe_posts_byUserId)
    SwipeRefreshLayout mSwipePostsByUserId;
    private PostsAdapter userIdPostsAdapter;
    private List<PostsModel> postsByUserIdModelList;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_posts_userid);
        ButterKnife.bind(this);
        initializeViews();
        userID = getIntent().getStringExtra("user/id/posts");
        Objects.requireNonNull(getSupportActionBar()).setTitle("Posts for user: " + userID);
        getPostsByUserId(Integer.parseInt(userID));
    }

    private void initializeViews() {
        mRvPostsByUsersId.setHasFixedSize(true);
        mRvPostsByUsersId.setLayoutManager(new LinearLayoutManager(this));
//        mSwipePostsByUserId.setOnRefreshListener(this::getPostsByUserId);
        mSwipePostsByUserId.setOnRefreshListener(() -> getPostsByUserId(Integer.parseInt(userID)));
    }

    private void getPostsByUserId(int id) {
        mSwipePostsByUserId.setRefreshing(true);

        Call<List<PostsModel>> call = APIControl.getRetrofit().create(PostsInterface.class).getPostsByUserId(id);
        call.enqueue(new Callback<List<PostsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<PostsModel>> call, @NonNull Response<List<PostsModel>> response) {
                mSwipePostsByUserId.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    postsByUserIdModelList = new ArrayList<>(response.body());
                    userIdPostsAdapter = new PostsAdapter(PostsByUserIdActivity.this, postsByUserIdModelList);
                    mRvPostsByUsersId.setAdapter(userIdPostsAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PostsModel>> call, @NonNull Throwable t) {
                if (!CheckConnection.isConnection(PostsByUserIdActivity.this)) {
                    mSwipePostsByUserId.setRefreshing(false);
                    message("Internet Isn't Connection!");
                } else {
                    mSwipePostsByUserId.setRefreshing(false);
                    message("Error Message: " + t.getLocalizedMessage());
                }
            }
        });
    }

    void message(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }
}
