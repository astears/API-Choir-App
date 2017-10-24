package org.androidtown.choir;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;

public class SongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        String songName = getIntent().getStringExtra("SONG_NAME");

        final PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.fromAsset("songs/" + songName).onRender(new OnRenderListener() {
            @Override
            public void onInitiallyRendered(int pages, float pageWidth, float pageHeight) {
                pdfView.fitToWidth(); // optionally pass page number
            }
        }).load();
    }

    public static Intent songIntent(Context packageContext, String song) {
        Intent intent = new Intent(packageContext, SongActivity.class);
        intent.putExtra("SONG_NAME", song);
        return intent;
    }
}
