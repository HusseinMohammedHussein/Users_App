/*
 *   @Copyright Hussein Mohamed
 *   Uploaded at 11/5/2020
 *   Email: memailbusiness@gmail.com
 */
package com.e.usersdata.ui.activity.photosActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.e.usersdata.R;
import com.e.usersdata.data.remote.APIControl;
import com.e.usersdata.data.remote.CheckConnection;
import com.e.usersdata.data.remote.response.PhotosInterface;
import com.e.usersdata.ui.adapter.PhotosListAdapter;
import com.e.usersdata.data.model.PhotosModel;


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

public class PhotosByAlbumIdActivity extends AppCompatActivity {


    @BindView(R.id.rv_photos_byAlbumId)
    RecyclerView mRvPhotosByAlbumId;
    @BindView(R.id.swipe_photos_byAlbumId)
    SwipeRefreshLayout mSwipePhotosByAlbumId;
    private PhotosListAdapter albumsAdapter;
    private List<PhotosModel> photosByAlbumIdModelList;
    private String albumID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_photos_albumid);
        ButterKnife.bind(this);
        initializeViews();
        albumID = getIntent().getStringExtra("album/id/photos");
        getPhotosByAlbumId(Integer.parseInt(albumID));
        Objects.requireNonNull(getSupportActionBar()).setTitle("Photos for Album: " + albumID);
    }

    private void initializeViews() {
        mRvPhotosByAlbumId.setLayoutManager(new LinearLayoutManager(this));
        mRvPhotosByAlbumId.setHasFixedSize(true);
//        mSwipePhotosByAlbumId.setOnRefreshListener(this::getPhotosByAlbumId);
        mSwipePhotosByAlbumId.setOnRefreshListener(() -> getPhotosByAlbumId(Integer.parseInt(albumID)));
    }


    private void getPhotosByAlbumId(int id) {
        mSwipePhotosByAlbumId.setRefreshing(true);
        Call<List<PhotosModel>> call = APIControl.getRetrofit().create(PhotosInterface.class).getAlbums(id);
        call.enqueue(new Callback<List<PhotosModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<PhotosModel>> call, @NonNull Response<List<PhotosModel>> response) {
                mSwipePhotosByAlbumId.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    photosByAlbumIdModelList = new ArrayList<>(response.body());
                    albumsAdapter = new PhotosListAdapter(PhotosByAlbumIdActivity.this, photosByAlbumIdModelList);
                    mRvPhotosByAlbumId.setAdapter(albumsAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PhotosModel>> call, @NonNull Throwable t) {
                if (!CheckConnection.isConnection(PhotosByAlbumIdActivity.this)) {
                    mSwipePhotosByAlbumId.setRefreshing(false);
                    message("Internet Isn't Connection!");
                } else {
                    mSwipePhotosByAlbumId.setRefreshing(false);
                    message("Error Message: " + t.getLocalizedMessage());
                }
            }
        });
    }

    void message(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
