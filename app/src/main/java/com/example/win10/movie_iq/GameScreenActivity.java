package com.example.win10.movie_iq;


import android.content.Intent;
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
    private static final int FACTS_SIZE = 3;
    private ArrayList<String> solutions;
    private User theUser;
    private UserTierInfo userTierInfo;

    DatabaseReference databaseReference;
    private int numOfHintsTaken;

    private ArrayList<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        hintBtn = findViewById(R.id.hintButton);
        submitBtn = findViewById(R.id.submitButton);
        factsBtn = findViewById(R.id.raiseIQButton);
        answerEditText = findViewById(R.id.answerEditText);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        factsBtn.setEnabled(false);


        String chosenBt = getIntent().getExtras().getString("chosenBt");
        theQuestion = (Question) getIntent().getSerializableExtra(chosenBt);

        questions = (ArrayList<Question>) getIntent().getSerializableExtra("questions");

        solutions = theQuestion.getSolutions();

        tierText = findViewById(R.id.tierTextView);


        questionText = findViewById(R.id.questionTextView);
        hintText1 = findViewById(R.id.hintTextView1);
        hintText2 = findViewById(R.id.hintTextView2);
        hintText3 = findViewById(R.id.hintTextView3);
        pointsText = findViewById(R.id.pointTextView);

        questionText.setText(theQuestion.getQuestion());
        tierText.setText("Tier " + Integer.toString(theQuestion.getTier()));
        pointsText.setText("Points " + Integer.toString(theQuestion.getCurrentPoints()));

        theUser = (User) getIntent().getSerializableExtra("user");
        final String email = theUser.getUserEmail().replace(".", "_");


        userTierInfo = theUser.getUserTierInfoByTierName(tierText.getText().toString().toLowerCase().replace(" ", ""));




        //Question questionThatOpenedBefore = userTierInfo.getQuestionByAnswer(theQuestion.getAnswer());
        //if (questionThatOpenedBefore != null)
        //    theQuestion = questionThatOpenedBefore;



        numOfHintsTaken = userTierInfo.getNumOfHintsTaked(theQuestion.getAnswer());
        exposeHints(numOfHintsTaken);
        checkAnsweredQuestion(userTierInfo);


        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = numOfHintsTaken; i < isHint.length; i++) {
                    String hint = theQuestion.getHints().get(i);
                    if (isHint[i] == false) {
                        theQuestion.reducePoints();

                        userTierInfo.getCurrentPointsForQuestion().put(theQuestion.getAnswer(), theQuestion.getCurrentPoints());


                        userTierInfo.addHintTakedIndexed(theQuestion.getAnswer());
                        databaseReference.child(email).setValue(theUser);


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
                        theUser.setTotalPoints(theQuestion.getCurrentPoints());
                        userTierInfo.addAnsweredQuestion(theQuestion);
                        databaseReference.child(email).setValue(theUser);
                        submitBtn.setEnabled(false);
                        hintBtn.setEnabled(false);
                        factsBtn.setEnabled(true);
                        break;
                    }
                }

            }
        });

        factsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent factsIntent = new Intent(getApplicationContext(), FactsActivity.class);
                factsIntent.putExtra("question", theQuestion);
                startActivity(factsIntent);
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


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, QuestionsActivity.class);

        intent.putExtra("user", theUser);
        intent.putExtra("questions",questions);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        exposeHints(numOfHintsTaken);
    }

    public void exposeHints(int limit) {
        for (int k = 0; k < limit; k++)
            isHint[k] = true;
        int i = 0;
        while (i < isHint.length && isHint[i] == true) {
            String hint = theQuestion.getHints().get(i);
            pointsText.setText("Points " + Integer.toString(userTierInfo.getCurrentPointsForQuestion().get(theQuestion.getAnswer())));
            if (i == 0)
                hintText1.setText(hint);
            else if (i == 1)
                hintText2.setText(hint);
            else if (i == 2)
                hintText3.setText(hint);
            i++;
        }
    }

    private void checkAnsweredQuestion(UserTierInfo userTierInfo) {
        if (userTierInfo.getAnsweredQuestions() != null) {
            for (int i = 0; i < userTierInfo.getAnsweredQuestions().size(); i++) {
                if (theQuestion.getAnswer().equalsIgnoreCase(userTierInfo.getAnsweredQuestions().get(i).getAnswer())) {
                    answerEditText.setFocusable(false);
                    questionText.setText("The answer is:\n" + theQuestion.getAnswer() + "\nWell done!");
                    hintBtn.setEnabled(false);
                    submitBtn.setEnabled(false);
                    factsBtn.setEnabled(true);
                    break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}


