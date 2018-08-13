package com.example.win10.movie_iq;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        databaseReference = FirebaseDatabase.getInstance().getReference("users");


        final Intent intent = getIntent();
        String chosenTier = getIntent().getExtras().getString("chosenTier");
        theUser = (User) intent.getSerializableExtra("user");

        questions = (ArrayList<Question>) intent.getSerializableExtra("questions");
        questionsActivityGrid = findViewById(R.id.questionsActivityGrid);

        questionsActivityGrid.setColumnCount(1);
        questionsActivityGrid.setRowCount(questions.size());
        final Intent questionIntent = new Intent(this, GameScreenActivity.class);
        questionIntent.putExtra("user", theUser);

        questionIntent.putExtra("questions", questions);

        for (int i = 0; i < questions.size(); i++) {
            final Button bt = new Button(this);

            bt.setText("Question " + (i + 1));


            UserTierInfo userTierInfo = theUser.getUserTierInfoByTierName(chosenTier);
            if(userTierInfo.checkIfTheQuestionIsAnsweredByAnswer(questions.get(i).getAnswer()))
                bt.setTextColor(Color.BLUE);


            questionIntent.putExtra("question" + (i + 1), questions.get(i));
            questionsActivityGrid.addView(bt);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String chosenBt = bt.getText().toString().toLowerCase().replace(" ", "");
                    questionIntent.putExtra("chosenBt", chosenBt);


                    String transMail = theUser.getUserEmail().replace(".", "_");
                    databaseReference.child(transMail).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            if (user.getUserTierInfos() != null)
                                questionIntent.putExtra("user", user);

                            startActivity(questionIntent);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            });

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, TiersActivity.class);
        intent.putExtra("user", theUser);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
