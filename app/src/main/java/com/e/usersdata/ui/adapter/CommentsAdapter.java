package com.e.usersdata.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.e.usersdata.R;
import com.e.usersdata.data.model.CommentsModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {
    private List<CommentsModel> commentsModelList;
    private Context mContext;

    public CommentsAdapter(Context mContext, List<CommentsModel> commentsModelList) {
        this.commentsModelList = commentsModelList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_comments_list, parent, false);
        return new CommentsViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        CommentsModel commentsModel = commentsModelList.get(position);
        holder.mTvPostIdComments.setText("posts ID: " + commentsModel.getmPostId());
        holder.mTvIDComments.setText("ID: " + commentsModel.getmId());
//        holder.mTvIDComments.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.text_color_black));
        holder.mTvNAMEComments.setText("Name: " + commentsModel.getmName());
        holder.mTvEMAILComments.setText("Email: " + commentsModel.getmEmail());
        holder.mTvBODYComments.setText("Body: " + commentsModel.getmBody());
        /* TODO: i'll to Search about (TextView how to select some words for color?) */
//        holder.tvNAMEComments.setText("Name: " + Color.parseColor("#008ab3") + commentsModel.getmName());


    }

    @Override
    public int getItemCount() {
        return commentsModelList.size();
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_comments_postId)
        TextView mTvPostIdComments;
        @BindView(R.id.tv_comments_id)
        TextView mTvIDComments;
        @BindView(R.id.tv_comments_name)
        TextView mTvNAMEComments;
        @BindView(R.id.tv_comments_email)
        TextView mTvEMAILComments;
        @BindView(R.id.tv_comments_body)
        TextView mTvBODYComments;

        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
