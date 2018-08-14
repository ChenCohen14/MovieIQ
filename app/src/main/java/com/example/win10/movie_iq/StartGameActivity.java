package com.example.win10.movie_iq;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class StartGameActivity extends AppCompatActivity {
    private static MediaPlayer mp;
    private User theUser;
    private boolean wasOnTiersBefore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button startBt = findViewById(R.id.startBt);

        theUser = (User) getIntent().getSerializableExtra("user");

        wasOnTiersBefore = false;

        startBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartGameActivity.this, TiersActivity.class);
                intent.putExtra("user", theUser);
                startActivity(intent);
            }
        });

        Button howToPlayBt = findViewById(R.id.howToPlayBt);
        howToPlayBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////TODO
            }
        });


        final Switch switchMusic = findViewById(R.id.switchMusic);
        switchMusic.setChecked(true);
        mp = new MediaPlayer();
        String backedFromTiers = getIntent().getExtras().getString("TAG");
        if (backedFromTiers != null) {
            if (backedFromTiers.equals("TiersActivity"))
                wasOnTiersBefore = true;
            playMusic(false);
        } else
            playMusic(true);
        switchMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic(switchMusic.isChecked());
            }
        });
    }

    private void playMusic(boolean checked) {
        if (checked && !wasOnTiersBefore) {
            // play songs
            mp = new MediaPlayer();

            try {
                mp.setDataSource("https://firebasestorage.googleapis.com/v0/b/movieiq2.appspot.com/o/Tarzan%20-Son%20Of%20Man%20(Phil%20Collins).mp3?alt=media&token=8281c86f-177f-42f8-a28d-b261dce48928");
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();

                    }
                });
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (wasOnTiersBefore && !checked || !wasOnTiersBefore && !checked)
            mp.stop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }


}
