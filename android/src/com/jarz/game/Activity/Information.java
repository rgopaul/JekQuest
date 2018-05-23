package com.jarz.game.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jarz.game.AndroidLauncher;
import com.jarz.game.R;

/**
 * Created by Margarita on 12/13/2017.
 */

/*
* This Activity is Used to Grab the User's Player Name
* The Name is then Transferred Over to the Android Launcher Before Starting the Game.
* */
public class Information extends Activity
{
    Button beginGame;
    private boolean shouldAllowBack = true;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        beginGame = (Button)findViewById(R.id.submit);

        shouldAllowBack = getIntent().getExtras().getBoolean("back");

        // handle set start click
        beginGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Information.this, AndroidLauncher.class);
                EditText editText=(EditText)findViewById(R.id.nameText);
                String name=editText.getText().toString();
                intent.putExtra("NAME", name);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            setContentView(R.layout.information);
            beginGame = (Button)findViewById(R.id.submit);

            beginGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Information.this, AndroidLauncher.class);
                    EditText editText=(EditText)findViewById(R.id.nameText);
                    String name=editText.getText().toString();
                    intent.putExtra("NAME", name);
                    startActivity(intent);
                }
            });
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.information);
            beginGame = (Button)findViewById(R.id.submit);

            beginGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Information.this, AndroidLauncher.class);
                    EditText editText=(EditText)findViewById(R.id.nameText);
                    String name=editText.getText().toString();
                    intent.putExtra("NAME", name);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBackPressed() //Disable Back Button on this Screen
    {
        if (!shouldAllowBack)
        {
            // Removes all Previous Activities from the Stack and Returns the the Main Menu
            Intent i = new Intent(Information.this, MainActivity.class);
            // set the new task and clear flags
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            super.onBackPressed();
        }
    }

}
