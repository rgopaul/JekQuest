package com.jarz.game;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.jarz.game.Activity.HighScoreScreen;
import com.jarz.game.Database.DataBaseManager;
import com.jarz.game.Main.Platformer;
/*
* The Base Launcher of the Android Version of the Game
* Implements "MyGameCallback" Interface to Launch Activities
* */
public class AndroidLauncher extends AndroidApplication implements Platformer.MyGameCallback
{
	private DataBaseManager db;
	String name = "Default";
	private boolean shouldAllowBack = false;

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		// create an instance of MyGame, and set the callback
		Platformer Game = new Platformer();
		Game.setMyGameCallback(this);

		//get user's name
		name = getIntent().getExtras().getString("NAME");

		//Check for Empty Name
		if(name.equals(""))
			name = "Jek";

		//instantiate Database
		db=new DataBaseManager(this);
		//Start Game
		initialize(Game, config);
	}

	//Callback Method to End the Game
	@Override
	public void endGame(int score)
	{
		db.submitScore(name, score);
		Intent intent = new Intent(this, HighScoreScreen.class);//highscore screen
		startActivity(intent);
	}

	//Disable Back Button
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
