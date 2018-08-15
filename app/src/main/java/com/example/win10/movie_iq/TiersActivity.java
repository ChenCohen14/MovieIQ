package com.example.win10.movie_iq;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
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
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TiersActivity extends AppCompatActivity {
    private static final String TAG = "TiersActivity";
    private final static int QUESTION_ARR_SIZE = 10;
    private static final int NUM_OF_TIERS = 5;
    private static final int NEXT_TIER_LIMIT = 6;
    private Soundtrack soundtrack;

    private ArrayList<Question> questions;
    private DatabaseReference databaseReference;
    private DatabaseReference userReference;

    private String[] soundtracks = new String[4];


    private ProgressBar prg;
    private int progress;
    private User theUser;
    private TextView userTextView;
    private TextView rankTextView;

    private Button btTier1;
    private Button btTier2;
    private Button btTier3;
    private Button btTier4;
    private Button btTier5;
    private Button[] tiersBtnArr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiers);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


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
        tiersBtnArr[0] = btTier1;
        tiersBtnArr[1] = btTier2;
        tiersBtnArr[2] = btTier3;
        tiersBtnArr[3] = btTier4;
        tiersBtnArr[4] = btTier5;

        //Show username + highscore + rank
        theUser = (User) getIntent().getSerializableExtra("user");
        userTextView = findViewById(R.id.userTextView);
        rankTextView = findViewById(R.id.rankTextView);
        userTextView.setText("Welcome " + theUser.getName() + "! Highscore is: " + theUser.getTotalPoints());
        rankTextView.setText(theUser.getRank());

        soundtrack = (Soundtrack) getIntent().getSerializableExtra("soundtrack");
        if(soundtrack != null) {
            soundtrack.getMediaPlayer().stop();
            soundtrack.getMediaPlayer().reset();
        }
        else
            soundtrack = new Soundtrack();
        setUpSoundtrack();


    }


    public void onClick(View view) {
        final Intent intent = new Intent(this, QuestionsActivity.class);
        final Button clickedBt = findViewById(view.getId());
        final String chosenTier = clickedBt.getText().toString().toLowerCase().replace(" ", "");

        lockOrEnableAllTiers(tiersBtnArr, false);

        if (!chosenTier.equalsIgnoreCase("tier1")) {
            if (!checkEligible(chosenTier, clickedBt)) {
                Toast.makeText(getApplicationContext(), "You must answer at least 5 question in the previous tier" +
                        " in order to open this one!", Toast.LENGTH_LONG).show();


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

                        intent.putExtra("user", theUser);
                        intent.putExtra("chosenTier", chosenTier);
                        intent.putExtra("soundtrack", soundtrack);
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
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        prg.setProgress(0);
        prg.setVisibility(View.INVISIBLE);
        theUser = (User) getIntent().getSerializableExtra("user");
    }

    @Override
    public void onBackPressed() {
        soundtrack.getMediaPlayer().stop();
        Intent intent = new Intent(this, StartGameActivity.class);
        intent.putExtra("user", theUser);
        intent.putExtra("TAG", TAG);
        startActivity(intent);
        finish();
    }


    private void lockOrEnableAllTiers(Button[] arr, boolean value) {
        for (int i = 0; i < arr.length; i++)
            arr[i].setClickable(value);
    }


    private void setUpSoundtrack() {
        Random rnd = new Random();
        int choice = rnd.nextInt(4);
        String uri;

        soundtracks[0] = "https://firebasestorage.googleapis.com/v0/b/movieiq2.appspot.com/o/inception%20soundtrack.mp3?alt=media&token=d2e2add9-a5db-48f5-b892-00ea6ef197f2";
        soundtracks[1] = "https://firebasestorage.googleapis.com/v0/b/movieiq2.appspot.com/o/amellie%20soundtack.mp3?alt=media&token=eb717767-49e3-48e5-8e44-5d1d6fbf9187";
        soundtracks[2] = "https://firebasestorage.googleapis.com/v0/b/movieiq2.appspot.com/o/stalker%20soundtrack.mp3?alt=media&token=9ec42903-b90a-409c-a1f6-ce8be8db3c41";
        soundtracks[3] = "https://firebasestorage.googleapis.com/v0/b/movieiq2.appspot.com/o/solaris%20soundtrack.mp3?alt=media&token=9681cb74-9fc9-4a75-8c77-6e37d4d3af41";
        uri = soundtracks[choice];

        try {
            soundtrack.getMediaPlayer().setDataSource(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            soundtrack.getMediaPlayer().prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        soundtrack.getMediaPlayer().start();
        soundtrack.getMediaPlayer().setLooping(true);
    }



}
