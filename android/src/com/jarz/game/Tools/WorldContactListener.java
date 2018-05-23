package com.jarz.game.Tools;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.jarz.game.Main.Platformer;
import com.jarz.game.Sprites.Enemies.Enemy;
import com.jarz.game.Sprites.InteractiveObjects.InteractiveTileObject;
import com.jarz.game.Sprites.Player.Player;
import com.jarz.game.Sprites.Player.Player_Level3;

/**
 * This Class Handles ALL Contact between Player, Objects, and Enemies
 */
public class WorldContactListener implements ContactListener
{

    /*
    * Once Contact has been Made Between Two Fixtures
    * This Method Will be Called to Compare What Has Collided
    * Then Call a Method Based on the Collision
    * These are (currently) ALL of the Possible Collisions in the Game
    * In the Future Some of These Comparisons Will be Changed to Reduce Redundancy
    * */
    @Override
    public void beginContact(Contact contact)
    {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef)
        {
            case Platformer.PLAYER_HEAD_BIT | Platformer.BRICK_BIT:
            case Platformer.PLAYER_HEAD_BIT | Platformer.COIN_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.PLAYER_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Player) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Player) fixB.getUserData());
                break;
            case Platformer.ENEMY_HEAD_BIT | Platformer.Player_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead((Player) fixB.getUserData());
                else
                    ((Enemy)fixB.getUserData()).hitOnHead((Player) fixA.getUserData());
                break;
            case Platformer.ENEMY_BIT | Platformer.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Platformer.Player_BIT | Platformer.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.Player_BIT)
                    ((Player) fixA.getUserData()).hit((Enemy)fixB.getUserData());
                else
                    ((Player) fixB.getUserData()).hit((Enemy)fixA.getUserData());
                break;
            case Platformer.PLAYER_HEAD_BIT | Platformer.PORTAL_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.PLAYER_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit();
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit();
                break;
            case Platformer.Player_BIT | Platformer.PORTAL_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.PLAYER_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit();
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit();
                break;
            case Platformer.Player_BIT | Platformer.SPIKE_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.Player_BIT)
                    ((Player_Level3) fixA.getUserData()).die();
                else
                    ((Player_Level3) fixB.getUserData()).die();
                break;
            case Platformer.Player_BIT | Platformer.OBSTACLE_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.Player_BIT)
                    ((Player_Level3) fixA.getUserData()).hit((Enemy)fixB.getUserData());
                else
                    ((Player_Level3) fixB.getUserData()).hit((Enemy)fixA.getUserData());
                break;
            case Platformer.OBSTACLE_BIT | Platformer.GROUND_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.OBSTACLE_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(false, true);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(false, true);
                break;
            case Platformer.PLATFORM_BIT| Platformer.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.OBSTACLE_BIT)
                    ((InteractiveTileObject)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((InteractiveTileObject)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Platformer.ENEMY_BIT | Platformer.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
                /* REMOVED DUE TO INCOMPLETE STATUS. WILL BE WORKED ON IN THE FUTURE.
                case Platformer.PLAYER_HEAD_BIT | Platformer.LADDER_BIT:
                if(fixA.getFilterData().categoryBits == Platformer.PLAYER_HEAD_BIT)
                    ((Player_Level2) fixA.getUserData()).setClimb();
                else if (Gdx.input.isKeyPressed(Input.Keys.UP))
                    ((Player_Level2) fixB.getUserData()).setClimb();
                break;*/
        }
    }

    @Override
    public void endContact(Contact contact)
    {
        //Player_Level2.setClimbOff();
        //System.out.println("Lost Contact");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
