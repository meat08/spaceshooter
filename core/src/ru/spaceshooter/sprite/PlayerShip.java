package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;

public class PlayerShip extends Sprite {
    private static final float V_LEN = 0.003f;

    private Vector2 touch;
    private Vector2 v;
    private Vector2 common;
    private Rect worldBounds;

    private boolean keyUp = false;
    private boolean keyDown = false;
    private boolean keyRight = false;
    private boolean keyLeft = false;
    private boolean mouseClick = false;


    public PlayerShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship")
                .split(atlas.findRegion("main_ship").originalWidth/2,
                        atlas.findRegion("main_ship").originalHeight)[0]);
        touch = new Vector2();
        v = new Vector2();
        common = new Vector2();
    }

    @Override
    public void update(float delta) {
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
        if (keyUp & pos.y < worldBounds.getTop()) {
            pos.add(0, V_LEN);
        }
        if (keyDown & pos.y > worldBounds.getBottom()) {
            pos.add(0, -V_LEN);
        }
        if (keyRight & pos.x < worldBounds.getRight()) {
            pos.add(V_LEN, 0);
        }
        if (keyLeft & pos.x > worldBounds.getLeft()) {
            pos.add(-V_LEN, 0);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        setBottom(worldBounds.getBottom() + 0.05f);
        this.worldBounds = worldBounds;
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
            case 19: {
                keyUp = true;
                break;
            }
            case 20: {
                keyDown = true;
                break;
            }
            case 21: {
                keyLeft = true;
                break;
            }
            case 22: {
                keyRight = true;
                break;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case 19: {
                keyUp = false;
                break;
            }
            case 20: {
                keyDown = false;
                break;
            }
            case 21: {
                keyLeft = false;
                break;
            }
            case 22: {
                keyRight = false;
                break;
            }
        }
        return false;
    }
}
