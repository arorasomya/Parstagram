package com.example.arorasomya64.parstagram;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.arorasomya64.parstagram.model.Post;

import java.util.List;

public class InstaAdapter extends RecyclerView.Adapter<InstaAdapter.ViewHolder> {
    private static List<Post> mPosts;
    static Context context;

    public InstaAdapter(List<Post> posts) {
        mPosts = posts;
    }

    @NonNull
    @Override
    public InstaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.item_feed, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        // get the data according to position
        Post post = mPosts.get(i);
        // populate the views according to this data
        holder.tvHandle.setText(post.getHandle());
        holder.tvCaption.setText(post.getKeyDescription());

        Glide.with(context).load(post.getImage().getUrl())
                .into(holder.ivPic);
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public Post getItem(int position) {
        if (getItemCount() == 0) return null;
        return mPosts.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHandle;
        TextView tvCaption;
        ImageView ivPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHandle = (TextView) itemView.findViewById(R.id.tvHandle);
            tvCaption = (TextView) itemView.findViewById(R.id.tvCaption);
            ivPic = (ImageView) itemView.findViewById(R.id.ivPic);
        }
    }

    // clear all elements
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }
}