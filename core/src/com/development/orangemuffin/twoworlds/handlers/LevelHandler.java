package com.development.orangemuffin.twoworlds.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/* Created by OrangeMuffin on 2015-06-29 */
public class LevelHandler {

    private Array<Vector2> block = new Array<Vector2>();
    private Array<Vector2> spike = new Array<Vector2>();
    private Array<Vector2> cookie = new Array<Vector2>();
    private Array<Vector2> player = new Array<Vector2>();
    private Array<Vector2> fire = new Array<Vector2>();
    private Array<Integer> speed = new Array<Integer>();
    private Array<Integer> vspeed = new Array<Integer>();
    private Array<Integer> special = new Array<Integer>();

    public LevelHandler(int level) {
        FileHandle file = Gdx.files.internal("levels/level"+level+".txt");
        String text = file.readString();
        String[] tile = text.split("\n");

        for (int row = 0; row < 24; row++) {
            for (int col = 0; col < 40; col++) {
                int x = col * 20;
                int y = 460 - (row * 20);
                if (tile[row].charAt(col) == '#') {
                    block.add(new Vector2(x, y));
                } else if (tile[row].charAt(col) == '^') {
                    spike.add(new Vector2(x, y));
                } else if (tile[row].charAt(col) == '$') {
                    cookie.add(new Vector2(x, y));
                } else if (tile[row].charAt(col) == 'x') {
                    player.add(new Vector2(x, y));
                } else if (tile[row].charAt(col) == '3') {
                    fire.add(new Vector2(x, y));
                    setArrays(0, Character.getNumericValue(tile[row].charAt(col)), 0);
                } else if (tile[row].charAt(col) == '~') {
                    fire.add(new Vector2(x, y));
                    setArrays(0, 7, 0);
                } else if (tile[row].charAt(col) == '"') {
                    fire.add(new Vector2(x, y));
                    setArrays(0, -7, 0);
                } else if (tile[row].charAt(col) == '&') {
                    fire.add(new Vector2(x, y));
                    setArrays(0, 5, 1);
                }  else if (tile[row].charAt(col) == '*') {
                    fire.add(new Vector2(x, y));
                    setArrays(0, -5, 1);
                } else if (tile[row].charAt(col) == ':') {
                    fire.add(new Vector2(x,y));
                    setArrays(-2, 0, 2);
                } else if (tile[row].charAt(col) >= '0'
                        && tile[row].charAt(col) <= '9') {
                    fire.add(new Vector2(x,y));
                    setArrays(Character.getNumericValue(tile[row].charAt(col)), 0, 0);
                }
            }
        }
    }

    private void setArrays(int speed, int vspeed, int special) {
        this.speed.add(speed);
        this.vspeed.add(vspeed);
        this.special.add(special);
    }

    public Array<Vector2> getPlayers() {
        return player;
    }
    public Array<Vector2> getCookie() { return cookie; }
    public Array<Vector2> getFires() { return fire; }
    public Array<Integer> getSpeed() { return speed; }
    public Array<Integer> getVSpeed() { return vspeed; }
    public Array<Integer> getSpecial() { return special; }
    public Array<Vector2> getBlocks() { return block; }
    public Array<Vector2> getSpikes() { return spike; }

}
