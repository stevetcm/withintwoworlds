package com.development.orangemuffin.twoworlds.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.development.orangemuffin.twoworlds.MyGame;
import com.development.orangemuffin.twoworlds.handlers.AssetHandler;
import com.development.orangemuffin.twoworlds.handlers.ShaderHandler;

/* Created by OrangeMuffin on 2015-06-26 */
public abstract class State {
    protected GSM gsm;
    protected OrthographicCamera camera;
    protected Vector3 touch;
    protected ShaderProgram halfGrayShader;
    protected ShaderProgram fullGrayShader;
    protected Texture wbg, bbg;

    protected State(GSM gsm) {
        this.gsm = gsm;
        camera = new OrthographicCamera(MyGame.WIDTH, MyGame.HEIGHT);
        camera.setToOrtho(false, MyGame.WIDTH, MyGame.HEIGHT);
        touch = new Vector3();
        halfGrayShader = new ShaderProgram(ShaderHandler.HALF_VERT_INVERT, ShaderHandler.HALF_FRAG_INVERT);
        fullGrayShader = new ShaderProgram(ShaderHandler.FULL_VERT_INVERT, ShaderHandler.FULL_FRAG_INVERT);
        wbg = AssetHandler.getWbg();
        bbg = AssetHandler.getBbg();
    }

    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);
}
