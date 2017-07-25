package com.development.orangemuffin.twoworlds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.development.orangemuffin.twoworlds.MyGame;
import com.development.orangemuffin.twoworlds.handlers.AssetHandler;
import com.development.orangemuffin.twoworlds.ui.TextImage;

/* Created by OrangeMuffin on 10/3/2015 */
public class EndState extends State {

    private TextImage textThanks1;
    private TextImage textThanks2;

    private int musicStatus;
    private float sfxVolume;
    private int padsStatus;

    protected EndState(GSM gsm, int musicStatus, float sfxVolume, int padsStatus) {
        super(gsm);
        Gdx.input.setCatchBackKey(true);
        textThanks1 = new TextImage("THANK YOU FOR PLAYING!", MyGame.WIDTH/2, (2*MyGame.HEIGHT)/3, "fontsmall");
        textThanks2 = new TextImage("THANK YOU FOR PLAYING!", MyGame.WIDTH/2, MyGame.HEIGHT/3, "fontsmall");

        AssetHandler.setPrefs("endgame", 1);
        AssetHandler.setPrefs("level", 1);
        this.musicStatus = musicStatus;
        this.sfxVolume = sfxVolume;
        this.padsStatus = padsStatus;

    }

    public void handleInput() {
        if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            State next = new MenuState(gsm, musicStatus, sfxVolume, padsStatus);
            gsm.set(new TransitionState(gsm, this, next, TransitionState.Type.BLACK_FADE));
        }
    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.setShader(halfGrayShader);
        batch.begin();
        batch.draw(wbg, 0, 0, MyGame.WIDTH, MyGame.HEIGHT);
        textThanks1.render(batch);
        textThanks2.render(batch);
        batch.end();
    }
}
