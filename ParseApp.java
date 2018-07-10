package com.example.arorasomya64.parstagram;

import android.app.Application;

import com.example.arorasomya64.parstagram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("tallgiraffes")
                .clientKey("wilson-butler")
                .server("http://arorasomya-insta.herokuapp.com/parse/")
                .build();
        Parse.initialize(configuration);
    }
}
