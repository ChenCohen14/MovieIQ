package com.example.win10.movie_iq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity {
    private GridLayout questionsActivityGrid;
    private ArrayList<Question> questions;
    private User theUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Intent intent = getIntent();
        theUser = (User) intent.getSerializableExtra("user");

        Toast.makeText(this, ""+theUser, Toast.LENGTH_LONG).show();
        Toast.makeText(this, ""+theUser, Toast.LENGTH_LONG).show();
        Toast.makeText(this, ""+theUser, Toast.LENGTH_LONG).show();

        questions = (ArrayList<Question>) intent.getSerializableExtra("questions");
        questionsActivityGrid = findViewById(R.id.questionsActivityGrid);

        questionsActivityGrid.setColumnCount(1);
        questionsActivityGrid.setRowCount(questions.size());
        //Toast.makeText(this,questions.get(0)+"",Toast.LENGTH_SHORT).show();
        final Intent questionIntent = new Intent(this, GameScreenActivity.class);

        questionIntent.putExtra("user", theUser);

        for (int i = 0; i < questions.size(); i++) {
            final Button bt = new Button(this);

            bt.setText("Question " + i);

            questionIntent.putExtra("question" + i, questions.get(i));
            questionsActivityGrid.addView(bt);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String chosenBt = bt.getText().toString().toLowerCase().replace(" ", "");
                    questionIntent.putExtra("chosenBt", chosenBt);
                    startActivity(questionIntent);
                }
            });
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, ""+theUser, Toast.LENGTH_LONG).show();
        Toast.makeText(this, ""+theUser, Toast.LENGTH_LONG).show();
        Toast.makeText(this, ""+theUser, Toast.LENGTH_LONG).show();
    }
}
