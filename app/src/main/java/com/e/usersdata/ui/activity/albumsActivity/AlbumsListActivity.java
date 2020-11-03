package com.e.usersdata.ui.activity.albumsActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.e.usersdata.R;
import com.e.usersdata.data.remote.APIControl;
import com.e.usersdata.data.remote.CheckConnection;
import com.e.usersdata.ui.adapter.AlbumsListAdapter;
import com.e.usersdata.data.remote.response.AlbumsInterface;
import com.e.usersdata.data.model.AlbumsModel;


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

public class AlbumsListActivity extends AppCompatActivity {

    @BindView(R.id.rv_albumsList)
    RecyclerView rvAlbumsList;
    @BindView(R.id.swipe_albums_list)
    SwipeRefreshLayout mSwipeAlbumsList;

    private AlbumsListAdapter albumsListAdapter;
    private List<AlbumsModel> albumsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_albums_list);
        ButterKnife.bind(this);
        initialization();
        getAlbumsList();
        String getActivityName = getIntent().getStringExtra("AlbumsActivity");
        Objects.requireNonNull(getSupportActionBar()).setTitle(getActivityName);
    }

    private void initialization() {
        rvAlbumsList.setHasFixedSize(true);
        rvAlbumsList.setLayoutManager(new LinearLayoutManager(this));
        mSwipeAlbumsList.setOnRefreshListener(this::getAlbumsList);

    }

    private void getAlbumsList() {
        mSwipeAlbumsList.setRefreshing(true);

        Call<List<AlbumsModel>> call = APIControl.getRetrofit().create(AlbumsInterface.class).getAlbumsList();
        call.enqueue(new Callback<List<AlbumsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<AlbumsModel>> call, @NonNull Response<List<AlbumsModel>> response) {
                mSwipeAlbumsList.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    albumsList = new ArrayList<>(response.body());
                    albumsListAdapter = new AlbumsListAdapter(AlbumsListActivity.this, albumsList);
                    rvAlbumsList.setAdapter(albumsListAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<AlbumsModel>> call, @NonNull Throwable t) {
                if (!CheckConnection.isConnection(AlbumsListActivity.this)) {
                    mSwipeAlbumsList.setRefreshing(false);
                    message("Internet Isn't Connection!");
                } else {
                    mSwipeAlbumsList.setRefreshing(false);
                    message("Error Message: "+t.getLocalizedMessage());
                }
            }
        });
    }

    void message(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
