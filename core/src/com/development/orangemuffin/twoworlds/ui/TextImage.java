package com.development.orangemuffin.twoworlds.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/* Created by OrangeMuffin on 2015-06-26 */
public class TextImage extends BoundedBox {
    private TextureRegion[][] bfontSheet;
    float widthOffset, widthSmall;
    String text, scale;
    int size;

    public TextImage(String text, float x, float y, String scale) {
        this.text = text;
        this.x = x;
        this.y = y;
        size = 50;
        width = (size) * text.length();
        widthOffset = width + (5 * text.length() - 1);
        widthSmall = ((25 * text.length()) + (3 * text.length() - 1));
        height = size;
        this.scale = scale;

        Texture blackfont = new Texture(Gdx.files.internal("sprites/bfontmap.png"));
        TextureRegion bsheet = new TextureRegion(blackfont);
        bfontSheet = bsheet.split(size, size);
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                c -= 'A';
            } else if (c >= '0' && c <= '9') {
                c -= '0';
                c += 27;
            } else if (c == ' ') {
                c -= ' ';
                c += 26;
            } else if (c == '!') {
                c -= '!';
                c += 39;
            }
            int index = (int) c;
            int row = index / bfontSheet[0].length;
            int col = index % bfontSheet[0].length;

            if (scale == "fontsmall") {
                batch.draw(bfontSheet[row][col], (x - (widthSmall / 2)) + 28 * i, y - 25 / 2, 25, 25);
            } else if (scale == "fontmedium") {
                batch.draw(bfontSheet[row][col], (x - (widthOffset / 2)) + 55 * i, y - height / 2);
            }
        }
    }
}
