package com.example.win10.movie_iq;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FactsActivity extends AppCompatActivity {

    private TextView factText1;
    private TextView factText2;
    private TextView factText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        factText1 = findViewById(R.id.factText1);
        factText2 = findViewById(R.id.factText2);
        factText3 = findViewById(R.id.factText3);

        Question q = (Question) getIntent().getSerializableExtra("question");
        for(int i = 0; i < q.getFacts().size(); i++){
            String fact = q.getFacts().get(i);
            if(i == 0)
                factText1.setText(fact);
            else if(i == 1)
                factText2.setText(fact);
            else if(i == 2)
                factText3.setText(fact);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
