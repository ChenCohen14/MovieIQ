package com.example.win10.movie_iq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameScreenActivity extends AppCompatActivity {

    private static final String TAG = "GameScreenActivity";

    private TextView questionText;

    private VideoView vid ;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        //updateQuestion();
        vid = findViewById(R.id.videoView);
        vid.setVisibility(View.GONE);

        databaseReference = FirebaseDatabase.getInstance().getReference("questions");

        Button getQ = findViewById(R.id.getQ);
        final Question[] q = {new Question()};
        getQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "getQ clicked");
               databaseReference.child("tier1").child("0").addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       Log.d(TAG, "loading a question");
                       q[0] = dataSnapshot.getValue(Question.class);
                       Toast.makeText(GameScreenActivity.this, "GOT THE QUESTION", Toast.LENGTH_SHORT).show();
                       Log.d(TAG, "Question parsed is : " + q[0]);
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });
            }
        });

    }

//    private void updateQuestion(){
//        questionText = findViewById(R.id.questionTextView);
//        questionText.setText(q.getQuestion());
//
//    }

    public void onClick (View view){

        String id = databaseReference.push().getKey();

        Question q = new Question("","");
        HashMap <Integer,Question> lst = new HashMap<>();
        lst.put(0,q);
        databaseReference.child("tier1").child(""+0).setValue(lst.get(0));

        Toast.makeText(this,"q added",Toast.LENGTH_LONG).show();


//        final EditText answerEditText = findViewById(R.id.answerEditText);
//        String answer = answerEditText.getText().toString();
//        if (answer.equals(q.getAnswer())) {
//            vid.setVisibility(View.VISIBLE);
//            MediaController m = new MediaController(this);
//            vid.setMediaController(m);
//
//            String path = "android.resource://com.example.win10.movieiq/"+R.raw.thelionking;
//
//            Uri u = Uri.parse(path);
//
//            vid.setVideoURI(u);
//
//            vid.start();
//        }
//        else
//            Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show();
    }
}
