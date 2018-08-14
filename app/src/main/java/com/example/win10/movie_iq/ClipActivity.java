package com.example.win10.movie_iq;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class ClipActivity extends AppCompatActivity {

    private VideoView clipView;
    //private ProgressBar clipProgressbar;
    private Uri clipUri;
    private Question q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip);

        
        clipView = (VideoView) findViewById(R.id.clipView);
        q = (Question) getIntent().getSerializableExtra("question");
        clipUri = Uri.parse(q.getUri());
        clipView.setVideoURI(clipUri);
        clipView.requestFocus();
        clipView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        clipView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clipView.stopPlayback();
    }

}
