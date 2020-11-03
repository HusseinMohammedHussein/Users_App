/*
 *   @Copyright Hussein Mohamed
 *   Uploaded at 11/5/2020
 *   Email: memailbusiness@gmail.com
 *   GitHub: https://github.com/HusseinMohammedHussein
 */
package com.e.usersdata.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.transition.TransitionManager;

import com.e.usersdata.R;
import com.e.usersdata.data.remote.CheckConnection;
import com.e.usersdata.ui.activity.albumsActivity.AlbumsByUserIdActivity;
import com.e.usersdata.ui.activity.albumsActivity.AlbumsListActivity;
import com.e.usersdata.ui.activity.commentsActivity.CommentsByPostIdActivity;
import com.e.usersdata.ui.activity.commentsActivity.CommentsListActivity;
import com.e.usersdata.ui.activity.photosActivity.PhotosByAlbumIdActivity;
import com.e.usersdata.ui.activity.photosActivity.PhotosListActivity;
import com.e.usersdata.ui.activity.postsActivity.PostsByUserIdActivity;
import com.e.usersdata.ui.activity.postsActivity.PostsListActivity;
import com.e.usersdata.ui.activity.todosActivity.TODOsByUserIdActivity;
import com.e.usersdata.ui.activity.todosActivity.TODOsListActivity;
import com.e.usersdata.ui.activity.usersActivity.UsersListActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    /* Start Get List */
    @BindView(R.id.tvShowPosts)
    TextView mTvShowPosts;
    @BindView(R.id.tvShowUsers)
    TextView mTvShowUsers;
    @BindView(R.id.tvShowComments)
    TextView mTvShowComments;
    @BindView(R.id.tvShowAlbums)
    TextView mTvShowAlbums;
    @BindView(R.id.tvShowPhotos)
    TextView mTvShowPhotos;
    @BindView(R.id.tvShowTodos)
    TextView mTvShowTodos;
    /* End Get List */

    /* Start Get Data By Id */
    /* Get Posts By UserId */
    @BindView(R.id.tv_showposts_byuserid)
    TextView mTvPostsByUserId;
    @BindView(R.id.et_getposts_byuserid)
    EditText mEtPostsByUserId;
    @BindView(R.id.btn_getposts_byuserid)
    Button mBtnGetPostsByUserId;
    /* Get Photos By Album Id */
    @BindView(R.id.btn_getphotos_byalbumid)
    Button mBtnGetPhotosByAlbumId;
    @BindView(R.id.et_getphotos_setalbumid)
    EditText mEtPhotosByAlbumId;
    @BindView(R.id.tv_showphotos_byalbumid)
    TextView mTvShowPhotosByAlbumId;
    /* Get Comments By PostId */
    @BindView(R.id.tv_getcomments_bypostid)
    TextView mTvShowCommentsByPostId;
    @BindView(R.id.et_getcomments_bypostid)
    EditText mEtGetCommentsByPostId;
    @BindView(R.id.btn_getComments_bypostid)
    Button mBtnGetCommentsByPostId;
    /* Get Albums By UserId */
    @BindView(R.id.tv_showalbums_byuserid)
    TextView mTvShowAlbumsByUserId;
    @BindView(R.id.et_getalbums_byuserid)
    EditText mEtGetAlbumsByUserId;
    @BindView(R.id.btn_getalbums_byuserid)
    Button mBtnGetAlbumsByUserId;
    /* Get TODOs by UserId */
    @BindView(R.id.tv_showtodos_byuserid)
    TextView mTvShowTODOsByUserId;
    @BindView(R.id.et_gettodos_byuserid)
    EditText mEtSetTODOsByUserId;
    @BindView(R.id.btn_gettodos_byuserid)
    Button mBtnGetTODOsByUserId;
    /* End Get Data By Id */

    @BindView(R.id.nsv_by_id)
    NestedScrollView nestedScrollView;
    /*ViewGroup For use in Delayed Transition*/
    @BindView(R.id.linear_scrollView)
    ViewGroup vgNested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        /* Get List */
        onClickGetList();
        /* Get By Id */
        onClickGetById();
        /* Delayed Transition */
        beginDelayedTransition();

        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
    }

    private void onClickGetList() {
        onClickShowPosts();
        onClickShowComments();
        onClickShowUsers();
        onClickShowAlbums();
        onClickShowPhotos();
        onClickShowTODOs();
    }

    private void onClickGetById() {
        onClickGetPostsByUserId();
        onClickGetAlbumsByUserId();
        onClickGetTODOsByUserId();
        onClickGetPhotosByAlbumsId();
        onClickGetCommentsByPostID();
    }

    /* Fetch all */
    private void onClickShowPosts() {
        mTvShowPosts.setOnClickListener(v -> {
            if (CheckConnection.isConnection(this)) {
                Intent intent = new Intent(MainActivity.this, PostsListActivity.class);
                intent.putExtra("PostsActivity", mTvShowPosts.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void onClickShowComments() {
        mTvShowComments.setOnClickListener(v -> {
            if (CheckConnection.isConnection(this)) {
                Intent intent = new Intent(MainActivity.this, CommentsListActivity.class);
                intent.putExtra("CommentsActivity", mTvShowComments.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickShowUsers() {
        mTvShowUsers.setOnClickListener(v -> {
            if (CheckConnection.isConnection(this)) {
                Intent intent = new Intent(MainActivity.this, UsersListActivity.class);
                intent.putExtra("UsersActivity", mTvShowUsers.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickShowAlbums() {
        mTvShowAlbums.setOnClickListener(v -> {
            if (CheckConnection.isConnection(this)) {
                Intent intent = new Intent(MainActivity.this, AlbumsListActivity.class);
                intent.putExtra("AlbumsActivity", mTvShowAlbums.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickShowPhotos() {
        mTvShowPhotos.setOnClickListener(v -> {
            if (CheckConnection.isConnection(this)) {
                Intent intent = new Intent(MainActivity.this, PhotosListActivity.class);
                intent.putExtra("PhotosActivity", mTvShowPhotos.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickShowTODOs() {
        mTvShowTodos.setOnClickListener(v -> {
            if (CheckConnection.isConnection(this)) {
                Intent intent = new Intent(MainActivity.this, TODOsListActivity.class);
                intent.putExtra("TODOsActivity", mTvShowTodos.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /* Fetch by ID */
    private void onClickGetPostsByUserId() {
        mTvPostsByUserId.setOnClickListener(v -> {
            beginDelayedTransition();
            mEtPostsByUserId.setVisibility(mEtPostsByUserId.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            mBtnGetPostsByUserId.setVisibility(mBtnGetPostsByUserId.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            mBtnGetPostsByUserId.setOnClickListener(v1 -> {
                if (validatePostsByUserId()) {
                    if (CheckConnection.isConnection(this)) {
                        Intent intent = new Intent(MainActivity.this, PostsByUserIdActivity.class);
                        intent.putExtra("user/id/posts", mEtPostsByUserId.getText().toString().trim());
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    private boolean validatePostsByUserId() {
        String input = mEtPostsByUserId.getText().toString().trim();
        if (input.isEmpty()) {
            mEtPostsByUserId.setError("Field is Required!");
            return false;
        } else if (Integer.parseInt(input) > 10 || Integer.parseInt(input) < 1) {
            mEtPostsByUserId.setError("Value Most More than Zero OR Less than 10");
            return false;
        } else {
            mEtPostsByUserId.setError(null);
            return true;
        }
    }

    private void onClickGetPhotosByAlbumsId() {
        mTvShowPhotosByAlbumId.setOnClickListener(v -> {
            beginDelayedTransition();
            mEtPhotosByAlbumId.setVisibility(mEtPhotosByAlbumId.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            mBtnGetPhotosByAlbumId.setVisibility(mBtnGetPhotosByAlbumId.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            mBtnGetPhotosByAlbumId.setOnClickListener(v1 -> {
                if (validatePhotosByAlbumsId()) {
                    if (CheckConnection.isConnection(this)) {
                        Intent i = new Intent(MainActivity.this, PhotosByAlbumIdActivity.class);
                        i.putExtra("album/id/photos", mEtPhotosByAlbumId.getText().toString());
                        startActivity(i);
                    } else {
                        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    private boolean validatePhotosByAlbumsId() {
        String input = mEtPhotosByAlbumId.getText().toString().trim();
        if (input.isEmpty()) {
            mEtPhotosByAlbumId.setError("Field is Required!");
            return false;
        } else if (Integer.parseInt(input) > 100 || Integer.parseInt(input) < 1) {
            mEtPhotosByAlbumId.setError("Value Most More than Zero OR Less than 100");
            return false;
        } else {
            mEtPhotosByAlbumId.setError(null);
            return true;
        }
    }

    private void onClickGetCommentsByPostID() {
        mTvShowCommentsByPostId.setOnClickListener(v -> {
            beginDelayedTransition();
            mEtGetCommentsByPostId.setVisibility(mEtGetCommentsByPostId.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            mBtnGetCommentsByPostId.setVisibility(mBtnGetCommentsByPostId.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            mBtnGetCommentsByPostId.setOnClickListener(v1 -> {
                if (validateCommentsByPostID()) {
                    if (CheckConnection.isConnection(this)) {
                        Intent intent = new Intent(MainActivity.this, CommentsByPostIdActivity.class);
                        intent.putExtra("post/id/comments", mEtGetCommentsByPostId.getText().toString());
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

    }

    private boolean validateCommentsByPostID() {
        String input = mEtGetCommentsByPostId.getText().toString().trim();
        if (input.isEmpty()) {
            mEtGetCommentsByPostId.setError("Field is Required!");
            return false;
        } else if (Integer.parseInt(input) > 100 || Integer.parseInt(input) < 1) {
            mEtGetCommentsByPostId.setError("Value Most More than Zero OR Less than 100");
            return false;
        } else {
            mEtGetCommentsByPostId.setError(null);
            return true;
        }
    }

    private void onClickGetAlbumsByUserId() {
        mTvShowAlbumsByUserId.setOnClickListener(v -> {
            beginDelayedTransition();
            mEtGetAlbumsByUserId.setVisibility(mEtGetAlbumsByUserId.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            mBtnGetAlbumsByUserId.setVisibility(mBtnGetAlbumsByUserId.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            mBtnGetAlbumsByUserId.setOnClickListener(v1 -> {
                if (validateAlbumsByUserId()) {
                    if (CheckConnection.isConnection(this)) {
                        Intent intent = new Intent(MainActivity.this, AlbumsByUserIdActivity.class);
                        intent.putExtra("user/id/albums", mEtGetAlbumsByUserId.getText().toString());
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    private boolean validateAlbumsByUserId() {
        String input = mEtGetAlbumsByUserId.getText().toString().trim();

        if (input.isEmpty()) {
            mEtGetAlbumsByUserId.setError("Field is Required!");
            return false;
        } else if (Integer.parseInt(input) > 10 || Integer.parseInt(input) < 1) {
            mEtGetAlbumsByUserId.setError("Value Most More than Zero OR Less than 10");
            return false;
        } else {
            mEtGetAlbumsByUserId.setError(null);
            return true;
        }

    }

    private void onClickGetTODOsByUserId() {
        mTvShowTODOsByUserId.setOnClickListener(v -> {
            beginDelayedTransition();
            mEtSetTODOsByUserId.setVisibility(mEtSetTODOsByUserId.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            mBtnGetTODOsByUserId.setVisibility(mBtnGetTODOsByUserId.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            mBtnGetTODOsByUserId.setOnClickListener(v1 -> {
                if (validateTODOsByUserId()) {
                    if (CheckConnection.isConnection(this)) {
                        Intent intent = new Intent(MainActivity.this, TODOsByUserIdActivity.class);
                        intent.putExtra("user/id/todos", mEtSetTODOsByUserId.getText().toString());
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    private boolean validateTODOsByUserId() {
        String input = mEtSetTODOsByUserId.getText().toString().trim();
        if (input.isEmpty()) {
            mEtSetTODOsByUserId.setError("Field is Required!");
            return false;
        } else if (Integer.parseInt(input) > 10 || Integer.parseInt(input) < 1) {
            mEtSetTODOsByUserId.setError("Value Most More than Zero OR Less than 10");
            return false;
        } else {
            mEtSetTODOsByUserId.setError(null);
            return true;
        }
    }

    /* Delayed Transition */
    private void beginDelayedTransition() {
        TransitionManager.beginDelayedTransition(vgNested);
    }
}
