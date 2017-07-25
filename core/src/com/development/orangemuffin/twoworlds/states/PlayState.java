package com.development.orangemuffin.twoworlds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.development.orangemuffin.twoworlds.MyGame;
import com.development.orangemuffin.twoworlds.entities.EntityManager;
import com.development.orangemuffin.twoworlds.handlers.AssetHandler;
import com.development.orangemuffin.twoworlds.handlers.LevelHandler;
import com.development.orangemuffin.twoworlds.ui.TextImage;
import com.development.orangemuffin.twoworlds.ui.buttons.ControlsButton;
import com.development.orangemuffin.twoworlds.ui.buttons.ExitButton;
import com.development.orangemuffin.twoworlds.ui.buttons.MusicButton;
import com.development.orangemuffin.twoworlds.ui.buttons.PadsButton;
import com.development.orangemuffin.twoworlds.ui.buttons.SfxButton;

/* Created by OrangeMuffin on 2015-06-26 */
public class PlayState extends State {
    private EntityManager entityManager;

    private LevelHandler levelHandler;
    private int level;

    private TextImage textLevel;
    private TextImage textReset;
    private TextImage textPaused;
    private TextImage textWarning1;
    private TextImage textWarning2;

    private ExitButton exitButton;

    private final int GAME_PAUSED = 0;
    private final int GAME_RUNNING = 1;
    private final int GAME_CONTROLS = 2;
    private int gameStatus = GAME_RUNNING;

    private final int MUSIC_PAUSED = 0;
    private final int MUSIC_PLAYING = 1;
    private MusicButton musicButton;
    private int musicStatus;
    private SfxButton sfxButton;
    private float sfxVolume;
    private ControlsButton controlsButton;
    private PadsButton padsButton;
    private final int PADS_OFF = 0;
    private final int PADS_ON = 1;
    private int padsStatus;

    public PlayState(GSM gsm, int level, int musicStatus, float sfxVolume, int padsStatus) {
        super(gsm);
        AssetHandler.setPrefs("level", level);
        this.level = level;
        levelHandler = new LevelHandler(level);
        entityManager = new EntityManager(touch, camera, levelHandler, sfxVolume);

        textLevel = new TextImage(Integer.toString(level), 35, MyGame.HEIGHT - 30, "fontsmall");
        textReset = new TextImage("RESET", MyGame.WIDTH - 85, MyGame.HEIGHT - 30, "fontsmall");
        textPaused = new TextImage("GAME PAUSED!", MyGame.WIDTH / 2, 11 * MyGame.HEIGHT / 15, "fontsmall");
        textWarning1 = new TextImage("GAME PROGRESS IS ALREADY", MyGame.WIDTH / 2, 9 * MyGame.HEIGHT / 15, "fontsmall");
        textWarning2 = new TextImage("SAVED", MyGame.WIDTH / 2, 8 * MyGame.HEIGHT / 15, "fontsmall");

        exitButton = new ExitButton(MyGame.WIDTH / 2, 6 * MyGame.HEIGHT / 15);

        Gdx.input.setCatchBackKey(true);

        musicButton = new MusicButton(50, 50);
        this.musicStatus = musicStatus;
        if (musicStatus == MUSIC_PAUSED) { musicButton.toggleAlpha(); }

        sfxButton = new SfxButton(115, 50);
        this.sfxVolume = sfxVolume;
        if (sfxVolume == 0f) { sfxButton.toggleAlpha(); }

        controlsButton = new ControlsButton(MyGame.WIDTH-57 ,50);

        padsButton = new PadsButton(MyGame.WIDTH-135, 50);
        this.padsStatus = padsStatus;
        if (padsStatus == PADS_OFF) { padsButton.toggleAlpha(); }
    }

    @Override
    public void update(float delta) {
        handleInput();

        if (gameStatus == GAME_RUNNING) { entityManager.update(delta); }

        if (entityManager.advance && level == 30) {
            State next = new EndState(gsm, musicStatus, sfxVolume, padsStatus);
            gsm.set(new TransitionState(gsm, this, next, TransitionState.Type.BLACK_FADE));
        } else if (entityManager.advance && level == 5) {
            State next = new PlayState(gsm, level + 1, musicStatus, sfxVolume, PADS_OFF);
            gsm.set(new TransitionState(gsm, this, next, TransitionState.Type.BLACK_FADE));
        } else if (entityManager.advance) {
            State next = new PlayState(gsm, level + 1, musicStatus, sfxVolume, padsStatus);
            gsm.set(new TransitionState(gsm, this, next, TransitionState.Type.BLACK_FADE));
        }
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            touch.x = Gdx.input.getX();
            touch.y = Gdx.input.getY();
            camera.unproject(touch);

            if (gameStatus == GAME_PAUSED) {
                if (exitButton.contains(touch.x, touch.y)) {
                    AssetHandler.setPrefs("pads", padsStatus);
                    State next = new MenuState(gsm, musicStatus, sfxVolume, padsStatus);
                    gsm.set(new TransitionState(gsm, this, next, TransitionState.Type.BLACK_FADE));
                } else if (musicButton.contains(touch.x, touch.y)) {
                    musicButton.toggleAlpha();
                    if (MyGame.music.isPlaying()) {
                        MyGame.music.pause();
                        musicStatus = MUSIC_PAUSED;
                    } else {
                        MyGame.music.play();
                        musicStatus = MUSIC_PLAYING;
                    }
                } else if (sfxButton.contains(touch.x, touch.y)) {
                    sfxButton.toggleAlpha();
                    if (sfxVolume == MyGame.SFX_VOLUME) { sfxVolume = 0f; }
                    else if (sfxVolume == 0f) { sfxVolume = MyGame.SFX_VOLUME; }
                    entityManager.sfxVolume = sfxVolume;
                } else if (padsButton.contains(touch.x, touch.y)) {
                    padsButton.toggleAlpha();
                    if (padsStatus == PADS_ON) { padsStatus = PADS_OFF; }
                    else if (padsStatus == PADS_OFF) { padsStatus = PADS_ON; }
                } else if (controlsButton.contains(touch.x, touch.y)) {
                    gameStatus = GAME_CONTROLS;
                }
            } else if (textLevel.contains(touch.x, touch.y)) {
                State next = new SelectState(gsm, musicStatus, sfxVolume, padsStatus);
                gsm.set(new TransitionState(gsm, this, next, TransitionState.Type.BLACK_FADE));
            }//level testing section
        }

        if (Gdx.input.isTouched()) {
            touch.x = Gdx.input.getX();
            touch.y = Gdx.input.getY();
            camera.unproject(touch);
            if (textReset.contains(touch.x, touch.y)) {
                entityManager.player.restart();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (gameStatus == GAME_RUNNING) {
                gameStatus = GAME_PAUSED;
            } else if (gameStatus == GAME_PAUSED) {
                gameStatus = GAME_RUNNING;
            } else if (gameStatus == GAME_CONTROLS) {
                gameStatus = GAME_PAUSED;
            }
        }

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.setShader(halfGrayShader);
        batch.begin();
        batch.draw(wbg, 0, 0, MyGame.WIDTH, MyGame.HEIGHT);
        if (padsStatus == PADS_ON) {
            padsRender(batch);
        }
        textLevel.render(batch);
        textReset.render(batch);
        entityManager.render(batch);
        if (gameStatus == GAME_PAUSED) {
            pausedRender(batch);
        } else if (gameStatus == GAME_CONTROLS) {
            controlsRender(batch);
        }
        batch.end();
    }

    public void pausedRender(SpriteBatch batch) {
        batch.setShader(fullGrayShader);
        batch.draw(wbg, 0, 0, MyGame.WIDTH, MyGame.HEIGHT);
        textPaused.render(batch);
        textWarning1.render(batch);
        textWarning2.render(batch);
        exitButton.render(batch);
        musicButton.render(batch);
        sfxButton.render(batch);
        controlsButton.render(batch);
        padsButton.render(batch);
    }

    public void controlsRender(SpriteBatch batch) {
        batch.setShader(null);
        batch.draw(AssetHandler.getControls(), 0, 0);
    }

    public void padsRender(SpriteBatch batch) {
        batch.setShader(null);
        batch.draw(AssetHandler.getPads(), 0, 0);
        batch.setShader(halfGrayShader);
    }

}
