package com.example.arorasomya64.parstagram;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.arorasomya64.parstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    InstaAdapter postAdapter;
    ArrayList<Post> posts;
    RecyclerView rvPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        posts = new ArrayList<Post>();
        postAdapter = new InstaAdapter(posts);
        rvPosts = (RecyclerView) findViewById(R.id.rvPosts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvPosts.setLayoutManager(linearLayoutManager);
        rvPosts.setAdapter(postAdapter);

        loadTopPosts();
    }

    private void loadTopPosts() {
        // clear out old items before appending new items
        postAdapter.clear();
            final Post.Query postsQuery = new Post.Query();
            postsQuery.getTop().withUser();
            postsQuery.findInBackground(new FindCallback<Post>() {
                @Override
                public void done(List<Post> objects, ParseException e) {
                    for (int i = 0; i < objects.size(); i++) {
                        Post post = objects.get(i);
                        posts.add(post);
                        postAdapter.notifyItemInserted(posts.size() - 1);
                    }
                }
            });
            postAdapter.addAll(posts);
    }

    public void launchCreateHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void launchLogout(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }



}
