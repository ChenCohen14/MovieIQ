package com.example.win10.movie_iq;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TiersActivity extends AppCompatActivity {
    private static final String TAG = "TiersActivity";
    private final static int QUESTION_ARR_SIZE = 10;

    private ArrayList<Question> questions;
    private DatabaseReference databaseReference;
    private DatabaseReference userReference;
    private ProgressBar prg;
    private int progress;
    private User theUser;
    private TextView userTextView;

    private Button btTier1;
    private Button btTier2;
    private Button btTier3;
    private Button btTier4;
    private Button btTier5;
    private Button [] tiersBtnArr;
    private static final int NUM_OF_TIERS = 5;
    private static final int NEXT_TIER_LIMIT = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiers);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        questions = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("questions");
        userReference = FirebaseDatabase.getInstance().getReference("users");
        prg = findViewById(R.id.progressBarTiers);
        prg.setVisibility(View.INVISIBLE);
        progress = 0;



        btTier1 = findViewById(R.id.tier1);
        btTier2 = findViewById(R.id.tier2);
        btTier3 = findViewById(R.id.tier3);
        btTier4 = findViewById(R.id.tier4);
        btTier5 = findViewById(R.id.tier5);

        tiersBtnArr = new Button[NUM_OF_TIERS];
        tiersBtnArr [0] = btTier1;
        tiersBtnArr [1] = btTier2;
        tiersBtnArr [2] = btTier3;
        tiersBtnArr [3] = btTier4;
        tiersBtnArr [4] = btTier5;







        //Show username + highscore
        theUser = (User)getIntent().getSerializableExtra("user");
        userTextView = findViewById(R.id.userTextView);
        userTextView.setText("Welcome " +theUser.getName() + "! Highscore is: " + theUser.getTotalPoints());













    }

    public void onClick(View view) {
        final Intent intent = new Intent(this, QuestionsActivity.class);
        final Button clickedBt = findViewById(view.getId());
        final String chosenTier = clickedBt.getText().toString().toLowerCase().replace(" ", "");


       lockOrEnableAllTiers(tiersBtnArr, false);

        if (!chosenTier.equalsIgnoreCase("tier1")) {
            if (!checkEligible(chosenTier, clickedBt)) {
                Toast.makeText(getApplicationContext(), "You must answer at least 5 question in the previous tier" +
                        "in order to open this one!", Toast.LENGTH_LONG).show();


                lockOrEnableAllTiers(tiersBtnArr, true);

                return;
            }
        }


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


                    if (questions.size() == QUESTION_ARR_SIZE) {
                        intent.putExtra("questions", questions);


                        UserTierInfo userTierInfo = new UserTierInfo(chosenTier);
                        if (!theUser.isExistUserTierInfo(userTierInfo)) {
                            theUser.addUserTierInfo(userTierInfo);
                            userReference.child(theUser.getUserEmail().replace(".", "_")).setValue(theUser);
                        }

                        Log.d("INSIDETIERS", "USER IS"+theUser);
                        intent.putExtra("user", theUser);
                        intent.putExtra("chosenTier",chosenTier);
                        startActivity(intent);




                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

    private boolean checkEligible(String chosenTier, Button clickedBt) {
        int t = Character.getNumericValue(chosenTier.charAt(chosenTier.length() - 1)) - 1;
        String prevTier = "tier" + t;
        if (theUser.getUserTierInfos() != null) {
            UserTierInfo uti = theUser.getUserTierInfoByTierName(prevTier);
            if (uti != null) {
                if (uti.getAnsweredQuestions().size() > NEXT_TIER_LIMIT) {
                    return true;

                }
            }
        }

        return false;


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
        theUser = (User) getIntent().getSerializableExtra("user");



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, RegisterActivity.class));
    }


    private void lockOrEnableAllTiers(Button [] arr,boolean value){
        for(int i = 0; i<arr.length ; i++)
            arr[i].setClickable(value);


    }

}
