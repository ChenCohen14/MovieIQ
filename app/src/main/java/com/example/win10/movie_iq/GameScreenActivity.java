package com.example.win10.movie_iq;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GameScreenActivity extends AppCompatActivity {

    private static final String TAG = "GameScreenActivity";

    private TextView questionText;
    private TextView hintText1;
    private TextView hintText2;
    private TextView hintText3;
    private TextView factsText1;
    private TextView factsText2;
    private TextView factsText3;
    private TextView tierText;
    private TextView pointsText;
    private EditText answerEditText;
    private Question theQuestion;
    private Button hintBtn;
    private Button submitBtn;
    private Button factsBtn;
    private String answer;
    private boolean[] isHint = new boolean[3];
    private boolean[] isFact = new boolean[3];
    private ArrayList<String> solutions;


    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        hintBtn = findViewById(R.id.hintButton);
        submitBtn = findViewById(R.id.submitButton);
        factsBtn = findViewById(R.id.raiseIQButton);
        answerEditText = findViewById(R.id.answerEditText);

        String chosenBt = getIntent().getExtras().getString("chosenBt");
        theQuestion = (Question) getIntent().getSerializableExtra(chosenBt);
        solutions = theQuestion.getSolutions();

        questionText = findViewById(R.id.questionTextView);
        hintText1 = findViewById(R.id.hintTextView1);
        hintText2 = findViewById(R.id.hintTextView2);
        hintText3 = findViewById(R.id.hintTextView3);
        factsText1 = findViewById(R.id.factTextView1);
        factsText2 = findViewById(R.id.factTextView2);
        factsText3 = findViewById(R.id.factTextView3);
        tierText = findViewById(R.id.tierTextView);
        pointsText = findViewById(R.id.pointTextView);

        questionText.setText(theQuestion.getQuestion());
        tierText.setText("Tier " + Integer.toString(theQuestion.getTier()));
        pointsText.setText("Points " + Integer.toString(theQuestion.getCurrentPoints()));

        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //databaseReference = FirebaseDatabase.getInstance().getReference("questions");

                //DatabaseReference condRef = databaseReference.child("tier"+Integer.toString(theQuestion.getTier())).child("0").child("currentPoints");

                // condRef.setValue(5);

                for (int i = 0; i < isHint.length; i++) {
                    String hint = theQuestion.getHints().get(i);
                    if (isHint[i] == false) {
                        theQuestion.setCurrentPoints(theQuestion.getCurrentPoints() - 2);
                        pointsText.setText("Points " + Integer.toString(theQuestion.getCurrentPoints()));
                        if (i == 0)
                            hintText1.setText(hint);
                        if (i == 1)
                            hintText2.setText(hint);
                        if (i == 2)
                            hintText3.setText(hint);
                        isHint[i] = true;
                        break;
                    }
                }
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = answerEditText.getText().toString();
                for (int i = 0; i < solutions.size(); i++) {
                    if (answer.equalsIgnoreCase(solutions.get(i))) {
                        Toast.makeText(getApplicationContext(), "Well Done!!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        });

        factsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < solutions.size(); i++) {
                    String fact = theQuestion.getFacts().get(i);
                    if (isFact[i] == false) {
                        if (i == 0) {
                            factsText1.setText(fact);
                            isFact[i] = true;
                            break;
                        }
                        if (i == 1) {
                            factsText2.setText(fact);
                            isFact[i] = true;
                            break;
                        }
                        if (i == 2) {
                            factsText3.setText(fact);
                            isFact[i] = true;
                            break;
                        }
                    }
                }
            }
        });


//    public void onClick (View view){
//
//        String id = databaseReference.push().getKey();
//
//        Question q = new Question("","");
//        HashMap <Integer,Question> lst = new HashMap<>();
//        lst.put(0,q);
//        databaseReference.child("tier1").child(""+0).setValue(lst.get(0));
//
//        Toast.makeText(this,"q added",Toast.LENGTH_LONG).show();


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
