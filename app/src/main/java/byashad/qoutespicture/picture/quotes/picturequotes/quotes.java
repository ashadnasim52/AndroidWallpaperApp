package byashad.qoutespicture.picture.quotes.picturequotes;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
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
import android.view.Window;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;

public class quotes extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,adapteritem.Onitemclicklistner {



    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref=database.getReference();
    DatabaseReference urlofimage=ref.child("urls");
    RecyclerView recyclerView;
    adapteritem adapterrecylerview;
    ArrayList<String> imagelist;
    ArrayList<String> likeslist;
    AVLoadingIndicatorView progressbara;






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imagelist=new ArrayList<>();
        likeslist=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclervieww);
        progressbara=findViewById(R.id.progressbara);;
        recyclerView.setVisibility(View.GONE);
        progressbara.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager sGrid = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
//         adapterofitemyo=new adapterofitem(titlearray,getApplicationContext());
//        adapterofitemyo.setonitemclicklistner(this);
        sGrid.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(sGrid);

        adapterrecylerview=new adapteritem(imagelist,getApplicationContext());
        adapterrecylerview.setonitemclicklistner(this);
        recyclerView.setAdapter(adapterrecylerview);

        recyclerView.setVisibility(View.VISIBLE);
        progressbara.setVisibility(View.GONE);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                ((StaggeredGridLayoutManager)recyclerView.getLayoutManager()).invalidateSpanAssignments();
            }
        });


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




//
//        urlofimage.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//
//
//
//                for (DataSnapshot ds:dataSnapshot.getChildren())
//                {
//
//
//
//                    Getsampledata value=ds.getValue(Getsampledata.class);
//
//                    String url=value.getImageurl();
//                    String type=value.getType();
//                    imagelist.add(new Getsampledata(url,type));
//                    Log.i("imageusr","   isurl   "+imagelist);
//                    adapterrecylerview.notifyDataSetChanged();
//
//
//
//                }
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                for (DataSnapshot ds:dataSnapshot.getChildren())
//                {
//
//
//                }
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//


        urlofimage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    Getsampledata value=ds.getValue(Getsampledata.class);
                    Log.i("imageusr","   value   "+value);

                    String url=value.getImageurl();
                    Log.i("imageusr","   url   "+url);

                    String type=value.getType();
                    String likes=value.getLikes();
                    Log.i("imageusr","   type   "+type);

                    imagelist.add(url);
                    likeslist.add(likes);
                    Log.i("imageusr","   isurl   "+imagelist);



                }
                adapterrecylerview.notifyDataSetChanged();


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
        getMenuInflater().inflate(R.menu.quotes, menu);
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

        Intent i=new Intent(getApplicationContext(),QuotesWhatToDotwo.class);
        i.putExtra("linkofimage",imagelist.get(position));
        i.putExtra("likeslist",likeslist.get(position));
        startActivity(i);
    }
}
