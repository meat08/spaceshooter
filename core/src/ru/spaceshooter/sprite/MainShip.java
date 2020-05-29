package ru.spaceshooter.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.Ship;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.pool.BulletPool;
import ru.spaceshooter.pool.ExplosionPool;
import ru.spaceshooter.pool.HitExplodePool;

public class MainShip extends Ship {

    private static final float SIZE = 0.1f;
    private static final float MARGIN = 0.03f;
    private static final int INVALID_POINTER = -1;
    private static final float BOOST_SHOOT_INTERVAL = 4f;
    private static final float BOOST_SHOOT_FACTOR = 0.5f;
    private static final float BOOST_SHIELD_INTERVAL = 5f;
    private static final float SENSE = 0.85f;
    private static final float DEFAULT_RELOAD_INTERVAL = 0.25f;

    private int leftPointer;
    private int rightPointer;
    private float boostTimer;
    private float shieldTimer;

    private boolean pressedLeft;
    private boolean pressedRight;
    private boolean isTouched;
    private boolean accelerometerAvailable;
    private boolean isShield;
    private boolean isShootBoost;



    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, HitExplodePool hitExplodePool) {
        super(atlas.findRegion("main_ship"), 1, 1, 1);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.hitExplodePool = hitExplodePool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletV = new Vector2(0, 0.5f);
        bulletHeight = 0.025f;
        damage = 1;
        v0.set(0.4f, 0);
        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
        reloadInterval = DEFAULT_RELOAD_INTERVAL;
        reloadTimer = reloadInterval;
        maxHp = 100;
        hp = maxHp;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        accelerometerAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        isTouched = false;
        isShield = false;
        isShootBoost = false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y + getHalfHeight());
        autoShoot(delta);
        if (isShootBoost) {
            boostShoot(delta);
        }
        if (isShield) {
            activateShield(delta);
        }
        if (accelerometerAvailable) {
            float accelerometerX = Gdx.input.getAccelerometerX();
            if (accelerometerX < -SENSE) {
                moveRight();
            } else if (accelerometerX > SENSE) {
                moveLeft();
            } else {
                if(!isTouched) {
                    stop();
                }
            }
        }
        if (getLeft() < worldBounds.getLeft()) {
            stop();
            setLeft(worldBounds.getLeft());
        }
        if (getRight() > worldBounds.getRight()) {
            stop();
            setRight(worldBounds.getRight());
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
    public void damage(int damage) {
        if (!isShield) {
            super.damage(damage);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            isTouched = true;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            isTouched = true;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                isTouched = false;
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                isTouched = false;
                stop();
            }
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    public boolean isBulletCollision(Bullet bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom()
        );
    }

    public void addHp(int hp) {
        if (this.hp + hp >= maxHp) {
            this.hp = maxHp;
        } else {
            this.hp += hp;
        }
    }

    public void shootSpeedBoost() {
        isShootBoost = !isShootBoost;
        isShootMulti = !isShootMulti;
        reloadInterval = reloadInterval * BOOST_SHOOT_FACTOR;
    }

    public void setShield() {
        isShield = true;
    }

    public boolean isShield() {
        return isShield;
    }

    private void activateShield(float delta) {
        shieldTimer += delta;
        if (shieldTimer >= BOOST_SHIELD_INTERVAL) {
            shieldTimer = 0f;
            isShield = false;
        }
    }

    private void boostShoot(float delta) {
        boostTimer += delta;
        if (boostTimer >= BOOST_SHOOT_INTERVAL) {
            reloadInterval = DEFAULT_RELOAD_INTERVAL;
            boostTimer = 0f;
            isShootBoost = false;
            isShootMulti = false;
        }
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

    public void startNewGame() {
        flushDestroy();
        stop();
        maxHp = 100;
        hp = maxHp;
        reloadInterval = DEFAULT_RELOAD_INTERVAL;
        isTouched = false;
        isShield = false;
        isShootBoost = false;
        pressedRight = false;
        pressedLeft = false;
        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
        pos.x = 0f;
    }
}
