package com.jarz.game.Sprites.Enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.jarz.game.Main.Platformer;
import com.jarz.game.Tools.HUD;
import com.jarz.game.Screens.Level1;
import com.jarz.game.Sprites.Player.Player;

public class Goomba extends com.jarz.game.Sprites.Enemies.Enemy
{
    private float stateTime;
    private Animation <TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    float angle;


    public Goomba(Level1 screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();

        for(int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getEnemyatlas().findRegion("goomba"), i * 16, 0, 16, 16));

        walkAnimation = new Animation <TextureRegion>(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / Platformer.PPM, 16 / Platformer.PPM);
        setToDestroy = false;
        destroyed = false;
        angle = 0;
    }

    public void update(float dt)
    {
        stateTime += dt;
        if(setToDestroy && !destroyed)
        {
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getEnemyatlas().findRegion("goomba"), 32, 0, 16, 16));
            stateTime = 0;
            HUD.addScore(500);
        }
        else if(!destroyed)
        {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Platformer.PPM);
        fdef.filter.categoryBits = Platformer.ENEMY_BIT;
        fdef.filter.maskBits = Platformer.GROUND_BIT |
                Platformer.COIN_BIT |
                Platformer.BRICK_BIT |
                Platformer.ENEMY_BIT |
                Platformer.OBJECT_BIT |
                Platformer.Player_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        //Create the Head here:
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 9).scl(1 / Platformer.PPM);
        vertice[1] = new Vector2(5, 9).scl(1 / Platformer.PPM);
        vertice[2] = new Vector2(-4.6f, 2).scl(1 / Platformer.PPM);
        vertice[3] = new Vector2(4.6f, 2).scl(1 / Platformer.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = Platformer.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    public void hitOnHead(Player player) {
        setToDestroy = true;
        GAM.manager.get("audio/sounds/stomp.wav", Sound.class).play();
    }

    @Override
    public void hitByEnemy(Enemy enemy) {
            reverseVelocity(true, false);
    }
}
