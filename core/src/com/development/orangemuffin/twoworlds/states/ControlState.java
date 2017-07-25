package com.development.orangemuffin.twoworlds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.development.orangemuffin.twoworlds.handlers.AssetHandler;

/* Created by OrangeMuffin on 8/20/2015 */
public class ControlState extends State {

    private boolean fromState;
    private int musicStatus;
    private float sfxVolume;
    private int padsStatus;

    private Texture controls = AssetHandler.getControls();

    protected ControlState(GSM gsm, int musicStatus, float sfxVolume, int padsStatus, boolean fromState) {
        super(gsm);
        this.fromState = fromState;
        this.sfxVolume = sfxVolume;
        this.musicStatus = musicStatus;
        this.padsStatus = padsStatus;
        Gdx.input.setCatchBackKey(true);
        AssetHandler.setPrefs("controls", 1);
    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    public void handleInput() {
        if (fromState) {
            if (Gdx.input.isTouched()) {
                State next = new PlayState(gsm, 1, musicStatus, sfxVolume, padsStatus);
                gsm.set(new TransitionState(gsm, this, next, TransitionState.Type.BLACK_FADE));
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            State next = new MenuState(gsm, musicStatus, sfxVolume, padsStatus);
            gsm.set(new TransitionState(gsm, this, next, TransitionState.Type.BLACK_FADE));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.setShader(null);
        batch.begin();
        batch.draw(controls, 0, 0);
        batch.end();
    }

}
