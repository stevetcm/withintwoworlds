package com.development.orangemuffin.twoworlds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.development.orangemuffin.twoworlds.MyGame;
import com.development.orangemuffin.twoworlds.handlers.AssetHandler;
import com.development.orangemuffin.twoworlds.ui.TextImage;
import com.development.orangemuffin.twoworlds.ui.buttons.RestartButton;

/* Created by OrangeMuffin on 10/7/2015 */
public class RestartState extends State {

    private TextImage textWarning;
    private TextImage textRestart1;
    private TextImage textRestart2;

    private RestartButton restartButton;

    private int musicStatus;
    private float sfxVolume;
    private int padsStatus;

    private final int PADS_ON = 1;

    protected RestartState(GSM gsm, int musicStatus, float sfxVolume, int padsStatus) {
        super(gsm);
        this.sfxVolume = sfxVolume;
        this.musicStatus = musicStatus;
        this.padsStatus = padsStatus;
        Gdx.input.setCatchBackKey(true);
        textWarning = new TextImage("WARNING!", MyGame.WIDTH/2, (11*MyGame.HEIGHT)/15, "fontsmall");
        textRestart1 = new TextImage("ALL SAVED PROGRESS WILL", MyGame.WIDTH/2, (9*MyGame.HEIGHT)/15, "fontsmall");
        textRestart2 = new TextImage("BE REMOVED", MyGame.WIDTH/2, (8*MyGame.HEIGHT)/15, "fontsmall");
        restartButton = new RestartButton(MyGame.WIDTH/2, (6*MyGame.HEIGHT)/15);
    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            touch.x = Gdx.input.getX();
            touch.y = Gdx.input.getY();
            camera.unproject(touch);
            if (restartButton.contains(touch.x, touch.y)) {
                AssetHandler.setPrefs("level", 1);
                AssetHandler.setPrefs("controls", 0);
                AssetHandler.setPrefs("endgame", 0);
                State next = new MenuState(gsm, musicStatus, sfxVolume, PADS_ON);
                gsm.set(new TransitionState(gsm, this, next, TransitionState.Type.BLACK_FADE));
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            State next = new MenuState(gsm, musicStatus, sfxVolume, padsStatus);
            gsm.set(new TransitionState(gsm, this, next, TransitionState.Type.BLACK_FADE));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.setShader(null);
        batch.begin();
        batch.draw(bbg, 0, 0, MyGame.WIDTH, MyGame.HEIGHT);
        batch.setShader(fullGrayShader);
        textWarning.render(batch);
        textRestart1.render(batch);
        textRestart2.render(batch);
        restartButton.render(batch);
        batch.end();
    }
}
