package com.jarz.game.Main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jarz.game.Screens.Level1;
import com.jarz.game.Tools.GameAssetManager;
/*
* The Main Entry Point of the Game
* Instantiates All Important Variables that Are Used Throughout Every Class
* Then Launches the First Level
* */
public class Platformer extends Game
{
	public SpriteBatch batch; //Holds all Ingame Characters
	public static final int V_WIDTH = 400;//Virtual Width
	public static final int V_HEIGHT = 208;//Virtual Height
	public static final float PPM = 100; //Pixels Per Meter
    public GameAssetManager GAM = new GameAssetManager(); //Asset Manager (Used to Manage Music, Sounds, Extra Textures, etc)

	// Define an interface for various callbacks to the android launcher
	public interface MyGameCallback
	{
		public void endGame(int score);
	}

	// Local variable to hold the callback implementation
	public MyGameCallback myGameCallback;

	// Setter for the callback
	public void setMyGameCallback(MyGameCallback callback) {
		myGameCallback = callback;
	}


	//Box2D Collision Bits
	public static final short NOTHING_BIT = 0; //No Collision
	public static final short GROUND_BIT = 1;
	public static final short Player_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short PORTAL_BIT = 256;
	public static final short LADDER_BIT = 320;
	public static final short SPIKE_BIT = 384;
	public static final short OBSTACLE_BIT = 448;
	public static final short PLAYER_HEAD_BIT = 512;
	public static final short PLATFORM_BIT = 576;


	@Override
	public void create () //Start Game
	{
		batch = new SpriteBatch();
		setScreen(new Level1(this));
	}

	//Memory Management
	@Override
	public void dispose()
    {
		super.dispose();
		batch.dispose();
		GAM.dispose();
	}

	@Override
	public void render ()
	{
		super.render();
	}

}
