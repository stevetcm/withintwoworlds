package com.development.orangemuffin.twoworlds.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/* Created by OrangeMuffin on 2015-06-26 */
public abstract class Entity {

    protected Texture texture;
    protected float x,y;
    protected float hsp = 0, vsp = 0;
    protected boolean falling = true;
    protected boolean jumping = false;

    public Entity(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public abstract void update(float delta);

    public void render(SpriteBatch batch) {
        batch.draw(texture,x, y);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public boolean isJumping() { return jumping; }
    public boolean isFalling() { return falling; }
    public void setJumping(boolean jumping) { this.jumping = jumping; }
    public void setFalling(boolean falling) { this.falling = falling; }

    public float getHsp() { return hsp; }
    public float getVsp() { return vsp; }
    public void setHsp(float hsp) { this.hsp = hsp; }
    public void setVsp(float vsp) { this.vsp = vsp; }

}
