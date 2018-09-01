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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class QuotesWhatToDoTwo extends AppCompatActivity implements adapteritemhorizontal.Onitemclicklistner {

    RecyclerView mrecyclerView;
    adapteritemhorizontal madapter;
    FirebaseDatabase fb=FirebaseDatabase.getInstance();
    DatabaseReference db=fb.getReference();
    DatabaseReference dburl=db.child("urls");
    ArrayList<String> marraylist;
    ImageView downloadimage,shareimage,likeimage;


    ImageView qoutesshowhimalone;
    String imagelink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_what_to_do_two);


        qoutesshowhimalone=findViewById(R.id.qoutesshowhimalone);
        mrecyclerView=findViewById(R.id.recyclerviewshown);
        downloadimage=findViewById(R.id.downlaodimage);
        shareimage=findViewById(R.id.shareimage);
        likeimage=findViewById(R.id.likeimage);




        marraylist=new ArrayList<>();
        mrecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,GridLayoutManager.HORIZONTAL));
        madapter=new adapteritemhorizontal(marraylist,getApplicationContext());
        madapter.setonitemclicklistner(this);
        mrecyclerView.setAdapter(madapter);


        Intent i=getIntent();
        imagelink=i.getStringExtra("linkofimage");
        Log.i("imagelinkisthat" ,"is "+imagelink);

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

        Glide.with(getApplicationContext())

                .load(imagelink).placeholder(R.drawable.ic_launcher_background)

                .into(qoutesshowhimalone)
        ;



        dburl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren())

                {
                    Getsampledata getsampledata=ds.getValue(Getsampledata.class);
                    String url=getsampledata.getImageurl();
                    marraylist.add(url);

                }
                madapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







    }



    @Override
    public void onitemclick(int position) {
        Toast.makeText(getApplicationContext(), "Tapped on "+position, Toast.LENGTH_SHORT).show();

        Intent i=new Intent(getApplicationContext(),QuotesWhatToDoTwo.class);
        i.putExtra("linkofimage",marraylist.get(position));
        startActivity(i);
        finish();
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
