package com.example.win10.movie_iq;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class StartGameActivity extends AppCompatActivity {
    private User theUser;
    private ProgressBar pb;
    private Soundtrack soundtrack;
    private static final String TAG = "StartGameActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        pb = findViewById(R.id.progressBarStartGame);
        pb.setVisibility(View.GONE);
        Button startBt = findViewById(R.id.startBt);
        Button howToPlayBt = findViewById(R.id.howToPlayBt);
        theUser = (User) getIntent().getSerializableExtra("user");


        soundtrack = (Soundtrack) getIntent().getSerializableExtra("soundtrack");
        if(soundtrack != null) {
            soundtrack.getMediaPlayer().stop();
            soundtrack.getMediaPlayer().reset();
        }
        else
            soundtrack = new Soundtrack();
        //setUpSoundtrack();

        startBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(StartGameActivity.this, TiersActivity.class);
//                intent.putExtra("user", theUser);
//                startActivity(intent);

                pb.setVisibility(View.VISIBLE);
                Intent intent = new Intent(StartGameActivity.this, TiersActivity.class);
                intent.putExtra("user", theUser);
                intent.putExtra("soundtrack", soundtrack);
                startActivity(intent);
              //  new MyAsyncTask().execute();
            }
        });


        howToPlayBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartGameActivity.this, HelpScreenActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        soundtrack.getMediaPlayer().stop();
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("TAG", TAG);
        startActivity(intent);

        finish();
        // onDestroy();
    }

    class MyAsyncTask extends AsyncTask<Void, Integer, Integer> {


        @Override
        protected Integer doInBackground(Void... voids) {
            // pb.setVisibility(View.VISIBLE);
            Intent intent = new Intent(StartGameActivity.this, TiersActivity.class);
            intent.putExtra("user", theUser);
            intent.putExtra("soundtrack", soundtrack);
            startActivity(intent);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb.setVisibility(View.VISIBLE);
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

        }
    }

    private void setUpSoundtrack() {
        String uri = "https://firebasestorage.googleapis.com/v0/b/movieiq2.appspot.com/o/amellie%20soundtack.mp3?alt=media&token=eb717767-49e3-48e5-8e44-5d1d6fbf9187";

        try {
            soundtrack.getMediaPlayer().setDataSource(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            soundtrack.getMediaPlayer().prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        soundtrack.getMediaPlayer().start();
        soundtrack.getMediaPlayer().setLooping(true);
    }



}
