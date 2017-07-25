package com.development.orangemuffin.twoworlds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.development.orangemuffin.twoworlds.MyGame;
import com.development.orangemuffin.twoworlds.ui.TextImage;

/* Created by OrangeMuffin on 7/9/2015 */
/*    USED FOR TESTING PURPOSES ONLY   */
public class SelectState extends State {

    private Array<TextImage> levels;
    private int musicStatus;
    private float sfxVolume;
    private int padsStatus;
    private int offset;

    public SelectState(GSM gsm, int musicStatus, float sfxVolume, int padsStatus) {
        super(gsm);
        this.musicStatus = musicStatus;
        this.sfxVolume = sfxVolume;
        this.padsStatus = padsStatus;
        levels = new Array<TextImage>();
        offset = 30;
        for(int index = 1; index < 9; index++) {
            levels.add(new TextImage(Integer.toString(index+offset),
                    index*(MyGame.WIDTH/9), (2*MyGame.HEIGHT)/3, "fontsmall"));
        }
        for(int index = 9; index < 17; index++) {
            levels.add(new TextImage(Integer.toString(index+offset),
                    (index-8)*(MyGame.WIDTH/9), MyGame.HEIGHT/3, "fontsmall"));
        }
    }

    public void handleInput() {
        if(Gdx.input.justTouched()) {
            touch.x = Gdx.input.getX();
            touch.y = Gdx.input.getY();
            camera.unproject(touch);
            for (int index = 0; index < levels.size; index++) {
                if (levels.get(index).contains(touch.x, touch.y)) {
                    State next = new PlayState(gsm, index+offset+1, musicStatus, sfxVolume, padsStatus);
                    gsm.set(new TransitionState(gsm, this, next, TransitionState.Type.BLACK_FADE));
                }
            }
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
        for (int index = 0; index < levels.size; index++) {
            levels.get(index).render(batch);
        }
        batch.end();
    }
}
