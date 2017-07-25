package com.development.orangemuffin.twoworlds.ui;

/* Created by OrangeMuffin on 7/9/2015 */
public class BoundedBox {
    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public boolean contains(float x, float y) {
        boolean condition = x > (this.x - width / 2)
                && x < (this.x + width / 2)
                && y > (this.y - height / 2)
                && y < (this.y + height / 2);
        return condition;
    }
}
