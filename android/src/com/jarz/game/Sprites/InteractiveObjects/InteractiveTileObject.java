package com.jarz.game.Sprites.InteractiveObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jarz.game.Screens.Level2;
import com.jarz.game.Screens.Level3;
import com.jarz.game.Sprites.Player.Player;
import com.jarz.game.Tools.GameAssetManager;
import com.jarz.game.Main.Platformer;
import com.jarz.game.Screens.Level1;

/**
 * Created by Randy on 11/26/2017.
 */

/*
* The Interactive Tile Object Class Represents all Tiles that Can Perform "Interactions" with the Player
* Examples of Interactions can Range from Breakable Bricks, Portals, and Ladders.
* Note that there are Multiple Constructors that are Meant for Different Levels
* Each Level has Their Own Set of Interactive Objects That Vary From one Another.
* */
public abstract class InteractiveTileObject extends Sprite
{
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected Level1 screen;
    protected Level2 screen2;
    protected Level3 screen3;
    protected MapObject object;
    public Vector2 velocity;


    protected Fixture fixture;

    public GameAssetManager GAM;
    public abstract void update(float dt);

    //Constructor for Level 1 That Instantiates Coins / Brick / Portal Objects
    public InteractiveTileObject(Level1 screen, MapObject object){
        this.object = object;
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();
        this.GAM = screen.GAM;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Platformer.PPM, (bounds.getY() + bounds.getHeight() / 2) / Platformer.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / Platformer.PPM, bounds.getHeight() / 2 / Platformer.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);

    }

    //Constructor for Level 2 That Instantiates Ladder Objects
    public InteractiveTileObject(Level2 screen, MapObject object){
        this.object = object;
        this.screen2 = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();
        this.GAM = screen.GAM;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        fdef.isSensor = true;
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Platformer.PPM, (bounds.getY() + bounds.getHeight() / 2) / Platformer.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / Platformer.PPM, bounds.getHeight() / 2 / Platformer.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);

    }

    //Constructor for Level 3 That Instantiates Moving Platforms (Not Fully Implemented)
    public InteractiveTileObject(Level3 screen, float x, float y)
    {
        this.world = screen.getWorld();
        this.screen3 = screen;
        this.GAM = screen.GAM;
        setPosition(x,y);

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / Platformer.PPM);
        fdef.filter.categoryBits = Platformer.PLATFORM_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

    }

    //Instantiates Only Portal Objects for Level 3 at the Moment.
    public InteractiveTileObject(Level3 screen, MapObject object)
    {
        this.object = object;
        this.screen3 = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();
        this.GAM = screen.GAM;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Platformer.PPM, (bounds.getY() + bounds.getHeight() / 2) / Platformer.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / Platformer.PPM, bounds.getHeight() / 2 / Platformer.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);

    }

    //Methods that Activate on Collision
    public abstract void onHeadHit(Player player);
    public abstract void onHeadHit();

    //Set Collision Data
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int)(body.getPosition().x * Platformer.PPM / 16),
                (int)(body.getPosition().y * Platformer.PPM / 16));
    }

    //This Method is NOT Fully Implemented and Is Meant for "movingPlatform.java"
    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }

}
