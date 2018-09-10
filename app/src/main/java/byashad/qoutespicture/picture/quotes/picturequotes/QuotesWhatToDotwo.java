package byashad.qoutespicture.picture.quotes.picturequotes;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class QuotesWhatToDotwo extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    MediaPlayer mediaPlayer;
    MediaPlayer mediaPlayer1;
    MediaPlayer mediaPlayer2;


    AVLoadingIndicatorView downlaodprogress, shareprogress, likeprogress;


    FirebaseDatabase fb = FirebaseDatabase.getInstance();
    DatabaseReference db = fb.getReference();
    DatabaseReference urlofimage = db.child("urls");

    Bitmap image;
    String path;


    int likes;
    ImageView downloadimage, shareimage;
    TextView likeimage;


    ImageView qoutesshowhimalone;
    String imagelink, likeslist;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_what_to_dotwo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);







        qoutesshowhimalone = findViewById(R.id.qoutesshowhimalone);
        downloadimage = findViewById(R.id.downlaodimage);
        shareimage = findViewById(R.id.shareimage);
        likeimage = findViewById(R.id.likeimage);
        downlaodprogress = findViewById(R.id.progresssbarondownload);
        shareprogress = findViewById(R.id.progressbaronshare);
        likeprogress = findViewById(R.id.progressbarlike);


        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);
        mediaPlayer1 = MediaPlayer.create(getApplicationContext(), R.raw.soundtwo);
        mediaPlayer2 = MediaPlayer.create(getApplicationContext(), R.raw.soundthree);


        Intent i = getIntent();
        imagelink = i.getStringExtra("linkofimage");
        likeslist = i.getStringExtra("likeslist");
        Log.i("imagelinkisthat", "is " + imagelink);
        likeimage.setText(likeslist);

        likes = Integer.parseInt(likeslist);

        downloadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showprogressondownload();
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                    } else
                        mediaPlayer.start();
                    mediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Glide.with(getApplicationContext())
                        .load(imagelink)
                        .asBitmap().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(new SimpleTarget<Bitmap>(1920, 1920) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                saveImage(resource);
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                                hideprogressondownload();
                                super.onLoadFailed(e, errorDrawable);
                            }
                        });
            }
        });
        likeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (mediaPlayer1.isPlaying()) {
                        mediaPlayer1.stop();
                        mediaPlayer1.release();
                        mediaPlayer1 = MediaPlayer.create(getApplicationContext(), R.raw.soundtwo);
                    }
                    mediaPlayer1.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                urlofimage.orderByChild("imageurl").equalTo(imagelink).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Log.i("adededede", "is " + dataSnapshot);


                        showprogressonlike();

                        if (dataSnapshot.hasChild("likes")) {

                            DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                            String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                            String path = "/" + dataSnapshot.getKey() + "/" + "likes";
                            String needtoupdatelike = urlofimage.child(path).getKey();

                            Object count = dataSnapshot.child(path).getValue();
                            int updatedlike = likes + 1;
                            String updatedlikessinstring = Integer.toString(updatedlike);
                            ////            urlofimage.child(path).setValue(result);
                            Log.i("needtoupdatelike", "is" + count);
                            urlofimage.child(path).setValue(updatedlikessinstring);


                            likeimage.setText(updatedlikessinstring);
                            hideprogressonlike();


                            Log.i("typaaaaaae", "iss " + path);

                        } else {
                            hideprogressonlike();
                        }


////
//                DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
//            String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
//            String path = "/" + dataSnapshot.getKey() + "/" + key;
//            HashMap<String, Object> result = new HashMap<>();
//            DatabaseReference fbfb=FirebaseDatabase.getInstance().getReference("path");
//            Log.i("refffff","path is"+path);
//            Log.i("refffff","databaseref is"+fbfb);
//            Log.i("refffff","key is"+key);
//
//
//            result.put("type", "COMPLETED");
////            urlofimage.child(path).setValue(result);

//            fbfb.setValue(result);
//
//                databaseReference.child(user.getUid()).setValue(/*YOUR OBJECT CLASS GOES HERE*/);

//
//                for (DataSnapshot child: dataSnapshot.getChildren()) {
//                    Log.d("User key", child.getKey());
//                    Log.d("User ref", child.getRef().toString());
//                    Log.d("User val", child.getValue().toString());
//
//                    Log.i("containornot","is "+child.getRef().equalTo(imagelink));
//                    child.getRef().setValue("0000");
////           ref.child("myDb").child("awais@gmailcom").child("leftSpace").setValue("YourDateHere");
//
//
//
//
//                }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Log.i("adededede", "is " + dataSnapshot);
                        showprogressonlike();

                        if (dataSnapshot.hasChild("likes")) {

                            DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                            String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                            String path = "/" + dataSnapshot.getKey() + "/" + "likes";
                            String needtoupdatelike = urlofimage.child(path).getKey();

                            Object count = dataSnapshot.child(path).getValue();
                            int updatedlike = likes + 1;
                            String updatedlikessinstring = Integer.toString(updatedlike);
                            ////            urlofimage.child(path).setValue(result);
                            Log.i("needtoupdatelike", "is" + count);
                            urlofimage.child(path).setValue(updatedlikessinstring);


                            likeimage.setText(updatedlikessinstring);
                            hideprogressonlike();


                            Log.i("typaaaaaae", "iss " + path);

                        } else {
                            hideprogressonlike();
                        }


                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Log.i("adededede", "is " + dataSnapshot);
                        showprogressonlike();

                        if (dataSnapshot.hasChild("likes")) {

                            DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                            String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                            String path = "/" + dataSnapshot.getKey() + "/" + "likes";
                            String needtoupdatelike = urlofimage.child(path).getKey();

                            Object count = dataSnapshot.child(path).getValue();
                            int updatedlike = likes + 1;
                            String updatedlikessinstring = Integer.toString(updatedlike);
                            ////            urlofimage.child(path).setValue(result);
                            Log.i("needtoupdatelike", "is" + count);
                            urlofimage.child(path).setValue(updatedlikessinstring);


                            likeimage.setText(updatedlikessinstring);
                            hideprogressonlike();


                            Log.i("typaaaaaae", "iss " + path);

                        } else {
                            hideprogressonlike();
                        }


                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        Log.i("adededede", "is " + dataSnapshot);
                        showprogressonlike();

                        if (dataSnapshot.hasChild("likes")) {

                            DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                            String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                            String path = "/" + dataSnapshot.getKey() + "/" + "likes";
                            String needtoupdatelike = urlofimage.child(path).getKey();

                            Object count = dataSnapshot.child(path).getValue();
                            int updatedlike = likes + 1;
                            String updatedlikessinstring = Integer.toString(updatedlike);
                            ////            urlofimage.child(path).setValue(result);
                            Log.i("needtoupdatelike", "is" + count);
                            urlofimage.child(path).setValue(updatedlikessinstring);


                            likeimage.setText(updatedlikessinstring);
                            hideprogressonlike();


                            Log.i("typaaaaaae", "iss " + path);

                        } else {
                            hideprogressonlike();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Toast.makeText(QuotesWhatToDotwo.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        Glide.with(getApplicationContext())

                .load(imagelink).asBitmap().placeholder(R.drawable.ic_launcher_background)
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })


                .into(qoutesshowhimalone)
        ;

//        urlofimage.getDatabase().getReference("type").orderByChild("url").equalTo(imagelink).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                urlofimage.getRef().child("PIN").setValue("30");
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


//                .once("value", function(snapshot) {
//            snapshot.forEach(function(user) {
//                user.ref.child("PIN").set(30);
//            });
//        })


//        urlofimage.orderByChild("image").equalTo(imagelink).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
////                DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
////            String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
////            String path = "/" + dataSnapshot.getKey() + "/" + key;
////            HashMap<String, Object> result = new HashMap<>();
////            result.put("type", "COMPLETED");
////            urlofimage.child(path).setValue(result);
////
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        shareimage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showprogressonshare();
                try {
                    if (mediaPlayer2.isPlaying()) {
                        mediaPlayer2.stop();
                        mediaPlayer2.release();
                        mediaPlayer2 = MediaPlayer.create(getApplicationContext(), R.raw.soundthree);
                    }
                    mediaPlayer2.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                Log.i("quoteswahttodo", "is tapped");


                Glide.with(getApplicationContext())
                        .load(imagelink)
                        .asBitmap().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)

                        .into(new SimpleTarget<Bitmap>(250, 250) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                Log.i("quoteswahttodo", "is onresoursereddy");

                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.putExtra(Intent.EXTRA_TEXT, "Hey view/download this image");
                                String path = MediaStore.Images.Media.insertImage(getContentResolver(), resource, "", null);
                                Log.i("quoteswahttodo", "is onresoursereddy" + path);

                                Uri screenshotUri = Uri.parse(path);

                                Log.i("quoteswahttodo", "is onresoursereddy" + screenshotUri);

                                intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                                intent.setType("image/*");
                                hideprogressonshare();
                                startActivity(Intent.createChooser(intent, "Share image via..."));
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                hideprogressonshare();

                                super.onLoadFailed(e, errorDrawable);
                            }

                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                Toast.makeText(getApplicationContext(), "Starting", Toast.LENGTH_SHORT).show();

                                super.onLoadStarted(placeholder);
                            }
                        });


            }
        });




    }



    private String saveImage(Bitmap image) {
        String savedImagePath = null;

        String imageFileName = UUID.randomUUID() + ".jpg";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/YOUR_FOLDER_NAME");
        //Todo chage this to app name
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add the image to the system gallery
            galleryAddPic(savedImagePath);

            hideprogressondownload();
            Toast.makeText(getApplicationContext(), "IMAGE SAVED", Toast.LENGTH_LONG).show();
        }
        return savedImagePath;
    }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }


    public void showprogressondownload() {
        downlaodprogress.show();
        downloadimage.setVisibility(View.GONE);
        shareimage.setVisibility(View.GONE);
        likeimage.setVisibility(View.GONE);
    }

    public void hideprogressondownload() {
        downlaodprogress.hide();
        downlaodprogress.setVisibility(View.GONE);
        downloadimage.setVisibility(View.VISIBLE);
        shareimage.setVisibility(View.VISIBLE);
        likeimage.setVisibility(View.VISIBLE);
    }


    public void showprogressonshare() {
        shareprogress.show();
        downloadimage.setVisibility(View.GONE);
        shareimage.setVisibility(View.GONE);
        likeimage.setVisibility(View.GONE);
    }

    public void hideprogressonshare() {
        shareprogress.hide();
        shareprogress.setVisibility(View.GONE);
        downloadimage.setVisibility(View.VISIBLE);
        shareimage.setVisibility(View.VISIBLE);
        likeimage.setVisibility(View.VISIBLE);
    }


    public void showprogressonlike() {
        likeprogress.show();
        downloadimage.setVisibility(View.GONE);
        shareimage.setVisibility(View.GONE);
        likeimage.setVisibility(View.GONE);
    }

    public void hideprogressonlike() {
        likeprogress.hide();
        downloadimage.setVisibility(View.VISIBLE);
        shareimage.setVisibility(View.VISIBLE);
        likeimage.setVisibility(View.VISIBLE);
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quotes_what_to_dotwo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id==R.id.wallpaperapply)
        {
            Glide.with(getApplicationContext())
                    .load(imagelink)
                    .asBitmap().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)

                    .into(new SimpleTarget<Bitmap>(1000, 1000) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            Log.i("quoteswahttodo", "is onresoursereddy");


                            WallpaperManager wallpaperManager =
                                    null;
                            try {

                                Intent intent = new Intent();
                                intent.setAction(WallpaperManager.ACTION_CROP_AND_SET_WALLPAPER);
                                startActivity(intent);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }



//                            Intent intent = new Intent(Intent.ACTION_SEND);
//                            intent.putExtra(Intent.EXTRA_TEXT, "Hey view/download this image");
//                            String path = MediaStore.Images.Media.insertImage(getContentResolver(), resource, "", null);
//                            Log.i("quoteswahttodo", "is onresoursereddy" + path);
//
//                            Uri screenshotUri = Uri.parse(path);
//
//                            Log.i("quoteswahttodo", "is onresoursereddy" + screenshotUri);
//
//                            intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
//                            intent.setType("image/*");
//                            hideprogressonshare();
//                            startActivity(Intent.createChooser(intent, "Share image via..."));
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            hideprogressonshare();

                            super.onLoadFailed(e, errorDrawable);
                        }

                        @Override
                        public void onLoadStarted(Drawable placeholder) {
                            Toast.makeText(getApplicationContext(), "Starting", Toast.LENGTH_SHORT).show();

                            super.onLoadStarted(placeholder);
                        }
                    });


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
