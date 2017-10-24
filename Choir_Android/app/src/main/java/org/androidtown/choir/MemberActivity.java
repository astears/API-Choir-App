package org.androidtown.choir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.PDFView.Configurator;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;

import java.io.IOException;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static org.androidtown.choir.SongActivity.songIntent;
//import static org.androidtown.choir.R.id.pdfView;


public class MemberActivity extends AppCompatActivity
        implements SongAdapter.ListItemClickListener{

    /*
     * References to RecyclerView and Adapter to reset the list to its
     * "pretty" state when the reset menu item is clicked.
     */
    private SongAdapter mAdapter;
    private RecyclerView mNumbersList;
    private String songs[];
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        int num_songs = 0;
        String[] assetFiles = null;

        try {
            assetFiles = getAssets().list("songs");
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
        mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);

        /*
         * A LinearLayoutManager is responsible for measuring and positioning item views within a
         * RecyclerView into a linear list. This means that it can produce either a horizontal or
         * vertical list depending on which parameter you pass in to the LinearLayoutManager
         * constructor. By default, if you don't specify an orientation, you get a vertical list.
         * In our case, we want a vertical list, so we don't need to pass in an orientation flag to
         * the LinearLayoutManager constructor.
         *
         * There are other LayoutManagers available to display your data in uniform grids,
         * staggered grids, and more! See the developer documentation for more details.
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // COMPLETED (6) Use setLayoutManager on mNumbersList with the LinearLayoutManager we created above
        mNumbersList.setLayoutManager(layoutManager);

        // COMPLETED (7) Use setHasFixedSize(true) to designate that the contents of the RecyclerView won't change an item's size
        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mNumbersList.setHasFixedSize(true);

        // COMPLETED (8) Store a new GreenAdapter in mAdapter and pass it NUM_LIST_ITEMS
        /*
         * The GreenAdapter is responsible for displaying each item in the list.
         */
        mAdapter = new SongAdapter(num_songs, assetFiles, this);

        // COMPLETED (9) Set the GreenAdapter you created on mNumbersList
        mNumbersList.setAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        /*if (mToast != null) {
            mToast.cancel();
        }

        String toastMessage = songs[clickedItemIndex] + " clicked.";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();*/

        Intent intent = new SongActivity().songIntent(MemberActivity.this, songs[clickedItemIndex]);
        startActivity(intent);
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);



        final PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.fromAsset("El consuelo de mi Alma.pdf").onRender(new OnRenderListener() {
            @Override
            public void onInitiallyRendered(int pages, float pageWidth, float pageHeight) {
                pdfView.fitToWidth(); // optionally pass page number
            }
        }).load();
    }*/
}


