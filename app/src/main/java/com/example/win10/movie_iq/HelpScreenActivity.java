package com.example.win10.movie_iq;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HelpScreenActivity extends AppCompatActivity {

    private String howToPlay;
    private String tip1;
    private String tip2;
    private String tip3;
    private String tip4;
    private TextView helpTextView;
    private TextView tip1TextView;
    private TextView tip2TextView;
    private TextView tip3TextView;
    private TextView tip4TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        helpTextView = findViewById(R.id.helpTextView);
        tip1TextView = findViewById(R.id.tip1TextView);
        tip2TextView = findViewById(R.id.tip2TextView);
        tip3TextView = findViewById(R.id.tip3TextView);
        tip4TextView = findViewById(R.id.tip4TextView);

        howToPlay = " The game is made out of 5 different tiers, designed to be increasingly difficult as you go along. "
                + "You'll be presented with a vague movie description, and all you have to do is figure it out and write it in the text box! A few pointers:";
        tip1 = "1) There are 3 hints to each movie.If the question is too hard you can always get a hint, but "
                + "bear in mind that the score for that particular question will decrease!";
        tip2 = "2) Abbreviations are not allowed. For example, let's say a movie description is presented, "
                + "and you figure out the answer is 'The Lord of the Rings', writing 'LOTR' won't do.You need to type the whole name.";
        tip3 = " 3) Don't write the entire movie name if it's a part of a franchise. If we continue with our 'The Lord of the Rings' example, "
                + "don't write 'The Lord of the Rings ג€“ The Return of the King' to get it right. Just writing 'The Lord of the Rings' will do just fine!";
        tip4 = "4) Lastly, there's no need to include 'The' in the answer. Writing 'Lord of the Rings' instead of 'The Lord of the Rings' is perfectly fine.";


        helpTextView.setText(howToPlay);
        tip1TextView.setText(tip1);
        tip2TextView.setText(tip2);
        tip3TextView.setText(tip3);
        tip4TextView.setText(tip4);

    }
}
