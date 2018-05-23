package com.jarz.game.Sprites.InteractiveObjects;

import com.badlogic.gdx.maps.MapObject;
import com.jarz.game.Main.Platformer;
import com.jarz.game.Screens.Level2;
import com.jarz.game.Sprites.Player.Player;

/**
 * Created by Randy on 12/13/2017.
 */

/*
* INCOMPLETE OBJECT: Is Meant for Level 2 and is NOT Fully Implemented.
* This class sends collision data to the WorldContactListener to Change Player_Level2's "CanClimb" value to True.
* */
public class Ladder extends InteractiveTileObject
{

    public Ladder(Level2 screen, MapObject object)
    {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(Platformer.LADDER_BIT);
    }

    @Override
    public void onHeadHit(Player player) {

    }

    @Override
    public void onHeadHit() {

    }

    public void update(float dt)
    {

    }

}
