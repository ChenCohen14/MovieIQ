package com.example.win10.movie_iq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TiersActivity extends AppCompatActivity {
    private static final String TAG = "TiersActivity";
    private final static int QUESTION_ARR_SIZE = 10;

    private ArrayList<Question> questions;
    private DatabaseReference databaseReference;
    private ProgressBar prg;
    private int progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiers);
        questions = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("questions");
        prg = findViewById(R.id.progressBarTiers);
        prg.setVisibility(View.INVISIBLE);
        progress = 0;




    }

    public void onClick(View view) {
        final Intent intent = new Intent(this, QuestionsActivity.class);
        final Button clickedBt = findViewById(view.getId());
        final String chosenTier = clickedBt.getText().toString().toLowerCase().replaceAll(" ", "");
        ;
        questions.clear();

        prg.setVisibility(View.VISIBLE);
        prg.setProgress(0);
        for (int i = 1; i <= QUESTION_ARR_SIZE; i++) {
            databaseReference.child(chosenTier).child(Integer.toString(i - 1)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d(TAG, "loading a question");
                    Question q = new Question();
                    q = dataSnapshot.getValue(Question.class);
                    questions.add(q);

                    progress += 100 / QUESTION_ARR_SIZE;
                    prg.setProgress(progress);


                    //  Toast.makeText(TiersActivity.this, "ADDED" + questions.size(), Toast.LENGTH_SHORT).show();
                    // Log.d(TAG, "Question parsed is : " + q);

                    if (questions.size() == QUESTION_ARR_SIZE) {
                        intent.putExtra("questions", questions);
                        startActivity(intent);

                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


//        if (questions.size() == 0) {
//            Thread.sleep(7000);
//
//        }
//        if (questions.size() > 0) {
//            Toast.makeText(this, "GO TO NEXT ACTIVITY", Toast.LENGTH_SHORT).show();
//
//        }
    }

    public void onLogout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, RegisterActivity.class));
    }


    @Override
    protected void onResume() {
        super.onResume();
        prg.setProgress(0);
        prg.setVisibility(View.INVISIBLE);
    }



}
