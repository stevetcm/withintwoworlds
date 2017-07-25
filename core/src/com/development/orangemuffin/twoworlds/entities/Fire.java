package com.development.orangemuffin.twoworlds.entities;

import com.development.orangemuffin.twoworlds.MyGame;
import com.development.orangemuffin.twoworlds.handlers.AssetHandler;

/* Created by OrangeMuffin on 6/30/2015 */
public class Fire extends Entity {

    private int special;

    public Fire(float x, float y, float hsp, float vsp, int special) {
        super(AssetHandler.getFire(), x, y);
        this.hsp = hsp;
        this.vsp = vsp;
        this.special = special;
    }

    @Override
    public void update(float delta) {
        x += hsp;
        y += vsp;
        basicCollision();
    }

    public void basicCollision() {
        if (special != 2) {
            if (x <= 0) {
                hsp = -hsp;
            } else if (x + 20 >= MyGame.WIDTH) {
                hsp = -hsp;
            }

            if (y >= MyGame.HEIGHT && vsp > 0) {
                y = -texture.getHeight();
            }
            if (y + texture.getHeight() <= 0 && vsp < 0) {
                y = MyGame.HEIGHT + texture.getHeight();
            }
        } else if (special == 2) {
            if (x+20 <= 0) {
                x = MyGame.WIDTH;
            }
        }
    }

    public int getSpecial() {
        return special;
    }

}
