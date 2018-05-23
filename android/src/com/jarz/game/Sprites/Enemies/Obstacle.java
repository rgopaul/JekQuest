package com.jarz.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.jarz.game.Main.Platformer;
import com.jarz.game.Screens.Level3;
import com.jarz.game.Sprites.Player.Player;

/**
 * Created by Randy on 12/14/2017.
 */

/*
* This is the Obstacle Class that is Used in Level 3
* The Class Shares Similarities to the Goomba Class
* With the One Exception being Hitbox Size and Collision Detection
* */
public class Obstacle extends Enemy
{
    private float stateTime;
    private Animation<TextureRegion> glowAnimation;
    private Array<TextureRegion> frames;
    float angle;

    public Obstacle(Level3 screen, float x, float y)
    {
        super(screen, x, y);
        frames = new Array<TextureRegion>(); //Set Frames

        //Add Animations
        frames.add(new TextureRegion(screen.getEnemyatlas().findRegion("obstacle0000"), 2, 2, 32, 32));
        frames.add(new TextureRegion(screen.getEnemyatlas().findRegion("obstacle0001"), 2, 2, 32, 32));
        frames.add(new TextureRegion(screen.getEnemyatlas().findRegion("obstacle0002"), 2, 2, 32, 32));
        glowAnimation = new Animation <TextureRegion>(0.2f, frames);

        stateTime = 0;
        setBounds(getX(), getY(), 32 / Platformer.PPM, 32 / Platformer.PPM);
        angle = 0;
    }

    //Define Hitboxes
    @Override
    protected void defineEnemy()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / Platformer.PPM);
        //Define Collision Bits
        fdef.filter.categoryBits = Platformer.OBSTACLE_BIT;
        fdef.filter.maskBits = Platformer.GROUND_BIT |
                Platformer.COIN_BIT |
                Platformer.BRICK_BIT |
                Platformer.ENEMY_BIT |
                Platformer.OBJECT_BIT |
                Platformer.Player_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    //Update Every One 60th of a Second
    @Override
    public void update(float dt)
    {
        stateTime += dt;
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(glowAnimation.getKeyFrame(stateTime, true));
    }

    public void hitGround(){
        velocity.y = -velocity.y;
    }

    //Unused Methods
    @Override
    public void hitOnHead(Player player) {

    }

    @Override
    public void hitByEnemy(Enemy enemy) {

    }
}
