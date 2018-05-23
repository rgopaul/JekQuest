package com.jarz.game.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jarz.game.R;

/**
 * Created by Randy on 12/16/2017.
 */

public class Introconfirm extends Activity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introconfirm);
        Button startGame = (Button) findViewById(R.id.no);
        Button startIntro = (Button) findViewById(R.id.yes);

        // start game click
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Introconfirm.this, Information.class);
                intent.putExtra("back", true);
                Introconfirm.this.startActivity(intent);


            }
        });

        // start intro click
        startIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Introconfirm.this, Intro.class);
                Introconfirm.this.startActivity(intent);

            }
        });
    }
}
