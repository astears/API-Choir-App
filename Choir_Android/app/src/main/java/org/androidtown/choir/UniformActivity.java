package org.androidtown.choir;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;

public class UniformActivity extends AppCompatActivity {

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
                        break;
                    case R.id.action_uniforms:
                        Intent intent_uni = new Intent(UniformActivity.this, UniformActivity.class);
                        startActivity(intent_uni);
                        break;
                    case R.id.action_messages:
                        Intent intent_announcements = new Intent(UniformActivity.this, AnnouncementsActivity.class);
                        startActivity(intent_announcements);
                        break;
                }
                return true;
            }
        });

        int num_days = 0;
        ArrayList<Date> dates = null;
        CalendarAdapter mAdapter;
        RecyclerView mCalendarList;

        dates = Dates.getDates();
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
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        mCalendarList.addItemDecoration(itemDecoration);

        mCalendarList.setHasFixedSize(true);

        mAdapter = new CalendarAdapter(num_days, dates);

        // Set the SongAdapter you created on mNumbersList
        mCalendarList.setAdapter(mAdapter);

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

}
