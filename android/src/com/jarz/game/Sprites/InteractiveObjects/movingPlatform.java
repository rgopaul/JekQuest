package com.jarz.game.Sprites.InteractiveObjects;

import com.jarz.game.Screens.Level3;
import com.jarz.game.Sprites.Player.Player;

/**
 * Created by Randy on 12/14/2017.
 */

//Incomplete (Will Eventually be Implemented in Level 3)
public class movingPlatform extends InteractiveTileObject
{
    private float stateTime;

    public movingPlatform(Level3 screen, float x, float y)
    {
        super(screen, x, y);
        //fixture.setUserData(this);
        //setCategoryFilter(Platformer.PLATFORM_BIT);
    }

    @Override
    public void onHeadHit(Player player) {
    }

    @Override
    public void onHeadHit() {

    }


    @Override
    public void update(float dt) {
        body.setLinearVelocity(velocity);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}
