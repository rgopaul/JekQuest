package com.jarz.game.Sprites.InteractiveObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.utils.Array;
import com.jarz.game.Main.Platformer;
import com.jarz.game.Screens.Level1;
import com.jarz.game.Screens.Level3;
import com.jarz.game.Sprites.Player.Player;

/**
 * Created by user on 12/11/2017.
 */

/*
* The Portal Class Represents an Exit Point of Any Level.
* When a Player Collides with a Portal they Are Transported to Another Level Based on The Current Level.
* */
public class Portal extends InteractiveTileObject
{
    private float stateTime;
    int level = 0;
    private Animation<TextureRegion> portalAnimation;
    private Array<TextureRegion> frames;

    //Multiple Constructors Based on Current Level
    public Portal(Level1 screen, MapObject object)
    {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(Platformer.PORTAL_BIT);
        level = 1;

        //Create Animation
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getPortalatlas().findRegion("portal0"), 2, 2, 64, 64));
        frames.add(new TextureRegion(screen.getPortalatlas().findRegion("portal1"), 2, 2, 64, 64));
        frames.add(new TextureRegion(screen.getPortalatlas().findRegion("portal3"), 2, 2, 64, 64));
        frames.add(new TextureRegion(screen.getPortalatlas().findRegion("portal6"), 2, 2, 64, 64));
        frames.add(new TextureRegion(screen.getPortalatlas().findRegion("portal7"), 2, 2, 64, 64));
        frames.add(new TextureRegion(screen.getPortalatlas().findRegion("portal8"), 2, 2, 64, 64));
        frames.add(new TextureRegion(screen.getPortalatlas().findRegion("portal9"), 2, 2, 64, 64));
        frames.add(new TextureRegion(screen.getPortalatlas().findRegion("portal10"), 2, 2, 64, 64));

        portalAnimation = new Animation <TextureRegion>(0.07f, frames);
        stateTime = 0;
        setBounds(32f, getY(), 110 / Platformer.PPM, 110 / Platformer.PPM);
    }

    public Portal(Level3 screen, MapObject object)
    {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(Platformer.PORTAL_BIT);
        level = 3;

        //Create Animation
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getPortalatlas().findRegion("portal0"), 2, 2, 64, 64));
        frames.add(new TextureRegion(screen.getPortalatlas().findRegion("portal1"), 2, 2, 64, 64));
        frames.add(new TextureRegion(screen.getPortalatlas().findRegion("portal3"), 2, 2, 64, 64));
        frames.add(new TextureRegion(screen.getPortalatlas().findRegion("portal6"), 2, 2, 64, 64));
        frames.add(new TextureRegion(screen.getPortalatlas().findRegion("portal7"), 2, 2, 64, 64));
        frames.add(new TextureRegion(screen.getPortalatlas().findRegion("portal8"), 2, 2, 64, 64));
        frames.add(new TextureRegion(screen.getPortalatlas().findRegion("portal9"), 2, 2, 64, 64));
        frames.add(new TextureRegion(screen.getPortalatlas().findRegion("portal10"), 2, 2, 64, 64));

        portalAnimation = new Animation <TextureRegion>(0.07f, frames);
        stateTime = 0;
        setBounds(58.3f, getY(), 110 / Platformer.PPM, 110 / Platformer.PPM);
    }

    //On Collision Set the Next Level.
    @Override
    public void onHeadHit(Player player)
    {
        screen.setScreen();
    }

    @Override
    public void onHeadHit()
    {
        if(level == 1)
            screen.setScreen();
        else if(level == 3)
            screen3.setScreen();
    }
    //Update Every One 60th of a Second
    @Override
    public void update(float dt)
    {
        stateTime += dt;
        setRegion(portalAnimation.getKeyFrame(stateTime, true));
    }

}
