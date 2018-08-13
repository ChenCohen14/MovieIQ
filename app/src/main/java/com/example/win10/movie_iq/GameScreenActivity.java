package com.example.win10.movie_iq;


import android.content.Intent;
import android.content.pm.ActivityInfo;
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
    private TextView pointsText;
    private EditText answerEditText;
    private Question theQuestion;
    private Button hintBtn;
    private Button submitBtn;
    private Button factsBtn;
    private boolean[] isHint = new boolean[3];
    private ArrayList<String> solutions;
    private User theUser;
    private UserTierInfo userTierInfo;

    private TextView tierText;

    DatabaseReference databaseReference;
    private int numOfHintsTaken;

    private ArrayList<Question> questions;

    private Integer currentPoints;

    private static final int REDUCED_POINTS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


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


        theUser = (User) getIntent().getSerializableExtra("user");
        final String email = theUser.getUserEmail().replace(".", "_");


        userTierInfo = theUser.getUserTierInfoByTierName(tierText.getText().toString().toLowerCase().replace(" ", ""));

        currentPoints = userTierInfo.getCurrentPointsForQuestion().get(theQuestion.getAnswer());
        if (currentPoints == null)
            currentPoints = theQuestion.getCurrentPoints();
        pointsText.setText("Points " + currentPoints);


        numOfHintsTaken = userTierInfo.getNumOfHintsTaked(theQuestion.getAnswer());
        exposeHints(numOfHintsTaken);
        checkAnsweredQuestion(userTierInfo);


        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = numOfHintsTaken; i < isHint.length; i++) {
                    String hint = theQuestion.getHints().get(i);
                    if (isHint[i] == false) {
                        currentPoints -= REDUCED_POINTS;
                        userTierInfo.getCurrentPointsForQuestion().put(theQuestion.getAnswer(), currentPoints);


                        userTierInfo.addHintTakedIndexed(theQuestion.getAnswer());
                        databaseReference.child(email).setValue(theUser);


                        pointsText.setText("Points " + Integer.toString(currentPoints));
                        if (i == 0)
                            hintText1.setText(hint);
                        else if (i == 1)
                            hintText2.setText(hint);
                        else if (i == 2)
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
                String answer = answerEditText.getText().toString();
                for (int i = 0; i < solutions.size(); i++) {
                    if (answer.equalsIgnoreCase(solutions.get(i))) {

                        Toast.makeText(getApplicationContext(), "Well Done!!", Toast.LENGTH_SHORT).show();
                        theUser.setTotalPoints(currentPoints);
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


    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, QuestionsActivity.class);
        String chosenTier = tierText.getText().toString().toLowerCase().replace(" ","");
        intent.putExtra("chosenTier", chosenTier);
        intent.putExtra("user", theUser);
        intent.putExtra("questions", questions);
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
            pointsText.setText("Points " + Integer.toString(currentPoints));
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


