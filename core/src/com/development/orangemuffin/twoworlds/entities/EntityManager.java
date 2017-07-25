package com.development.orangemuffin.twoworlds.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.development.orangemuffin.twoworlds.handlers.AssetHandler;
import com.development.orangemuffin.twoworlds.handlers.InputHandler;
import com.development.orangemuffin.twoworlds.handlers.LevelHandler;

/* Created by OrangeMuffin on 6/27/2015 */
public class EntityManager {

    public Player player;

    public InputHandler inputHandler;
    public boolean leftButton = false;
    public boolean rightButton = false;
    public boolean jumpButton = false;
    public boolean warpButton = false;

    private LevelHandler levelHandler;
    public boolean advance = false;
    private Array<Entity> entities = new Array<Entity>();

    public float sfxVolume;

    public EntityManager(Vector3 touch, OrthographicCamera camera, LevelHandler levelHandler, float sfxVolume) {
        inputHandler = new InputHandler(this, touch, camera);
        Gdx.input.setInputProcessor(inputHandler);

        this.sfxVolume = sfxVolume;

        this.levelHandler = levelHandler;
        initFires();
        initBlocks();
        initSpikes();
        initCookie();
        initPlayer();
    }

    public void update(float delta) {
        checkCollision();
        handleInput();
        for (Entity entity : entities) {
            entity.update(delta);
        }
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void render(SpriteBatch batch) {
        for (Entity entity : entities) {
            entity.render(batch);
        }
    }

    private void checkCollision() {
        for (Block block : getBlocks()) {
            for (Fire fire : getFires()) {
                if (fire.getBounds().overlaps(block.getBounds())) {
                    if (fire.getSpecial() == 0) {
                        fire.setHsp(-fire.getHsp());
                        fire.setVsp(-fire.getVsp());
                    } else if (fire.getSpecial() == 1) {
                        if (fire.getHsp() == 0) {
                            if (fire.getVsp() > 0) {
                                fire.setY(block.getY() - block.texture.getHeight());
                            } else if (fire.getVsp() < 0) {
                                fire.setY(block.getY() + block.texture.getHeight());
                            }
                            fire.setHsp(-fire.getVsp());
                            fire.setVsp(0);
                        } else if (fire.getVsp() == 0) {
                            if (fire.getHsp() < 0) {
                                fire.setX(block.getX() + block.texture.getWidth());
                            } else if (fire.getHsp() > 0) {
                                fire.setX(block.getX() - block.texture.getWidth());
                            }
                            fire.setVsp(fire.getHsp());
                            fire.setHsp(0);
                        }
                    }
                }
            }
        }

        if (!player.isWarping()) {
            for (Block block : getBlocks()) {
                if (!player.isParallel()) {
                    if (player.getTopBounds().overlaps(block.getBounds())) {
                        player.setVsp(0);
                        player.setY(block.getY() - block.texture.getHeight());
                    }
                    if (player.getBottomBounds().overlaps(block.getBounds())) {
                        player.setVsp(0);
                        player.setFalling(false);
                        player.setJumping(false);
                        player.setY(block.getY() + block.texture.getHeight());
                    } else {
                        player.setFalling(true);
                    }

                    if (player.getRightBounds().overlaps(block.getBounds())) {
                        player.setX(block.getX() - block.texture.getWidth());
                    }
                    if (player.getLeftBounds().overlaps(block.getBounds())) {
                        player.setX(block.getX() + block.texture.getWidth());
                    }
                } else if (player.isParallel()) {
                    if (player.getBottomBounds().overlaps(block.getBounds())) {
                        player.setVsp(0);
                        player.setY(block.getY() + block.texture.getHeight());
                    }
                    if (player.getTopBounds().overlaps(block.getBounds())) {
                        player.setVsp(0);
                        player.setFalling(false);
                        player.setJumping(false);
                        player.setY(block.getY() - block.texture.getHeight());
                    } else {
                        player.setFalling(true);
                    }
                    if (player.getRightBounds().overlaps(block.getBounds())) {
                        player.setX(block.getX() - block.texture.getWidth());
                    }
                    if (player.getLeftBounds().overlaps(block.getBounds())) {
                        player.setX(block.getX() + block.texture.getWidth());
                    }
                }
            }

            for (Spike spike : getSpikes()) {
                if (player.getBounds().overlaps(spike.getBounds())) {
                    AssetHandler.getDeathSFX().play(sfxVolume);
                    player.restart();
                }
            }

            for (Cookie cookie : getCookie()) {
                if (player.getBounds().overlaps(cookie.getBounds())) {
                    AssetHandler.getCookieSFX().play(sfxVolume);
                    advance = true;
                }
            }

            for (Fire fire : getFires()) {
                if (player.getBounds().overlaps(fire.getBounds())) {
                    AssetHandler.getDeathSFX().play(sfxVolume);
                    player.restart();
                }
            }
        }
    }

    private Array<Block> getBlocks() {
        Array<Block> temp = new Array<Block>();
        for (Entity entity : entities) {
            if (entity instanceof Block) {
                temp.add((Block) entity);
            }
        }
        return temp;
    }

    private Array<Spike> getSpikes() {
        Array<Spike> temp = new Array<Spike>();
        for (Entity entity : entities) {
            if (entity instanceof Spike) {
                temp.add((Spike) entity);
            }
        }
        return temp;
    }

    private Array<Cookie> getCookie() {
        Array<Cookie> temp = new Array<Cookie>();
        for (Entity entity : entities) {
            if (entity instanceof Cookie) {
                temp.add((Cookie) entity);
            }
        }
        return temp;
    }

    private Array<Fire> getFires() {
        Array<Fire> temp = new Array<Fire>();
        for (Entity entity : entities) {
            if (entity instanceof Fire) {
                temp.add((Fire) entity);
            }
        }
        return temp;
    }

    private void initPlayer() {
        Array<Vector2> temp = levelHandler.getPlayers();
        for (int i = 0; i < temp.size; i++) {
            player = new Player(temp.get(i).x, temp.get(i).y);
            addEntity(player);
        }
    }

    private void initFires() {
        Array<Vector2> temp = levelHandler.getFires();
        Array<Integer> temp2 = levelHandler.getSpeed();
        Array<Integer> temp3 = levelHandler.getVSpeed();
        Array<Integer> temp4 = levelHandler.getSpecial();
        for (int i = 0; i < temp.size; i++) {
            addEntity(new Fire(temp.get(i).x, temp.get(i).y,
                    temp2.get(i), temp3.get(i), temp4.get(i)));
        }
    }

    private void initBlocks() {
        Array<Vector2> temp = levelHandler.getBlocks();
        for (int i = 0; i < temp.size; i++) {
            addEntity(new Block(temp.get(i).x, temp.get(i).y));
        }
    }

    private void initSpikes() {
        Array<Vector2> temp = levelHandler.getSpikes();
        for (int i = 0; i < temp.size; i++) {
            addEntity(new Spike(temp.get(i).x, temp.get(i).y));
        }
    }

    private void initCookie() {
        Array<Vector2> temp = levelHandler.getCookie();
        for (int i = 0; i < temp.size; i++) {
            addEntity(new Cookie(temp.get(i).x, temp.get(i).y));
        }
    }

    private void handleInput() {
        float xVelocity = 2.75f;
        float yVelocity = 6.90f;
        if ((Gdx.input.isKeyPressed(Input.Keys.D) || rightButton)) {
            player.setHsp(xVelocity);
        } else if ((Gdx.input.isKeyPressed(Input.Keys.A) || leftButton)) {
            player.setHsp(-xVelocity);
        } else {
            player.setHsp(0);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R) || warpButton) {
            if (!player.isWarping()) {
                player.warp();
            }
            warpButton = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || jumpButton) {
            if (!player.isJumping()) {
                if (!player.isParallel()) {
                    player.setVsp(yVelocity);
                } else if (player.isParallel()) {
                    player.setVsp(-yVelocity);
                }
                player.setJumping(true);
            }
            jumpButton = false;
        }
    }

}
