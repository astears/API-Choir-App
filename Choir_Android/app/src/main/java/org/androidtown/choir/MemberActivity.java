package org.androidtown.choir;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static java.security.AccessController.getContext;


public class MemberActivity extends AppCompatActivity
        implements SongAdapter.ListItemClickListener, NavigationView.OnNavigationItemSelectedListener,
        android.support.v7.widget.SearchView.OnQueryTextListener{

    private String songs[];
    private String fullSongName[];
    SessionManager session;
    SongAdapter mAdapter;
    ArrayList<String> newSongs;

    Toolbar tbl1;
    TextView tv1;
    RelativeLayout rl1;
    View navHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_member);
        setContentView(R.layout.g__main_page_dm);

        session = new SessionManager(getApplicationContext());

        int num_songs = 0;
        String[] assetFiles = null;
        RecyclerView mSongsList;

        try {
            assetFiles = getAssets().list("songs");
            fullSongName = getAssets().list("songs");
            newSongs = new ArrayList<>(Arrays.asList(assetFiles));
            num_songs = assetFiles.length;
        }
        catch (IOException e) {

        }
        songs = assetFiles;
        Log.i("Num songs ", String.valueOf(num_songs));

        mSongsList = (RecyclerView) findViewById(R.id.rv_numbers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mSongsList.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        mSongsList.addItemDecoration(itemDecoration);

        mSongsList.setHasFixedSize(true);

        stripExtension();
        mAdapter = new SongAdapter(num_songs, songs, this);

        mSongsList.setAdapter(mAdapter);

        //Toolbar
        tbl1 = (Toolbar) findViewById(R.id.tbl_one_mpdm);
        setSupportActionBar(tbl1);
        getSupportActionBar().setTitle("");


        //TextView
        tv1 = (TextView) findViewById(R.id.tv_one_mpdm);

        //Nav Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_one_mpdm);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, tbl1, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_one_mpdm);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                session.logoutUser();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_message_dm) {
            Intent i = new Intent(this, AnnouncementsActivity.class);
            startActivity(i);
            return true;

        }
        else if (id == R.id.nav_hymns_dm) {
            /*Intent i = new Intent(this, MemberActivity.class);
            startActivity(i);*/
        }
//        else if (id == R.id.nav_uniforms_dm) {
//            Intent i = new Intent(this, UniformActivity.class);
//            startActivity(i);
//            return true;
//        }
        else if (id == R.id.nav_logout_dm) {
            //session.logoutUser();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_one_mpdm);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        Intent intent = new SongActivity().songIntent(MemberActivity.this, newSongs.get(clickedItemIndex));
        startActivity(intent);
    }

    public void stripExtension() {
        for (int i  = 0; i < songs.length; i++) {
            int endIdx = songs[i].length() - 1;
            if (songs[i].substring(endIdx - 3).equals(".pdf")) {
                songs[i] = songs[i].substring(0, endIdx - 3);

            }
        }
    }

    @Override
    public void onBackPressed() {

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_one_mpdm);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }*/

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("FLAG", 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        newSongs = new ArrayList<String>();
        for (String song : songs) {
            String name = song.toLowerCase();

            if (name.contains(newText)) {
                newSongs.add(song);
            }
        }

        mAdapter.setFilter(newSongs);
        return false;
    }
}


