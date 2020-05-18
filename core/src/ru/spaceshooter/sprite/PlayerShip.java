package ru.spaceshooter.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.pool.BulletPool;

public class PlayerShip extends Sprite {
    private static final float V_LEN = 0.004f;

    private Vector2 touch;
    private Vector2 v;
    private Vector2 common;
    private Rect worldBounds;

    private boolean keyUp = false;
    private boolean keyDown = false;
    private boolean keyRight = false;
    private boolean keyLeft = false;
    private boolean mouseClick = false;

    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletV;
    private Sound bulletSound;

    private float shootTimer;
    private float shootInterval;


    public PlayerShip(TextureAtlas atlas, BulletPool bulletPool, Sound bulletSound) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletSound = bulletSound;
        bulletV = new Vector2(0, 0.5f);
        touch = new Vector2();
        v = new Vector2();
        common = new Vector2();
        shootInterval = 0.25f;
    }

    @Override
    public void update(float delta) {
        shootTimer += delta;
        if (shootTimer >= shootInterval) {
            shoot();
            shootTimer = 0f;
        }
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
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom() + 0.05f);
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

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, 0.01f, worldBounds, 1);
        bulletSound.play(0.5f);
    }
}
