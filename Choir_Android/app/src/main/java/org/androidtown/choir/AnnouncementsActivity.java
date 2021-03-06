package org.androidtown.choir;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

// for swipe to delete
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.List;

public class AnnouncementsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    // Test comment for testBranch
    private FirebaseListAdapter<Message> adapter;
    private SwipeMenuListView listOfMessages;
    SessionManager session;
    String role;

    Toolbar tbl1;
    TextView tv1;
    RelativeLayout rl1;
    View navHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        //session = new SessionManager(getApplicationContext());

        displayChatMessages();

        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);

        // for swipe gesture
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {


                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set the delete icon to ic_delete
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listOfMessages.setMenuCreator(creator);

        listOfMessages.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            // position stores the index of message in the list
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {


                /*Toast.makeText(getApplicationContext(), "Deleted message at position " + position,
                        Toast.LENGTH_LONG).show();*/

                // grabs the position of message and remove it from firebase
                adapter.getRef(position).removeValue();
                return false;
            }
        });



            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText input = (EditText) findViewById(R.id.input);

                    // Read the input field and push a new instance
                    // of Message to the Firebase database
                    FirebaseDatabase.getInstance()
                            .getReference("Messages")
                            .push()
                            .setValue(new Message(input.getText().toString(), "Director")
                            );

                    // Clear the input
                    input.setText("");
                }
            });


//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.action_songs:
//                        Intent intent_songs = new Intent(AnnouncementsActivity.this, MemberActivity.class);
//
//                        startActivity(intent_songs);
//                        break;
//                    case R.id.action_uniforms:
//                        Intent intent_uni = new Intent(AnnouncementsActivity.this, UniformActivity.class);
//
//                        startActivity(intent_uni);
//                        break;
//                    case R.id.action_messages:
//                        break;
//                }
//                return true;
//            }
//        });

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

    private void displayChatMessages() {

        listOfMessages = (SwipeMenuListView)findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<Message>(this, Message.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference("Messages")) {
            @Override
            protected void populateView(View v, Message model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("MM-dd",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);

    }

    public void updateValue(int pos) {
        String key = adapter.getRef(pos).getKey();
        try {
            FirebaseDatabase.getInstance()
                    .getReference("Uniforms")
                    .push()
                    .setValue(new Message("None specified", "None specified")
                    );
            //FirebaseDatabase.getInstance().getReference().child("Messages").child("messageText").setValue("Updated val");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                //updateValue(0);
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
            /*Intent i = new Intent(this, AnnouncementsActivity.class);
            startActivity(i);
            return true;*/
        }
        else if (id == R.id.nav_hymns_dm) {
            Intent i = new Intent(this, MemberActivity.class);
            startActivity(i);
        }
//        else if (id == R.id.nav_uniforms_dm) {
//            Intent i = new Intent(this, UniformActivity.class);
//            startActivity(i);
//            return true;
//        }
        else if (id == R.id.nav_logout_dm) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("FLAG", 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_one_mpdm);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
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

    // to set width of delete item
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
