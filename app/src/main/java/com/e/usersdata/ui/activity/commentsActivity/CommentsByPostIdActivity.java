/*
 *   @Copyright Hussein Mohamed
 *   Uploaded at 11/5/2020
 *   Email: memailbusiness@gmail.com
 */
package com.e.usersdata.ui.activity.commentsActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.e.usersdata.R;
import com.e.usersdata.data.remote.APIControl;
import com.e.usersdata.data.remote.CheckConnection;
import com.e.usersdata.data.remote.response.CommentsInterface;
import com.e.usersdata.ui.adapter.CommentsAdapter;
import com.e.usersdata.data.model.CommentsModel;


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

public class CommentsByPostIdActivity extends AppCompatActivity {

    @BindView(R.id.rv_comments_byPostId)
    RecyclerView mRvCommentsPostId;
    @BindView(R.id.swipe_comments_byPostId)
    SwipeRefreshLayout mSwipeCommentsByPostId;
    private List<CommentsModel> comList;
    private CommentsAdapter commentsAdapter;
    private String postId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_comments_by_postid);
        ButterKnife.bind(this);
        buildRecycleView();
        postId = getIntent().getStringExtra("post/id/comments");
        getCommentsByPostid(Integer.parseInt(postId));
        Objects.requireNonNull(getSupportActionBar()).setTitle("Comments for Post: " + postId);
    }

    private void buildRecycleView() {
        mRvCommentsPostId.setHasFixedSize(true);
        mRvCommentsPostId.setLayoutManager(new LinearLayoutManager(this));
//        mSwipeCommentsByPostId.setOnRefreshListener(this::getCommentsByPostid);
        mSwipeCommentsByPostId.setOnRefreshListener(() -> getCommentsByPostid(Integer.parseInt(postId)));
    }

    private void getCommentsByPostid(int id) {
        mSwipeCommentsByPostId.setRefreshing(true);
        Call<List<CommentsModel>> call = APIControl.getRetrofit().create(CommentsInterface.class).getComments(id);
        call.enqueue(new Callback<List<CommentsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<CommentsModel>> call, @NonNull Response<List<CommentsModel>> response) {
                mSwipeCommentsByPostId.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    comList = new ArrayList<>(response.body());
                    commentsAdapter = new CommentsAdapter(CommentsByPostIdActivity.this, comList);
                    mRvCommentsPostId.setAdapter(commentsAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CommentsModel>> call, @NonNull Throwable t) {
                if (!CheckConnection.isConnection(CommentsByPostIdActivity.this)) {
                    mSwipeCommentsByPostId.setRefreshing(false);
                    message("Internet Isn't Connection!");
                } else {
                    mSwipeCommentsByPostId.setRefreshing(false);
                    message("Message Error: " + t.getLocalizedMessage());
                }
            }
        });
    }

    void message(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
