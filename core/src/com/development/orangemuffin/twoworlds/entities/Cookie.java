package com.development.orangemuffin.twoworlds.entities;

import com.development.orangemuffin.twoworlds.handlers.AssetHandler;

/* Created by OrangeMuffin on 2015-06-29 */
public class Cookie extends Entity {

    public Cookie(float x, float y) {
        super(AssetHandler.getCookie(), x, y);
    }

    @Override
    public void update(float delta) {

    }

}
