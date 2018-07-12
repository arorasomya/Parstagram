package com.example.arorasomya64.parstagram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_USER = "user";
    private static final String KEY_HANDLE = "handle";

    public ParseFile getMedia() {
        return getParseFile("media");
    }

    public void setMedia(ParseFile parseFile) {
        put("media", parseFile);
    }

    public String getKeyDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public void setHandle(String handle) { put(KEY_HANDLE, handle); }

    public String getHandle() {
        return getUser().getString(KEY_HANDLE);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public static class Query extends ParseQuery<Post>{

        public Query() {
            super(Post.class);
        }
        public Query getTop() {
            setLimit(20);
            return this; // builder pattern
        }
        public Query withUser() {
            include("user");
            return this;
        }
    }
}