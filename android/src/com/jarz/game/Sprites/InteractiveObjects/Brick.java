package com.jarz.game.Sprites.InteractiveObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.jarz.game.Main.Platformer;
import com.jarz.game.Tools.HUD;
import com.jarz.game.Screens.Level1;
import com.jarz.game.Sprites.Player.Player;

/**
 * Created by Randy on 11/26/2017.
 */

/*
* The Brick Class Represents Super Mario Bricks in Level 1.
* */
public class Brick extends InteractiveTileObject
{
    public Brick(Level1 screen, MapObject object)
    {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(Platformer.BRICK_BIT);
    }

    //Is Called when a Player's Head Collides with This Object
    @Override
    public void onHeadHit(Player player)
    {
        setCategoryFilter(Platformer.DESTROYED_BIT); //Destroy the Object
        getCell().setTile(null); //Remove Texture
        HUD.addScore(200); //Add Points
        GAM.manager.get("audio/sounds/breakblock.wav", Sound.class).play(); //Play a Sound
    }

    @Override
    public void onHeadHit() {

    }

    public void update(float dt)
    {

    }

}
