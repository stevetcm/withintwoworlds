package com.development.orangemuffin.twoworlds.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/* Created by OrangeMuffin on 7/2/2015 */
public class AssetHandler {

    private static Texture player, block, cookie, fire, spike;
    private static Texture wbg, bbg, controls, pads;
    private static Texture music_img, sfx_img, exit_img, controls_img;
    private static Texture pads_img, restart_img, restartarrow_img;
    private static Sound death_sfx, cookie_sfx;
    private static Music music;
    private static Preferences prefs;

    public AssetHandler() {
        player = new Texture(Gdx.files.internal("sprites/player.png"));
        block = new Texture(Gdx.files.internal("sprites/block.png"));
        cookie = new Texture(Gdx.files.internal("sprites/cookie.png"));
        fire = new Texture(Gdx.files.internal("sprites/fire.png"));
        spike = new Texture(Gdx.files.internal("sprites/spike.png"));
        wbg = new Texture(Gdx.files.internal("sprites/white.png"));
        bbg = new Texture(Gdx.files.internal("sprites/black.png"));
        controls = new Texture(Gdx.files.internal("sprites/controls_bg.png"));
        pads = new Texture(Gdx.files.internal("sprites/pads_bg.png"));
        music_img = new Texture(Gdx.files.internal("sprites/music.png"));
        sfx_img = new Texture(Gdx.files.internal("sprites/sfx.png"));
        exit_img = new Texture(Gdx.files.internal("sprites/exit.png"));
        controls_img = new Texture(Gdx.files.internal("sprites/controls.png"));
        pads_img = new Texture(Gdx.files.internal("sprites/pads.png"));
        restart_img = new Texture(Gdx.files.internal("sprites/restart.png"));
        restartarrow_img = new Texture(Gdx.files.internal("sprites/restartarrow.png"));
        death_sfx = Gdx.audio.newSound(Gdx.files.internal("sounds/death_sfx.wav"));
        cookie_sfx = Gdx.audio.newSound(Gdx.files.internal("sounds/cookie_sfx.wav"));
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/bg_music.ogg"));
        prefs = Gdx.app.getPreferences("TwoWorlds");
    }

    public static Texture getPlayer() { return player; }
    public static Texture getBlock() { return block; }
    public static Texture getCookie() { return cookie; }
    public static Texture getFire() { return fire; }
    public static Texture getSpike() { return spike; }
    public static Texture getWbg() { return wbg; }
    public static Texture getBbg() { return bbg; }
    public static Texture getControls() { return controls; }
    public static Texture getPads() { return pads; }
    public static Texture getMusicImg() { return music_img; }
    public static Texture getSfxImg() { return sfx_img; }
    public static Texture getExitImg() { return exit_img; }
    public static Texture getControlsImg() { return controls_img; }
    public static Texture getPadsImg() { return pads_img; }
    public static Texture getRestartImg() { return restart_img; }
    public static Texture getRestartArrowImg() { return restartarrow_img; }
    public static Music getMusic() { return music; }
    public static Sound getDeathSFX() { return death_sfx; }
    public static Sound getCookieSFX() { return cookie_sfx; }

    public static void setupPrefs(String name, int value) {
        if (!prefs.contains(name)) {
            prefs.putInteger(name, value);
        }
    }

    public static void setPrefs(String name, int value) {
        prefs.putInteger(name, value);
        prefs.flush();
    }

    public static int getPrefs(String name) {
        return prefs.getInteger(name);
    }
}
