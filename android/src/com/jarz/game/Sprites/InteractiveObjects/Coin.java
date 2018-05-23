package com.jarz.game.Sprites.InteractiveObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.jarz.game.Main.Platformer;
import com.jarz.game.Tools.HUD;
import com.jarz.game.Screens.Level1;
import com.jarz.game.Sprites.Player.Player;


/**
 * Created by Randy on 11/26/2017.
 */

/*
* The Brick Class Represents Super Mario Coin Blocks in Level 1.
* */
public class Coin extends InteractiveTileObject
{
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;

    public Coin(Level1 screen, MapObject object)
    {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(Platformer.COIN_BIT);
    }

    //Call When the Player Collides with This Object
    @Override
    public void onHeadHit(Player player)
    {
        if(getCell().getTile().getId() != BLANK_COIN) //If the Coin is Not Collected
        {
            GAM.manager.get("audio/sounds/coin.wav", Sound.class).play(); //Play a Sound
            getCell().setTile(tileSet.getTile(BLANK_COIN)); //Change the Graphic to a Blank Block
            HUD.addScore(100); //Add Points
        }
        else
            GAM.manager.get("audio/sounds/bump.wav", Sound.class).play(); //Coin is Already Collected so Play a Bump Sound for the Empty Block
    }

    @Override
    public void onHeadHit() {

    }

    public void update(float dt)
    {

    }

}
