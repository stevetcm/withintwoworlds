package com.development.orangemuffin.twoworlds.entities;

import com.development.orangemuffin.twoworlds.handlers.AssetHandler;

/* Created by OrangeMuffin on 6/28/2015 */
public class Block extends Entity {

    public Block(float x, float y) {
        super(AssetHandler.getBlock(), x, y);
    }

    @Override
    public void update(float delta) {

    }

}
