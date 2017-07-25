package com.development.orangemuffin.twoworlds;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.development.orangemuffin.twoworlds.handlers.AssetHandler;
import com.development.orangemuffin.twoworlds.states.GSM;
import com.development.orangemuffin.twoworlds.states.MenuState;

public class MyGame extends ApplicationAdapter {

    public static final String TITLE = "TWO WORLDS";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;

    public static final int MUSIC_PLAYING = 1;
    public static final float SFX_VOLUME = 0.10f;
    public static final int PADS_ON = 1;

    private SpriteBatch batch;

    private GSM gsm;

    public AssetHandler assetHandler;

    public static Music music;

	@Override
	public void create () {
		batch = new SpriteBatch();
        assetHandler = new AssetHandler();

        music = AssetHandler.getMusic();
        if (!music.isPlaying()) {
            music.setLooping(true);
            music.setVolume(1);
            music.play();
        }

        gsm = new GSM();
        gsm.push(new MenuState(gsm, MUSIC_PLAYING, SFX_VOLUME, PADS_ON));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
	}

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }

}
