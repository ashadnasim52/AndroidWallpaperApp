package byashad.qoutespicture.picture.quotes.picturequotes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class QuotesWhatToDoTwo extends AppCompatActivity  {



    FirebaseDatabase fb=FirebaseDatabase.getInstance();
    DatabaseReference db=fb.getReference();
    DatabaseReference urlofimage=db.child("urls");




    int likes;
    ImageView downloadimage,shareimage;
    TextView likeimage;


    ImageView qoutesshowhimalone;
    String imagelink,likeslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_what_to_do_two);


        qoutesshowhimalone=findViewById(R.id.qoutesshowhimalone);
        downloadimage=findViewById(R.id.downlaodimage);
        shareimage=findViewById(R.id.shareimage);
        likeimage=findViewById(R.id.likeimage);





        Intent i=getIntent();
        imagelink=i.getStringExtra("linkofimage");
        likeslist=i.getStringExtra("likeslist");
        Log.i("imagelinkisthat" ,"is "+imagelink);
        likeimage.setText(likeslist);

        likes = Integer.parseInt(likeslist);

        downloadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Glide.with(getApplicationContext())
                        .load(imagelink)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>(1920,1920) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation)  {
                                saveImage(resource);
                            }
                        });
            }
        });
        likeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlofimage.orderByChild("imageurl").equalTo(imagelink).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Log.i("adededede","is "+dataSnapshot);



                        if (dataSnapshot.hasChild("likes")) {

                            DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                            String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                            String path = "/" + dataSnapshot.getKey() + "/" + "likes";
                            String needtoupdatelike=urlofimage.child(path).getKey();

                            Object count = dataSnapshot.child(path).getValue();
                            int updatedlike=likes+1;
                            String updatedlikessinstring=Integer.toString(updatedlike);
                            ////            urlofimage.child(path).setValue(result);
                            Log.i("needtoupdatelike","is"+count);
                            urlofimage.child(path).setValue(updatedlikessinstring);



                            likeimage.setText(updatedlikessinstring);


                            Log.i("typaaaaaae","iss "+path);

                        } else {
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
                        Log.i("adededede","is "+dataSnapshot);

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Log.i("adededede","is "+dataSnapshot);

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        Log.i("adededede","is "+dataSnapshot);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        Glide.with(getApplicationContext())

                .load(imagelink).placeholder(R.drawable.ic_launcher_background)

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







    }



    private String saveImage(Bitmap image) {
        String savedImagePath = null;

        String imageFileName = UUID.randomUUID()+ ".jpg";
        File storageDir = new File(            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
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




}
