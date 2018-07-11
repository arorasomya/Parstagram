package com.example.arorasomya64.parstagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.arorasomya64.parstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private String imagePath;
    private EditText descriptionInput;
    private Button createBtn;
    private Button addPic;
    private ImageView imgTakenPic;
    private static final int CAM_REQUEST = 1313;
    public String photoFileName;
    public final String APP_TAG = "MyCustomApp";
    File photoFile;
    private final int SOME_WIDTH = 330;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        descriptionInput = findViewById(R.id.descriptionInput);
        createBtn = findViewById(R.id.createBtn);
        addPic = (Button) findViewById(R.id.addPic);
        imgTakenPic = (ImageView) findViewById(R.id.imgTakenPic);

        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create the file where the photo should go

                try {
                    photoFile = createPhotoFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri photoURI = FileProvider.getUriForFile(HomeActivity.this, "com.example.arorasomya64.parstagram", photoFile);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, CAM_REQUEST);
                }
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String description = descriptionInput.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();
                // take a photo from the camera and grab the data
                final ParseFile parseFile = new ParseFile(photoFile);
                createPost(description, parseFile, user);
            }
        });
        loadTopPosts();
    }

    private void createPost(String description, ParseFile parseFile, ParseUser user) {
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setUser(user);
        newPost.setImage(parseFile);
        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("HomeActivity", "Create Post Success");
                } else {
                    e.printStackTrace();
                    Log.d("HomeActivity", e.getMessage());
                }
            }
        });
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
    }

    private void loadTopPosts() {
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();
        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("HomeActivity", "Post[" + i + "] = "
                                + objects.get(i).getKeyDescription() + "\nusername = " + objects.get(i).getUser().getUsername());
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAM_REQUEST && resultCode == RESULT_OK) {
                Bitmap rawTakenImage = BitmapFactory.decodeFile(photoFileName);
                imgTakenPic.setImageBitmap(rawTakenImage);
        }
    }


    // creates the file path and returns the file
    public File createPhotoFile() throws IOException {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory");
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        photoFileName = image.getAbsolutePath();
        return image;
    }
}