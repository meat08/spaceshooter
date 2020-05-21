package ru.spaceshooter.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.Ship;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.pool.BulletPool;
import ru.spaceshooter.pool.ExplosionPool;

public class MainShip extends Ship {

    private static final float SIZE = 0.15f;
    private static final float MARGIN = 0.05f;
    private static final float V_LEN = 0.004f;
    private static final int HP = 100;

    private Vector2 touch;
    private Vector2 common;

    private boolean keyUp = false;
    private boolean keyDown = false;
    private boolean keyRight = false;
    private boolean keyLeft = false;
    private boolean mouseClick = false;



    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletV = new Vector2(0, 0.5f);
        bulletHeight = 0.01f;
        damage = 1;
        reloadInterval = 0.25f;
        reloadTimer = reloadInterval;
        hp = HP;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        touch = new Vector2();
        common = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y + getHalfHeight());
        if (mouseClick) {
            common.set(touch);
            if ((common.sub(pos)).len() > V_LEN) {
                pos.add(v);
            } else {
                pos.set(touch);
                v.setZero();
                mouseClick = false;
            }
        }
        if (keyUp & getTop() < worldBounds.getTop()) {
            pos.add(0, V_LEN);
        }
        if (keyDown & getBottom() > worldBounds.getBottom()) {
            pos.add(0, -V_LEN);
        }
        if (keyRight & getRight() < worldBounds.getRight()) {
            pos.add(V_LEN, 0);
        }
        if (keyLeft & getLeft() > worldBounds.getLeft()) {
            pos.add(-V_LEN, 0);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(SIZE);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    public void dispose() {
        sound.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mouseClick = true;
        this.touch.set(touch);
        v.set(touch.sub(pos)).setLength(V_LEN);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        mouseClick = true;
        this.touch.set(touch);
        v.set(touch.sub(pos)).setLength(V_LEN);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                keyUp = true;
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                keyDown = true;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                keyLeft = true;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                keyRight = true;
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.UP:
                keyUp = false;
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                keyDown = false;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                keyLeft = false;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                keyRight = false;
                break;
        }
        return false;
    }
}
