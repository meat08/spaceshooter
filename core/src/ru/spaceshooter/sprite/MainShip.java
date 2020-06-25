/*    Copyright (C) 2020  Ilya Mafov <i.mafov@gmail.com>
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package ru.spaceshooter.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.Ship;
import ru.spaceshooter.base.enums.ShootType;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.pool.BulletPool;
import ru.spaceshooter.pool.ExplosionPool;
import ru.spaceshooter.pool.HitExplodePool;
import ru.spaceshooter.screen.GameScreen;
import ru.spaceshooter.utils.Assets;
import ru.spaceshooter.utils.Regions;

public class MainShip extends Ship {

    private static final float SIZE = 0.1f;
    private static final float MARGIN = 0.03f;
    private static final int INVALID_POINTER = -1;
    private static final float BOOST_SHOOT_INTERVAL = 4f;
    private static final float BOOST_SHOOT_FACTOR = 0.5f;
    private static final float BOOST_SHIELD_INTERVAL = 5f;
    private static final float DEFAULT_RELOAD_INTERVAL = 0.25f;
    private static final int HP = 100;
    private static final int LIVES = 3;

    private TextureAtlas atlas;
    private float sense;
    private int leftPointer;
    private int rightPointer;
    private float boostTimer;
    private float shieldTimer;
    private int lives;
    private int upgradeCount;

    private boolean pressedLeft;
    private boolean pressedRight;
    private boolean isTouched;
    private boolean accelerometerAvailable;
    private boolean isShield;
    private boolean isShootBoost;



    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, HitExplodePool hitExplodePool, GameScreen screen) {
        super(atlas.findRegion("main_ship"), 1, 4, 4);
        this.atlas = atlas;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.hitExplodePool = hitExplodePool;
        this.screen = screen;
        sound = Assets.getInstance().getAssetManager().get("sounds/laser.wav");
        accelerometerAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        sense = screen.getSenseAccel();
        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletV = new Vector2(0, 0.5f);
        bulletHeight = 0.025f;
        v0.set(0.4f, 0);
        shootType = ShootType.ONE;
        damage = 1;
        upgradeCount = -1;
        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
        reloadInterval = DEFAULT_RELOAD_INTERVAL;
        reloadTimer = reloadInterval;
        lives = LIVES;
        maxHp = HP;
        hp = maxHp;
        isTouched = false;
        isShield = false;
        isShootBoost = false;
        isShootMulti = false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        switch (shootType) {
            case ONE: {
                bulletPos.set(pos.x, pos.y + getHalfHeight()/2);
                break;
            }
            case DUAL: {
                bulletPos.set(pos.x - getHalfWidth()/2, pos.y + getHalfHeight());
                bullet2Pos.set(pos.x + getHalfWidth()/2, pos.y + getHalfHeight());
                break;
            }
            case TRIPLE: {
                bulletPos.set(pos.x - getHalfWidth()/2, pos.y + getHalfHeight());
                bullet2Pos.set(pos.x + getHalfWidth()/2, pos.y + getHalfHeight());
                bullet3Pos.set(pos.x, pos.y + getHalfHeight()/2);
                break;
            }
        }
        if (screen.isNotNuked() & screen.isNotMainShipDestroy()) {
            autoShoot(delta);
        }
        if (isShootBoost) {
            boostShoot(delta);
        }
        if (isShield) {
            activateShield(delta);
        }
        if (accelerometerAvailable & screen.isAccelerometerOn()) {
            float accelerometerX = Gdx.input.getAccelerometerX();
            if (accelerometerX < -sense) {
                moveRight();
            } else if (accelerometerX > sense) {
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
        atlas.dispose();
    }

    @Override
    public void damage(int damage) {
        if (!isShield) {
            super.damage(damage);
            Gdx.input.vibrate(100);
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
            if (rightPointer == INVALID_POINTER) {
                moveLeft();
            }
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            isTouched = true;
            if (leftPointer == INVALID_POINTER) {
                moveRight();
            }
        }
        return true;
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
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                if (!pressedRight) {
                    moveLeft();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                if (!pressedLeft) {
                    moveRight();
                }
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

    @Override
    public void destroy() {
        if (--lives == 0) {
            super.destroy();
        } else {
            hp = maxHp;
            screen.explodeMainShip();
            Gdx.input.vibrate(300);
        }
    }

    public void upgradeShip(int upgradeCount, boolean isLoad) {
        this.upgradeCount = upgradeCount;
        switch (upgradeCount) {
            case 0: {
                if (!isLoad) {
                    maxHp += 50;
                    hp = maxHp;
                }
                break;
            }
            case 1: {
                this.regions = changeRegion();
                this.shootType = ShootType.DUAL;
                break;
            }
            case 2: {
                this.regions = changeRegion();
                this.shootType = ShootType.TRIPLE;
                break;
            }
            case 3: {
                if (!isLoad) {
                    maxHp += 250;
                    hp = maxHp;
                }
                this.regions = changeRegion();
                this.shootType = ShootType.TRIPLE;
                break;
            }
        }
    }

    public int getLives() {
        return lives;
    }

    public void setSense(float sense) {
        this.sense = sense;
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

    public void addMaxHp(int hp) {
        this.maxHp += hp;
    }

    public void addOneLive() {
        this.lives += 1;
    }

    public void shootSpeedBoost() {
        isShootBoost = true;
        isShootMulti = true;
        reloadInterval = reloadInterval * BOOST_SHOOT_FACTOR;
    }

    public void setShield() {
        isShield = true;
    }

    public boolean isShield() {
        return isShield;
    }

    public int getUpgradeCount() {
        return upgradeCount;
    }

    private TextureRegion[] changeRegion() {
        if (upgradeCount > 0) {
            return  Regions.split(atlas.findRegion("main_ship_guns"), 1, 4, 4);
        } else {
            return  Regions.split(atlas.findRegion("main_ship"), 1, 4, 4);
        }
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
        maxHp = HP;
        hp = maxHp;
        shootType = ShootType.ONE;
        lives = LIVES;
        reloadInterval = DEFAULT_RELOAD_INTERVAL;
        isTouched = false;
        isShield = false;
        isShootBoost = false;
        isShootMulti = false;
        pressedRight = false;
        pressedLeft = false;
        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
        pos.x = 0f;
        upgradeCount = -1;
        this.regions = changeRegion();
    }

    public void loadGame(int maxHp, int hp, float x, int lives, int upgradeCount) {
        flushDestroy();
        stop();
        this.maxHp = maxHp;
        this.hp = hp;
        this.lives = lives;
        upgradeShip(upgradeCount, true);
        pos.x = x;
        reloadInterval = DEFAULT_RELOAD_INTERVAL;
        isTouched = false;
        isShield = false;
        isShootMulti = false;
        isShootBoost = false;
        pressedRight = false;
        pressedLeft = false;
        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
    }
}
