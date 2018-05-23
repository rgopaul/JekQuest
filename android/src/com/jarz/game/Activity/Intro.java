package com.jarz.game.Activity;

import android.app.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.jarz.game.R;

/**
 * Created by Randy on 12/16/2017.
 */

public class Intro extends Activity
{
    VideoView video;
    private boolean shouldAllowBack = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        video = (VideoView)findViewById(R.id.videoView);
        String videopath = "android.resource://"+getPackageName()+"/"+R.raw.test;

        Uri uri = Uri.parse(videopath);

        video.setVideoURI(uri);
        video.requestFocus();
        video.start();

        //Start Game After Intro Finishes
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer)
            {
                Intent intent = new Intent(Intro.this, Information.class);
                intent.putExtra("back", false);
                Intro.this.startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed() //Disable Back Button on this Screen
    {
        if (!shouldAllowBack)
        {

        } else {
            super.onBackPressed();
        }
    }



}
