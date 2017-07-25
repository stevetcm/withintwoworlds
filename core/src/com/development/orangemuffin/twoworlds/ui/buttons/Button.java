package com.development.orangemuffin.twoworlds.ui.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.development.orangemuffin.twoworlds.ui.BoundedBox;

/* Created by OrangeMuffin on 8/20/2015 */
public abstract class Button extends BoundedBox{
    protected Texture texture;
    protected float alpha;

    public Button(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        alpha = 1f;
    }

    public void render(SpriteBatch batch) {
        batch.setColor(0, 0, 0, alpha);
        batch.draw(texture, x - (width/2), y - (height/2));
        batch.setColor(1, 1, 1, 1);
    }

    public void toggleAlpha() {
        if (alpha == 1f) {
            alpha = 0.55f;
        } else {
            alpha = 1f;
        }
    }
}
