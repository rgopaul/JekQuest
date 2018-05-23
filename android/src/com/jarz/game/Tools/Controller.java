package com.jarz.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Zaid on 12/4/2017.
 */

public class Controller {
    Viewport viewport;
    Stage stage;
    OrthographicCamera cam;
    public boolean upPressed, leftPressed, downPressed, rightPressed;

    public Controller(SpriteBatch batch)
    {
        cam = new OrthographicCamera();
        viewport = new FitViewport(800, 400, cam);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.left().bottom();

        Table table2 = new Table();
        table.left().bottom();

        Image up = new Image(new Texture("up.png"));
        up.setSize(70, 70);
        up.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("TAG", "Touched down");
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("TAG", "Touched up");
                upPressed = false;
            }
        });


        Image left = new Image(new Texture("left.png"));
        left.setSize(70, 70);
        left.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        Image right = new Image(new Texture("right.png"));
        right.setSize(70, 70);
        right.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        table2.padLeft(1500);
        table2.padBottom(85);
        table2.add(up).size(up.getWidth(), up.getHeight()).right();

        table.row().pad(5, 20, 5, 5);
        table.add(left).size(left.getWidth(), left.getHeight());
        table.add();
        table.add(right).size(right.getWidth(), right.getHeight());
        table.row().padBottom(5);
        table.add();

        stage.addActor(table);
        stage.addActor(table2);
    }

    public void draw(){
            stage.draw();
    }

    public boolean isUpPressed() {return upPressed;}

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }
}
