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

        startBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(StartGameActivity.this, TiersActivity.class);
//                intent.putExtra("user", theUser);
//                startActivity(intent);
                pb.setVisibility(View.VISIBLE);
                new MyAsyncTask().execute();
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
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("TAG",TAG);
        startActivity(intent);

        finish();
       // onDestroy();
    }

    class MyAsyncTask extends AsyncTask<Void, Integer, Integer>{



     @Override
     protected Integer doInBackground(Void... voids) {
        // pb.setVisibility(View.VISIBLE);
         Intent intent = new Intent(StartGameActivity.this, TiersActivity.class);
         intent.putExtra("user", theUser);
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
}
