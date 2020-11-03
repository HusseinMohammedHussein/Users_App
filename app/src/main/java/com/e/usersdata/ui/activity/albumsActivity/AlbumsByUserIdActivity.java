/*
 *   @Copyright Hussein Mohamed
 *   Uploaded at 11/5/2020
 *   Email: memailbusiness@gmail.com
 */
package com.e.usersdata.ui.activity.albumsActivity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.e.usersdata.R;
import com.e.usersdata.data.model.AlbumsModel;
import com.e.usersdata.data.remote.APIControl;
import com.e.usersdata.data.remote.CheckConnection;
import com.e.usersdata.data.remote.response.AlbumsInterface;
import com.e.usersdata.ui.adapter.AlbumsListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumsByUserIdActivity extends AppCompatActivity {

    @BindView(R.id.rv_albums_byUserId)
    RecyclerView mRvAlbumsUserid;
    @BindView(R.id.swipe_albums_byUserId)
    SwipeRefreshLayout mSwipeAlbumsByUserId;
    private List<AlbumsModel> albumsList;
    private AlbumsListAdapter albumsAdapter;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_albums_by_userid);
        ButterKnife.bind(this);
        initializeViews();
        userID = getIntent().getStringExtra("user/id/albums");
        getAlbumsByUserId(Integer.parseInt(userID));
        Objects.requireNonNull(getSupportActionBar()).setTitle("Albums for user: " + userID);
    }

    private void initializeViews() {
        mRvAlbumsUserid.setLayoutManager(new LinearLayoutManager(this));
        mRvAlbumsUserid.setHasFixedSize(true);
//        mSwipeAlbumsByUserId.setOnRefreshListener(this::getAlbumsByUserId);
        mSwipeAlbumsByUserId.setOnRefreshListener(()-> getAlbumsByUserId(Integer.parseInt(userID)));
    }

    private void getAlbumsByUserId(int id) {
        mSwipeAlbumsByUserId.setRefreshing(true);
        Map<String, String> getParameters = new HashMap<>();
        getParameters.put("userId", String.valueOf(id));
        getParameters.put("_sort", "id");
        getParameters.put("_order", "desc");
        Call<List<AlbumsModel>> call = APIControl.getRetrofit().create(AlbumsInterface.class).getAlbumsByUserId(getParameters);
        call.enqueue(new Callback<List<AlbumsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<AlbumsModel>> call, @NonNull Response<List<AlbumsModel>> response) {
                mSwipeAlbumsByUserId.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    albumsList = new ArrayList<>(response.body());
                    albumsAdapter = new AlbumsListAdapter(AlbumsByUserIdActivity.this, albumsList);
                    mRvAlbumsUserid.setAdapter(albumsAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<AlbumsModel>> call, @NonNull Throwable t) {
                if (!CheckConnection.isConnection(AlbumsByUserIdActivity.this)) {
                    mSwipeAlbumsByUserId.setRefreshing(false);
                    message("Internet Isn't Connection!");
                } else {
                    mSwipeAlbumsByUserId.setRefreshing(false);
                    message("Error Message: " + t.getLocalizedMessage());
                }
            }
        });
    }

    void message(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
