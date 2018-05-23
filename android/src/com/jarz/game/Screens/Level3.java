package com.jarz.game.Screens;

/**
 * Created by Randy on 12/14/2017.
 */

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jarz.game.Main.Platformer;
import com.jarz.game.Sprites.InteractiveObjects.InteractiveTileObject;
import com.jarz.game.Tools.HUD;
import com.jarz.game.Sprites.Enemies.Enemy;
import com.jarz.game.Sprites.Player.Player_Level3;
import com.jarz.game.Tools.B2WorldCreator;
import com.jarz.game.Tools.Controller;
import com.jarz.game.Tools.GameAssetManager;
import com.jarz.game.Tools.WorldContactListener;

/*
* The First Level of the Game:
* - handleInput: Is called on every update and handles grabbing inputs from the user.
* - update: Is called every frame the game is rendered. Calls Methods that require constant updates and updates the Camera position.
* - render: Draws our resources to the screen. (Checks if the Player has Died After Rendering)
* - resize: Resizes the game based on the android's screen size.
* - gameOver: Returns true if the Player's state is "dead"
* - dispose: Dumps all aspects of the Game on Call. (Used to Free up Memory)
* */
public class Level3 implements Screen
{
    private Platformer game;
    private OrthographicCamera gamecam; //Follows Player in the World
    private Viewport gamePort; //Restricts Aspect Ratio of the Game to Fit Screens
    private com.jarz.game.Tools.HUD HUD;
    public GameAssetManager GAM;
    Controller controller;

    private TextureAtlas atlas;
    private TextureAtlas portalAtlas;
    private TextureAtlas Enemyatlas;
    private boolean latch = true;

    //Our Player Object
    private Player_Level3 player;
    private float Default_Gravity;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //Game Map Related Variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    MapProperties mapProp;
    private OrthogonalTiledMapRenderer renderer;

    /* Camera Variables */
    private int mapLeft;
    private int mapRight;
    private int mapBottom;
    private int mapTop;
    private float cameraHalfWidth;
    private float cameraHalfHeight;
    private float cameraLeft;
    private float cameraRight;
    private float cameraBottom;
    private float cameraTop;


    /*
    * Main Constructor:
    * Instantiates HUD, Player, Enemies, Cameras, Music, and World Collisions.
    * */
    public Level3(Platformer game, HUD hud, int score)
    {
        atlas = new TextureAtlas("Jekplz.pack"); //Grab Main Character Sprite Information
        Enemyatlas = new TextureAtlas("obstacle.pack"); //Obstacle Sprite Information
        portalAtlas = new TextureAtlas("Portal.pack"); //Portal Sprite Information

        //Set Cameras
        this.game = game;
        this.GAM = game.GAM;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Platformer.V_WIDTH / Platformer.PPM, Platformer.V_HEIGHT / Platformer.PPM, gamecam); //Using FitViewport assures that Game Maintains the Aspect Ratio on Any Screen
        this.HUD = hud; //Instantiate The Score Display of the Game
        HUD.setScore(score);
        HUD.setWorld("SCHOOL");
        HUD.setLevel("Level 2");

        //Load Map and Assign a Camera / Renderer to the Game
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Stages/obstacle.tmx");
        mapProp = map.getProperties();
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Platformer.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,-6.8f), true); //First Parameter Sets Gravity
        b2dr = new Box2DDebugRenderer(); //Debug Lines
        creator = new B2WorldCreator(this);
        world.setContactListener(new WorldContactListener()); //Contact Listener for All In-game Collisions
        controller = new Controller(game.batch);

        //Instantiate Player Object
        player = new Player_Level3(world, this);

        //Grab Default Gravity
        Default_Gravity = player.b2body.getGravityScale();

        //Add some Music Assets
        GAM.loadAssets();
        GAM.manager.finishLoading();
        GAM.manager.get("audio/music/School.mp3", Music.class).setLooping(true);
        GAM.manager.get("audio/music/School.mp3", Music.class).play();
    }

    public HUD getHud(){ return HUD; }
    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }
    public TextureAtlas getAtlas()
    {
        return atlas;
    }
    public TextureAtlas getEnemyatlas(){return Enemyatlas;}
    public TextureAtlas getPortalatlas(){return portalAtlas;}

    //Handles Inputs from the Player (Allows Movement)
    public void handleInput(float dt)
    {
        if(player.currentState != Player_Level3.State.DEAD)
        {
              //keyboard controls
                if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y == 0)
                    player.b2body.applyLinearImpulse(new Vector2(0, 2.8f), player.b2body.getWorldCenter(), true);

                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2.0)
                    player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);

                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2.0)
                    player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);

                //touch controls
                if (controller.isUpPressed() && player.b2body.getLinearVelocity().y == 0) {
                    Gdx.app.log("Touched", "UP");
                    player.b2body.applyLinearImpulse(new Vector2(0, 2.8f), player.b2body.getWorldCenter(), true);
                    controller.upPressed = false;
                }

                if (controller.isRightPressed() && player.b2body.getLinearVelocity().x <= 2.0) {
                    Gdx.app.log("Touched", "RIGHT");
                    player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
                }

                if (controller.isLeftPressed() && player.b2body.getLinearVelocity().x >= -2.0) {
                    Gdx.app.log("Touched", "LEFT");
                    player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
                }
            }

    }

    // dt stands for Delta Time
    public void update(float dt)//Constantly Checks for Inputs / Updates Aspects of the Game
    {
        handleInput(dt);

        world.step(1/60f, 6, 2);
        player.update(dt);
        HUD.update(dt);

        //Updates Enemies
        for(Enemy enemy : creator.getObstacles()) {enemy.update(dt);}

        //Update Portal
        for(InteractiveTileObject portals : creator.getPortals()) {portals.update(dt);}

        //attach our gamecam to our players.x coordinate if they are alive
        if(player.currentState != Player_Level3.State.DEAD)
        {
            //Camera Follows Player
            gamecam.position.x = player.b2body.getPosition().x;
            gamecam.position.y = player.b2body.getPosition().y;

            //MAKES SURE THE CAMERA DOES NOT GO OUT OF BOUNDS OF THE GAME MAP.
            // The left boundary of the map (x)
            mapLeft = 0;
            // The right boundary of the map (x + width)
            mapRight = 0 + mapProp.get("width",Integer.class);
            // The bottom boundary of the map (y)
            mapBottom = 0;
            // The top boundary of the map (y + height)
            mapTop = 0 + mapProp.get("height",Integer.class);
            // The camera dimensions, halved
            cameraHalfWidth = gamecam.viewportWidth * .5f;
            cameraHalfHeight = gamecam.viewportHeight * .5f;

            // Move camera after player as normal
            cameraLeft = gamecam.position.x - cameraHalfWidth;
            cameraRight = gamecam.position.x + cameraHalfWidth;
            cameraBottom = gamecam.position.y - cameraHalfHeight;
            cameraTop = gamecam.position.y + cameraHalfHeight;

            // Horizontal axis
            if(mapProp.get("width",Integer.class) < gamecam.viewportWidth) {gamecam.position.x = mapRight / 2;}
            else if(cameraLeft <= mapLeft) {gamecam.position.x = mapLeft + cameraHalfWidth;}
            else if(cameraRight >= mapRight) {gamecam.position.x = mapRight - cameraHalfWidth;}

            // Vertical axis
            if(mapProp.get("width",Integer.class) < gamecam.viewportHeight) {gamecam.position.y = mapTop / 2;}
            else if(cameraBottom <= mapBottom) {gamecam.position.y = mapBottom + cameraHalfHeight;}
            else if(cameraTop >= mapTop) {gamecam.position.y = mapTop - cameraHalfHeight;}
        }

        gamecam.update();
        renderer.setView(gamecam);
    }



    @Override
    public void render(float delta) //Draws Objects to the Screen
    {
        update(delta);

        //Clears Screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Renders Screen
        renderer.render();

        //renderer out Box2DDebugLines
        //b2dr.render(world,gamecam.combined);

        //Render Main Character, Enemies, and Portal
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Enemy enemy : creator.getObstacles())
            enemy.draw(game.batch);

        for(InteractiveTileObject portals : creator.getPortals())
            portals.draw(game.batch);

        game.batch.end();

        //Render Controller
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            controller.draw();

        //Set our batch to draw what the HUD Camera Sees
        game.batch.setProjectionMatrix(HUD.stage.getCamera().combined);
        HUD.stage.draw();

        //Go to GameOverScreen after Death.
        if(gameOver())
        {
            game.myGameCallback.endGame(HUD.getScore());
            this.dispose();
        }
    }

    //Enter Next Level
    public void setScreen()
    {
        game.GAM.manager.get("audio/music/School.mp3", Music.class).stop();
        HUD.addScore(HUD.getTime() * 3);
        game.setScreen(new Level2(game, HUD, HUD.getScore()));
    }

    @Override
    public void resize(int width, int height) //Update Size of Game Screen
    {
        gamePort.update(width, height);
        controller.resize(width, height);
    }

    public boolean gameOver(){
        if(player.currentState == Player_Level3.State.DEAD && player.getStateTimer() > 3) {return true;}
        return false;
    }

    //Used for Memory Management
    @Override
    public void dispose()
    {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        HUD.dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide(){}

    @Override
    public void show() {}

}
