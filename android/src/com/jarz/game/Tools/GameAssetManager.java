package com.jarz.game.Tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Randy on 12/4/2017.
 */

/*
* Asset Manager for Our Entire Game.
* Is Used to Load Assets Such as Music
* Will Eventually Be Expanded Upon to Load / Manage ALL Textures
* In Order to Help Keep Memory Usage at a Minimum
* */
public class GameAssetManager
{
    public final AssetManager manager = new AssetManager();


    //Loads All of Our Music at Once
    public void loadAssets()
    {
        manager.load("audio/music/BY8bit.mp3", Music.class);
        manager.load("audio/music/Mario.ogg", Music.class);
        manager.load("audio/music/cutman.mp3", Music.class);
        manager.load("audio/music/School.mp3", Music.class);
        manager.load("audio/sounds/breakblock.wav", Sound.class);
        manager.load("audio/sounds/coin.wav", Sound.class);
        manager.load("audio/sounds/bump.wav", Sound.class);
        manager.load("audio/sounds/stomp.wav", Sound.class);
    }

    //Remove from Memory
    public void dispose()
    {
        manager.dispose();
    }
}
