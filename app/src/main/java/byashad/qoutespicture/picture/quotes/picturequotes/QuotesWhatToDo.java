package byashad.qoutespicture.picture.quotes.picturequotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class QuotesWhatToDo extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,adapteritem.Onitemclicklistner {
    RecyclerView mrecyclerView;
    adapteritem madapter;
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
        setContentView(R.layout.activity_quotes_what_to_do);
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

        qoutesshowhimalone=findViewById(R.id.qoutesshowhimalone);
        mrecyclerView=findViewById(R.id.recyclerviewshown);
        downloadimage=findViewById(R.id.downlaodimage);
        shareimage=findViewById(R.id.shareimage);
        likeimage=findViewById(R.id.likeimage);
        downloadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        marraylist=new ArrayList<>();
        mrecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL));
        madapter=new adapteritem(marraylist,getApplicationContext());
        madapter.setonitemclicklistner(this);
        mrecyclerView.setAdapter(madapter);


        Intent i=getIntent();
        imagelink=i.getStringExtra("linkofimage");
        Log.i("imagelinkisthat" ,"is "+imagelink);


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
        getMenuInflater().inflate(R.menu.quotes_what_to_do, menu);
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

    @Override
    public void onitemclick(int position) {
        Toast.makeText(getApplicationContext(), "Tapped on "+position, Toast.LENGTH_SHORT).show();

        Intent i=new Intent(getApplicationContext(),QuotesWhatToDo.class);
        i.putExtra("linkofimage",marraylist.get(position));
        startActivity(i);
        finish();
    }
}
