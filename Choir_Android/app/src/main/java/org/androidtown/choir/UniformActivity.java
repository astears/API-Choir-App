package org.androidtown.choir;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UniformActivity extends AppCompatActivity
    implements CalendarAdapter.ListItemClickListener {

    private FirebaseListAdapter<Uniform> adapter;
    private FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uniform);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_songs:
                        Intent intent_songs = new Intent(UniformActivity.this, MemberActivity.class);
                        startActivity(intent_songs);
                        break;
                    case R.id.action_uniforms:
                        break;
                    case R.id.action_messages:
                        Intent intent_announcements = new Intent(UniformActivity.this, AnnouncementsActivity.class);
                        startActivity(intent_announcements);
                        break;
                }
                return true;
            }
        });





       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Add button clicked!", Toast.LENGTH_LONG).show();
            }


        });*/

        int num_days = 0;
        ArrayList<Date> dates = null;
        CalendarAdapter mAdapter;
        RecyclerView mCalendarList;
        ArrayList<Uniform> uniforms;

        Date now = new Date();
        dates = Dates.getDates();
        dates.add(now);
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


    }


    /*private void displayUniforms() {

        //ListView rv = (ListView) findViewById(R.id.rv_calendar);
        adapter = new FirebaseListAdapter<Uniform>(this, Uniform.class,
                R.layout.calendar_item, FirebaseDatabase.getInstance().getReference("Uniforms")) {
            @Override
            protected void populateView(View v, Uniform model, int position) {
                // Get references to the views of message.xml
                TextView mensUniform = (TextView)v.findViewById(R.id.tv_item_men_uniform);
                TextView womensUniform = (TextView)v.findViewById(R.id.tv_item_women_uniform);

                // Set their text
                mensUniform.setText(model.getMensUniform());
                womensUniform.setText(model.getWomensUniform());

            }
        };

        //rv.setAdapter(adapter);
    }*/

    @Override
    public void onListItemClick(int clickedItemIndex) {

        /*Toast.makeText(getApplicationContext(), "item " + String.valueOf(clickedItemIndex) + " was clicked",
                Toast.LENGTH_LONG).show();*/
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
                //session.logoutUser();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void toast_add_button(View view) {
        Toast.makeText(getApplicationContext(), "Add button clicked!", Toast.LENGTH_LONG).show();
    }
}
