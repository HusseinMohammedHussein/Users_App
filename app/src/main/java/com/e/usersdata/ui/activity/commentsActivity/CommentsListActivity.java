/*
 *   @Copyright Hussein Mohamed
 *   Uploaded at 11/5/2020
 *   Email: memailbusiness@gmail.com
 *   GitHub: https://github.com/HusseinMohammedHussein
 */
package com.e.usersdata.ui.activity.commentsActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.e.usersdata.R;
import com.e.usersdata.data.remote.APIControl;
import com.e.usersdata.data.remote.CheckConnection;
import com.e.usersdata.ui.adapter.CommentsAdapter;
import com.e.usersdata.data.remote.response.CommentsInterface;
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

public class CommentsListActivity extends AppCompatActivity {

    @BindView(R.id.rv_Comments)
    RecyclerView mRvComments;
    @BindView(R.id.swipe_comments_list)
    SwipeRefreshLayout mSwipCommentsList;
    private CommentsAdapter mCommentsAdapter;
    private ArrayList<CommentsModel> mCMList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_comments_list);
        ButterKnife.bind(this);
        initialization();
        getComments();
        String getActivityName = getIntent().getStringExtra("CommentsActivity");
        Objects.requireNonNull(getSupportActionBar()).setTitle(getActivityName);

    }

    private void initialization() {
        mRvComments.setHasFixedSize(true);
        mRvComments.setLayoutManager(new LinearLayoutManager(this));
        mSwipCommentsList.setOnRefreshListener(this::getComments);
    }

    private void getComments() {
        mSwipCommentsList.setRefreshing(true);
        Call<List<CommentsModel>> listCall = APIControl.getRetrofit().create(CommentsInterface.class).getComments(APIControl.BASE_URL + "comments");
        listCall.enqueue(new Callback<List<CommentsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<CommentsModel>> call, @NonNull Response<List<CommentsModel>> response) {
                mSwipCommentsList.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    mCMList = new ArrayList<>(response.body());
                    mCommentsAdapter = new CommentsAdapter(CommentsListActivity.this, mCMList);
                    mRvComments.setAdapter(mCommentsAdapter);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<CommentsModel>> call, @NonNull Throwable t) {
                if (!CheckConnection.isConnection(CommentsListActivity.this)) {
                    mSwipCommentsList.setRefreshing(false);
                    message("Internet Isn't Connection!");
                } else {
                    mSwipCommentsList.setRefreshing(false);
                    message("Error Message: " + t.getLocalizedMessage());
                }
            }
        });
    }
    void message(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
