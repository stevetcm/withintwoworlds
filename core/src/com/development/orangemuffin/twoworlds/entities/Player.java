package com.development.orangemuffin.twoworlds.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.development.orangemuffin.twoworlds.MyGame;
import com.development.orangemuffin.twoworlds.handlers.AssetHandler;

/* Created by OrangeMuffin on 6/27/2015 */
public class Player extends Entity {

    private float gravity = -0.32f;
    private float MAX_SPEED = -5.5f;
    private boolean warping;
    private float warpSpeed = 20f;
    private float warpDest;
    private boolean parallel = false;
    private float xStart, yStart;

    private boolean collision = false;

    public Player(float x, float y) {
        super(AssetHandler.getPlayer(), x, y);
        xStart = x; yStart = y;
    }

    @Override
    public void update(float delta) {
        if (warping) {
            if (y >= warpDest) {
                y -= warpSpeed;
                if (y < warpDest) {
                    y = warpDest;
                    warping = false;
                }
            } else if (y <= warpDest) {
                y += warpSpeed;
                if (y > warpDest) {
                    y = warpDest;
                    warping = false;
                }
            }
        } else if (!warping && !collision) {
            x += hsp;
            y += vsp;

            if (falling || jumping) {
                vsp += gravity;
                if (parallel == false) {
                    if (vsp < MAX_SPEED) {
                        vsp = MAX_SPEED;
                    }
                } else if (parallel == true) {
                    if (vsp > -MAX_SPEED) {
                        vsp = -MAX_SPEED;
                    }
                }
            }
            basicCollision();
        }
    }

    public boolean warp() {
        swapStats();

        if (warping) return false;

        if (y >= MyGame.HEIGHT/2) {
            warpDest = y - ((2*(y - MyGame.HEIGHT/2))+20);
        } else if (y <= MyGame.HEIGHT/2) {
            warpDest = y + ((2*(MyGame.HEIGHT/2-y))-20);
        }

        warping = true;
        return true;
    }

    public void swapStats() {
        parallel = !parallel;
        gravity = -gravity;
        vsp = 0;
    }

    public void basicCollision() {
        if (!parallel) {
            if (y <= MyGame.HEIGHT / 2) {
                y = MyGame.HEIGHT / 2;
                jumping = false;
            }
        } else if (parallel) {
            if (y+20 >= MyGame.HEIGHT/2) {
                y = (MyGame.HEIGHT / 2) - 20;
                jumping = false;
            }
        }

        if ((x <= 0) && hsp < 0) { x = 0; }
        if ((x+texture.getWidth() >= MyGame.WIDTH) && hsp > 0) {
            x = MyGame.WIDTH - texture.getWidth();
        }
    }

    public void restart() {
        if (y < MyGame.HEIGHT/2) {
            swapStats();
        }
        x = xStart; y = yStart;
        hsp = 0; vsp = 0;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    public Rectangle getBottomBounds() {return new Rectangle(x+5, y, 10, 10);}
    public Rectangle getTopBounds() { return new Rectangle(x+5, y+10, 10, 10); }
    public Rectangle getLeftBounds() { return new Rectangle(x, y+3, 10, 14); }
    public Rectangle getRightBounds() { return new Rectangle(x+10, y+3, 10, 14); }

    public boolean isWarping() { return warping; }
    public boolean isParallel() { return  parallel; }
}
