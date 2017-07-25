package com.development.orangemuffin.twoworlds.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.development.orangemuffin.twoworlds.MyGame;
import com.development.orangemuffin.twoworlds.entities.EntityManager;

/* Created by OrangeMuffin on 2015-06-28 */
public class InputHandler extends InputAdapter {

    private int jumpPointer, warpPointer, leftPointer, rightPointer;
    private EntityManager entityManager;
    private Vector3 touch;
    private OrthographicCamera camera;
    private int padsSide;


    public InputHandler(EntityManager entityManager, Vector3 touch, OrthographicCamera camera) {
        this.entityManager = entityManager;
        this.touch = touch;
        this.camera = camera;
        padsSide = AssetHandler.getPrefs("padsSide");
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.x = Gdx.input.getX(pointer);
        touch.y = Gdx.input.getY(pointer);
        camera.unproject(touch);

        if (padsSide == 0) { padsControlsRight(pointer); }
        else if (padsSide == 1) { padsControlsLeft(pointer); }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer == rightPointer) {
            entityManager.rightButton = false;
        }
        if (pointer == leftPointer) {
            entityManager.leftButton = false;
        }
        if (pointer == jumpPointer) {
            entityManager.jumpButton = false;
        }
        if (pointer == warpPointer) {
            entityManager.warpButton = false;
        }
        return true;
    }

    private void padsControlsRight(int pointer) {
        if (touch.x > (3* MyGame.WIDTH)/4 && touch.x < MyGame.WIDTH
                && touch.y < MyGame.HEIGHT-35) {
            entityManager.rightButton = true;
            rightPointer = pointer;
        }
        if (touch.x > (MyGame.WIDTH/2) && touch.x < (3*MyGame.WIDTH)/4) {
            entityManager.leftButton = true;
            leftPointer = pointer;
        }
        if (touch.x > 0 && touch.x < MyGame.WIDTH/2
                && touch.y > 0 && touch.y < MyGame.HEIGHT/2) {
            entityManager.jumpButton = true;
            jumpPointer = pointer;
        }
        if (touch.x > 0 && touch.x < MyGame.WIDTH/2
                && touch.y > MyGame.HEIGHT/2 && touch.y < MyGame.HEIGHT) {
            entityManager.warpButton = true;
            warpPointer = pointer;
        }
    }

    private void padsControlsLeft(int pointer) {
        if (touch.x > 0 && touch.x < (MyGame.WIDTH/4)) {
            entityManager.leftButton = true;
            leftPointer = pointer;
        }
        if (touch.x > (MyGame.WIDTH/4) && touch.x < (MyGame.WIDTH/2)) {
            entityManager.rightButton = true;
            rightPointer = pointer;
        }
        if (touch.x > (MyGame.WIDTH/2) && touch.x < MyGame.WIDTH
            && touch.y > 0 && touch.y < (MyGame.HEIGHT/2)) {
            entityManager.jumpButton = true;
            jumpPointer = pointer;
        }
        if (touch.x > (MyGame.WIDTH/2) && touch.x < MyGame.WIDTH
                && touch.y > (MyGame.HEIGHT/2) && touch.y < MyGame.HEIGHT) {
            entityManager.warpButton = true;
            warpPointer = pointer;
        }
    }

}
