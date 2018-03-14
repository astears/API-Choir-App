package org.androidtown.choir;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UniformActivity extends AppCompatActivity
        implements CalendarAdapter.ListItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    private FirebaseListAdapter<Uniform> adapter;
    private FloatingActionButton button;
    CalendarAdapter mAdapter;
    SessionManager session;

    Toolbar tbl1;
    TextView tv1;
    RelativeLayout rl1;
    View navHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uniform);

        //session = new SessionManager(getApplicationContext());


       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Add button clicked!", Toast.LENGTH_LONG).show();
            }
        });*/

        int num_days = 0;
        ArrayList<Date> dates = null;
        //CalendarAdapter mAdapter;
        RecyclerView mCalendarList;
        ArrayList<Uniform> uniforms;

        Date now = new Date();
        dates = Dates.getDates();
        //dates.add(now);
        num_days = dates.size();
        Log.i("num days ", String.valueOf(num_days));

        /*
         * Using findViewById, we get a reference to our RecyclerView from xml. This allows us to
         * do things like set the adapter of the RecyclerView and toggle the visibility.
         */
        mCalendarList = (RecyclerView) findViewById(R.id.rv_calendar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // COMPLETED (6) Use setLayoutManager on mNumbersList with the LinearLayoutManager we created above
        mCalendarList.setLayoutManager(layoutManager);
        //DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        //mCalendarList.addItemDecoration(itemDecoration);

        mCalendarList.setHasFixedSize(true);

        //displayUniforms();

        mAdapter = new CalendarAdapter(num_days, dates, this);

        // Set the SongAdapter you created on mNumbersList
        mCalendarList.setAdapter(mAdapter);

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
    public void onListItemClick(final int clickedItemIndex) {

        /*Toast.makeText(getApplicationContext(), "item " + String.valueOf(clickedItemIndex) + " was clicked",
                Toast.LENGTH_LONG).show();*/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);


        builder.setTitle("Uniforms");
        final EditText mensUniformEditText = new EditText(this);
        layout.addView(mensUniformEditText);
        mensUniformEditText.setHint("Enter Men's Uniform");

        final EditText womensUniformEditText = new EditText(this);
        layout.addView(womensUniformEditText);
        womensUniformEditText.setHint("Enter Women's Uniform");

        builder.setView(layout);


        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Toast.makeText(getApplicationContext(), "Men: " + mensUniformEditText.getText() + "\nWomen: " + womensUniformEditText.getText(),
                // Toast.LENGTH_LONG).show();
                try {
                    //String key = adapter.getRef(clickedItemIndex).getKey();
                    FirebaseDatabase.getInstance().getReference("Uniforms")
                            .push().setValue(new Uniform(mensUniformEditText.getText().toString(),
                            womensUniformEditText.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("Cancel",  new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }


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
            Intent i = new Intent(this, MemberActivity.class);
            startActivity(i);
        }
//        else if (id == R.id.nav_uniforms_dm) {
//            /*Intent i = new Intent(this, UniformActivity.class);
//            startActivity(i);*/
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
    public void onBackPressed() {

       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_one_mpdm);
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

}