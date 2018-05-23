package com.jarz.game.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jarz.game.Database.DataBaseManager;
import com.jarz.game.R;
import com.jarz.game.Database.Scores;

import java.util.ArrayList;

/*
* The Highscore Screen Implements an SQL Database to Keep Track of The Top 10 Scores
* Once the User has Reached this Screen It Means the Game is Either Over or They Are Simply Checking the Scores
* Regardless, Upon Exiting the Activity Stack will be Cleared to Prevent The User from Attempting to Access Previous Gamestates
* */
public class HighScoreScreen extends Activity
{
    private int buttonWidth;
    private DataBaseManager db;
    private TextView scores;
    private TextView names;
    private boolean shouldAllowBack = false;
    private Button goBack;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore);
        scores = (TextView)findViewById(R.id.scores);
        names = (TextView)findViewById(R.id.names);
        scores.setTextSize(30);
        names.setTextSize(30);

        goBack = (Button)findViewById(R.id.exitButton);

        // Removes all Previous Activities from the Stack and Returns the the Main Menu
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HighScoreScreen.this, MainActivity.class);
                // set the new task and clear flags
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });


        db = new DataBaseManager(this);

        for(int i = 0; i < 10; i++)
            db.submitScore("AAA", 0);

        Point size = new Point( );
        getWindowManager( ).getDefaultDisplay( ).getSize( size );
        buttonWidth = size.x;

        updateView( );

    }

    public void updateView()
    {
        ArrayList<Scores> score = db.selectAll();
        String nametext = "";
        String scoretext = "";
        for (Scores scores : score)
        {
            nametext += scores.getName() + "\n";
            scoretext += scores.getScore() + "\n";
        }

        scores.setText(scoretext);
        names.setText(nametext);
    }

    @Override
    public void onBackPressed() //Disable Back Button on this Screen
    {
        if (!shouldAllowBack)
        {
            // Removes all Previous Activities from the Stack and Returns the the Main Menu
            Intent i = new Intent(HighScoreScreen.this, MainActivity.class);
            // set the new task and clear flags
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            super.onBackPressed();
        }
    }
}
