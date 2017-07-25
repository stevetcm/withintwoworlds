package com.development.orangemuffin.twoworlds.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.development.orangemuffin.twoworlds.MyGame;
import com.development.orangemuffin.twoworlds.handlers.AssetHandler;
import com.development.orangemuffin.twoworlds.ui.TextImage;
import com.development.orangemuffin.twoworlds.ui.buttons.ControlsButton;
import com.development.orangemuffin.twoworlds.ui.buttons.MusicButton;
import com.development.orangemuffin.twoworlds.ui.buttons.PadsButton;
import com.development.orangemuffin.twoworlds.ui.buttons.RestartArrowButton;
import com.development.orangemuffin.twoworlds.ui.buttons.SfxButton;

/* Created by OrangeMuffin on 2015-06-26 */
public class MenuState extends State {

    private TextImage textTitle;
    private TextImage textTitle1;
    private TextImage textPlay1;
    private TextImage textPlay2;
    private TextImage textPlay3;
    private TextImage textRun;

    private RestartArrowButton restartArrowButton;
    private MusicButton musicButton;
    private SfxButton sfxButton;
    private ControlsButton controlsButton;
    private PadsButton padsButton;

    private final int MUSIC_PAUSED = 0;
    private final int MUSIC_PLAYING = 1;
    private int musicStatus;

    private float sfxVolume;

    private final int GAME_MENU = 0;
    private final int GAME_CONTROLS = 1;
    private int menuStatus = GAME_MENU;

    private int backKey = 1;

    private final int PADS_OFF = 0;
    private final int PADS_ON = 1;
    private int padsStatus = PADS_ON;

    public MenuState(GSM gsm, int musicStatus, float sfxVolume, int padsStatus) {
        super(gsm);
        Gdx.input.setCatchBackKey(true);
        textTitle1 = new TextImage("WITHIN", MyGame.WIDTH/2, 4*MyGame.HEIGHT/5, "fontmedium");
        textTitle = new TextImage(MyGame.TITLE, MyGame.WIDTH/2, (2*MyGame.HEIGHT)/3, "fontmedium");
        textPlay1 = new TextImage("TOUCH TO START", MyGame.WIDTH/2, MyGame.HEIGHT/3, "fontsmall");
        textPlay3 = new TextImage("TOUCH TO PLAY", MyGame.WIDTH/2, MyGame.HEIGHT/3, "fontsmall");
        textPlay2 = new TextImage("TOUCH TO CONTINUE", MyGame.WIDTH/2, MyGame.HEIGHT/3, "fontsmall");

        restartArrowButton = new RestartArrowButton(50, MyGame.HEIGHT-50);

        musicButton = new MusicButton(50, 50);
        this.musicStatus = musicStatus;
        if (musicStatus == MUSIC_PAUSED) { musicButton.toggleAlpha(); }

        sfxButton = new SfxButton(115, 50);
        this.sfxVolume = sfxVolume;
        if (sfxVolume == 0f) { sfxButton.toggleAlpha(); }

        AssetHandler.setupPrefs("endgame", 0);
        AssetHandler.setupPrefs("controls", 0);
        AssetHandler.setupPrefs("level", 1);
        AssetHandler.setupPrefs("pads", 1);
        AssetHandler.setupPrefs("first", 0);
        AssetHandler.setupPrefs("padsSide", 0);

        if (AssetHandler.getPrefs("level") > 30) {
            AssetHandler.setPrefs("level", 1);
        }

        textRun = new TextImage(AssetHandler.getPrefs("level") + " OUT OF 30", MyGame.WIDTH/2, MyGame.HEIGHT/3-35, "fontsmall");

        controlsButton = new ControlsButton(MyGame.WIDTH-57 ,50);

        padsButton = new PadsButton(MyGame.WIDTH-135, 50);
        this.padsStatus = padsStatus;
        this.padsStatus = AssetHandler.getPrefs("pads");

        if(AssetHandler.getPrefs("first") == 0) {
            AssetHandler.setPrefs("first", 1);
            this.padsStatus = PADS_ON;
        }
        if (this.padsStatus == PADS_OFF) { padsButton.toggleAlpha(); }
    }

    public void handleInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.BACK) || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            AssetHandler.setPrefs("pads", padsStatus);
            Gdx.app.exit();
        }
        if (Gdx.input.justTouched()) {
            touch.x = Gdx.input.getX();
            touch.y = Gdx.input.getY();
            camera.unproject(touch);
            if (musicButton.contains(touch.x, touch.y)) {
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
                if (sfxVolume == MyGame.SFX_VOLUME) {
                    sfxVolume = 0f;
                } else if (sfxVolume == 0f) {
                    sfxVolume = MyGame.SFX_VOLUME;
                }
            } else if (padsButton.contains(touch.x, touch.y)) {
                padsButton.toggleAlpha();
                if (padsStatus == PADS_ON) {
                    padsStatus = PADS_OFF;
                } else if (padsStatus == PADS_OFF) {
                    padsStatus = PADS_ON;
                }
            } else if (restartArrowButton.contains(touch.x, touch.y)) {
                State next = new RestartState(gsm, musicStatus, sfxVolume, padsStatus);
                gsm.set(new TransitionState(gsm, this, next, TransitionState.Type.BLACK_FADE));
            } else if (controlsButton.contains(touch.x, touch.y)) {
                State next = new ControlState(gsm, musicStatus, sfxVolume, padsStatus, false);
                gsm.set(new TransitionState(gsm, this, next, TransitionState.Type.BLACK_FADE));
            } else if (AssetHandler.getPrefs("controls") == 0) {
                State next = new ControlState(gsm, musicStatus, sfxVolume, padsStatus, true);
                gsm.set(new TransitionState(gsm, this, next, TransitionState.Type.BLACK_FADE));
            } else if (AssetHandler.getPrefs("controls") == 1) {
                State next = new PlayState(gsm, AssetHandler.getPrefs("level"), musicStatus, sfxVolume, padsStatus);
                gsm.set(new TransitionState(gsm, this, next, TransitionState.Type.BLACK_FADE));
            }
        }


    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.setShader(halfGrayShader);
        batch.begin();
        batch.draw(wbg, 0, 0, MyGame.WIDTH, MyGame.HEIGHT);
        restartArrowButton.render(batch);
        textTitle1.render(batch);
        textTitle.render(batch);
        textRun.render(batch);
        if (AssetHandler.getPrefs("level") <= 1 && AssetHandler.getPrefs("endgame") == 0) {
            textPlay1.render(batch);
        } else if (AssetHandler.getPrefs("level") <= 1 && AssetHandler.getPrefs("endgame") == 1) {
            textPlay3.render(batch);
        } else {
            textPlay2.render(batch);
        }
        musicButton.render(batch);
        sfxButton.render(batch);
        controlsButton.render(batch);
        padsButton.render(batch);
        batch.end();
    }
}
