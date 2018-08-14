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
    private User theUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button startBt = findViewById(R.id.startBt);
        Button howToPlayBt = findViewById(R.id.howToPlayBt);
        theUser = (User) getIntent().getSerializableExtra("user");

        startBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartGameActivity.this, TiersActivity.class);
                intent.putExtra("user", theUser);
                startActivity(intent);
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


}
