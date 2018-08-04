package com.example.win10.movie_iq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TiersActivity extends AppCompatActivity {
    private static final String TAG = "TiersActivity";
    private final static int QUESTION_ARR_SIZE = 10;

    private ArrayList<Question> questions;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiers);
        questions = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("questions");


    }

    public void onClick(View view) {
        Intent intent = new Intent(this, QuestionsActivity.class);
        final Button clickedBt = findViewById(view.getId());
        final String chosenTier = clickedBt.getText().toString().toLowerCase().replaceAll(" ", "");
        ;


            databaseReference.child(chosenTier).child("0").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d(TAG, "loading a question");
                    Question q = new Question();
                    q = dataSnapshot.getValue(Question.class);
                    questions.add(q);
                    Toast.makeText(TiersActivity.this, "ADDED" + questions.size(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Question parsed is : " + q);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        intent.putExtra("questions", questions);

        Toast.makeText(this, "GO TO NEXT ACTIVITY", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }


}
