package com.jarz.game.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.jarz.game.Screens.Level1;
import com.jarz.game.Screens.Level3;
import com.jarz.game.Tools.GameAssetManager;
import com.jarz.game.Sprites.Player.Player;

public abstract class Enemy extends Sprite {
    protected World world;
    protected Level1 screen;
    protected Level3 screen3;
    public Body b2body;
    public Vector2 velocity;
    public GameAssetManager GAM;

    public Enemy(Level1 screen, float x, float y)
    {
        this.world = screen.getWorld();
        this.screen = screen;
        this.GAM = screen.GAM;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(-1, -2);
        b2body.setActive(false);
    }

    public Enemy(Level3 screen, float x, float y)
    {
        this.world = screen.getWorld();
        this.screen3 = screen;
        this.GAM = screen.GAM;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(0, -1);
        b2body.setActive(true);
    }

    protected abstract void defineEnemy();
    public abstract void update(float dt);
    public abstract void hitOnHead(Player player);
    public abstract void hitByEnemy(Enemy enemy);

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }
}
