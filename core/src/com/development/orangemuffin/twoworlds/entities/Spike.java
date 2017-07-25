package com.development.orangemuffin.twoworlds.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.development.orangemuffin.twoworlds.MyGame;
import com.development.orangemuffin.twoworlds.handlers.AssetHandler;

/* Created by OrangeMuffin on 2015-06-29 */
public class Spike extends Entity {

    public Spike(float x, float y) {
        super(AssetHandler.getSpike(), x, y);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
        if (y >= MyGame.HEIGHT/2) {
            super.render(batch);
        } else if (y < MyGame.HEIGHT/2) {
            batch.draw(texture, x, y, texture.getWidth(), texture.getHeight(),
                    0, 0, texture.getWidth(), texture.getHeight(), false, true);
        }

    }
}
