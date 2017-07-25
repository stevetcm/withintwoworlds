package com.development.orangemuffin.twoworlds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.development.orangemuffin.twoworlds.MyGame;

/* Created by OrangeMuffin on 2015-06-26 */
public class TransitionState extends State {
    public enum Type { BLACK_FADE }

    private State prev;
    private State next;
    private Type type;

    // black fade
    private Texture bbox;
    private float maxTime;
    private float timer;

    public TransitionState(GSM gsm, State prev, State next, Type type) {
        super(gsm);
        this.prev = prev;
        this.next = next;
        this.type = type;

        if (type == Type.BLACK_FADE) {
            bbox = new Texture(Gdx.files.internal("sprites/black.png"));
            maxTime = 1;
        }
    }

    @Override
    public void update(float delta) {
        timer += delta;
        if (type == Type.BLACK_FADE) {
            if (timer >= maxTime) {
                gsm.set(next);
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (type == Type.BLACK_FADE) {
            float alpha;
            if (timer < maxTime / 2) {
                alpha = timer / (maxTime / 2);
                prev.render(batch);
            } else {
                alpha = 1 - (timer / (maxTime / 2));
                next.render(batch);
            }
            batch.setColor(0, 0, 0, alpha);
            batch.setProjectionMatrix(camera.combined);
            batch.setShader(halfGrayShader);
            batch.begin();
            batch.draw(bbox, 0, 0, MyGame.WIDTH, MyGame.HEIGHT);
            batch.end();
            batch.setColor(1, 1, 1, 1);
        }
    }
}
