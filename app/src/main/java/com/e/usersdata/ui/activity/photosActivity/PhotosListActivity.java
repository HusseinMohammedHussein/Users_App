package com.e.usersdata.ui.activity.photosActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.e.usersdata.R;
import com.e.usersdata.data.remote.APIControl;
import com.e.usersdata.data.remote.CheckConnection;
import com.e.usersdata.ui.adapter.PhotosListAdapter;
import com.e.usersdata.data.remote.response.PhotosInterface;
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

public class PhotosListActivity extends AppCompatActivity {

    @BindView(R.id.rv_photos_list)
    RecyclerView mRvPhotosList;
    @BindView(R.id.swipe_photos_list)
    SwipeRefreshLayout mSwipePhotosList;
    private PhotosListAdapter photosListAdapter;
    private List<PhotosModel> photosModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_photos_list);
        ButterKnife.bind(this);
        initializeViews();
        getPhotosList();
        String getActivityName = getIntent().getStringExtra("PhotosActivity");
        Objects.requireNonNull(getSupportActionBar()).setTitle(getActivityName);

    }

    private void initializeViews() {
        mRvPhotosList.setLayoutManager(new LinearLayoutManager(this));
        mRvPhotosList.setHasFixedSize(true);
        mSwipePhotosList.setOnRefreshListener(this::getPhotosList);
    }

    private void getPhotosList() {
        mSwipePhotosList.setRefreshing(true);

        Call<List<PhotosModel>> call = APIControl.getRetrofit().create(PhotosInterface.class).getPhotosList();
        call.enqueue(new Callback<List<PhotosModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<PhotosModel>> call, @NonNull Response<List<PhotosModel>> response) {
                mSwipePhotosList.setRefreshing(false);
                if (response.isSuccessful() && response.body() != null) {
                    photosModels = new ArrayList<>(response.body());
                    photosListAdapter = new PhotosListAdapter(PhotosListActivity.this, photosModels);
                    mRvPhotosList.setAdapter(photosListAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PhotosModel>> call, @NonNull Throwable t) {
                if (!CheckConnection.isConnection(PhotosListActivity.this)) {
                    mSwipePhotosList.setRefreshing(false);
                    message("Internet Isn't Connection!");
                } else {
                    mSwipePhotosList.setRefreshing(false);
                    message("Error Message: " + t.getLocalizedMessage());
                }
            }
        });
    }

    void message(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
