package org.androidtown.choir;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import java.io.IOException;

import static java.security.AccessController.getContext;


public class MemberActivity extends AppCompatActivity
        implements SongAdapter.ListItemClickListener {

    /*
     * References to RecyclerView and Adapter to reset the list to its
     * "pretty" state when the reset menu item is clicked.
     */
    private String songs[];
    private String fullSongName[];

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        session = new SessionManager(getApplicationContext());

        /*Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);*/


       BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_songs:
                        break;
                    case R.id.action_uniforms:
                        Toast.makeText(MemberActivity.this, "Uniforms", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_messages:
                        Intent intent = new Intent(MemberActivity.this, AnnouncementsActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        int num_songs = 0;
        String[] assetFiles = null;
        SongAdapter mAdapter;
        RecyclerView mSongsList;

        try {
            assetFiles = getAssets().list("songs");
            fullSongName = getAssets().list("songs");
            num_songs = assetFiles.length;
        }
        catch (IOException e) {

        }
        songs = assetFiles;
        Log.i("Num songs ", String.valueOf(num_songs));
        /*
         * Using findViewById, we get a reference to our RecyclerView from xml. This allows us to
         * do things like set the adapter of the RecyclerView and toggle the visibility.
         */
        mSongsList = (RecyclerView) findViewById(R.id.rv_numbers);

        /*
         * A LinearLayoutManager is responsible for measuring and positioning item views within a
         * RecyclerView into a linear list. This means that it can produce either a horizontal or
         * vertical list depending on which parameter you pass in to the LinearLayoutManager
         * constructor. By default, if you don't specify an orientation, you get a vertical list.
         * In our case, we want a vertical list, so we don't need to pass in an orientation flag to
         * the LinearLayoutManager constructor.
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // COMPLETED (6) Use setLayoutManager on mNumbersList with the LinearLayoutManager we created above
        mSongsList.setLayoutManager(layoutManager);

        // Use setHasFixedSize(true) to designate that the contents of the RecyclerView won't change an item's size
        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mSongsList.setHasFixedSize(true);

        // Store a new SongAdapter in mAdapter and pass it num_songs and the assetFiles
        /*
         * The SongAdapter is responsible for displaying each item in the list.
         */
        stripExtension();
        mAdapter = new SongAdapter(num_songs, songs, this);

        // Set the SongAdapter you created on mNumbersList
        mSongsList.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                session.logoutUser();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        Intent intent = new SongActivity().songIntent(MemberActivity.this, fullSongName[clickedItemIndex]);
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
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("FLAG", 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}


