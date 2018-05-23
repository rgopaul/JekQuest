package com.jarz.game.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.icu.text.IDNA;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.jarz.game.AndroidLauncher;
import com.jarz.game.R;

public class MainActivity extends Activity
{
    WebView webView; //Web Viewer to Display a Gif via HTML
    MediaPlayer music; //Music Player
    int length = 0;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        Button startGame = (Button)findViewById(R.id.button);
        Button viewScores = (Button)findViewById(R.id.button2);
        Button exitGame = (Button) findViewById(R.id.button3);

        webView = (WebView) findViewById(R.id.webView); //Grab Gif
        webView.loadUrl("file:///android_asset/scanline.html"); //Display Gif
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        music = MediaPlayer.create(this, R.raw.a2080);
        music.start(); //Start Music!

        //Disable Scrolling on WebView
        webView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });
        // handle set start click
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Introconfirm.class);
                MainActivity.this.startActivity(intent);

            }
        });

        // handle view scores click
        viewScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HighScoreScreen.class);
                MainActivity.this.startActivity(intent);
            }
        });
        // handle exit click
        exitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    @Override
    protected void onPause() //In Case the User Backs Out
    {
        super.onPause();
        music.pause();
        length=music.getCurrentPosition();
    }

    @Override
    protected void onStop() //In Case the User Backs Out
    {
        super.onStop();
        music.pause();
        length=music.getCurrentPosition();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        music.seekTo(length);
        music.start();
    }
}
