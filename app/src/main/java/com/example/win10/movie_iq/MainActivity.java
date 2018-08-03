package com.example.win10.movie_iq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        Intent intent = new Intent(this, GameScreenActivity.class);
        final EditText nameEditText = findViewById(R.id.playerNameEditText);
        String playerName = nameEditText.getText().toString();
        intent.putExtra("playerName", playerName);

        startActivity(intent);

    }


}
