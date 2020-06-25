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
package ru.spaceshooter.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.enums.ShootType;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.pool.BulletPool;
import ru.spaceshooter.pool.ExplosionPool;
import ru.spaceshooter.pool.HitExplodePool;
import ru.spaceshooter.screen.GameScreen;

public class Ship extends Sprite {

    protected Vector2 v0;
    protected Vector2 v;
    protected Rect worldBounds;
    protected ExplosionPool explosionPool;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected TextureRegion bulletRegion1;
    protected HitExplodePool hitExplodePool;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected Vector2 bullet2Pos;
    protected Vector2 bullet3Pos;
    protected Vector2 bullet4Pos;
    protected float bulletHeight;
    protected int damage;
    protected float reloadInterval;
    protected float reloadTimer;
    protected float extraReloadTimer;
    protected boolean isShootMulti;
    protected Sound sound;
    protected int hp;
    protected int maxHp;
    protected ShootType shootType;
    protected GameScreen screen;
    private boolean isDestroyBottom;


    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        isDestroyBottom = false;
        initVector();
    }

    public Ship(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound, HitExplodePool hitExplodePool, GameScreen screen) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.hitExplodePool = hitExplodePool;
        this.worldBounds = worldBounds;
        this.sound = sound;
        this.screen = screen;
        initVector();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        if (isAnimateEnd) {
            frame = 0;
        }
    }

    protected void autoShoot(float delta) {
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            switch (shootType) {
                case ONE: {
                    if (isShootMulti) {
                        shootMulti();
                    } else {
                        shoot();
                    }
                    break;
                }
                case DUAL: {
                    if (isShootMulti) {
                        shootMultiDual();
                    } else {
                        shootDual(false);
                    }
                    break;
                }
                case DUAL_SPIN: {
                    shootDual(true);
                    break;
                }
                case QUAD: {
                    shootQuad();
                    break;
                }
                case TRIPLE: {
                    if (isShootMulti) {
                        shootMultiDual();
                    } else {
                        shootTriple();
                    }
                    break;
                }
            }
            reloadTimer = 0f;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public int getMaxHp() {
        return maxHp;
    }
    public void setDestroyBottom(boolean destroyBottom) {
        isDestroyBottom = destroyBottom;
    }

    private void initVector() {
        v0 = new Vector2();
        v = new Vector2();
        bulletV = new Vector2();
        bulletPos = new Vector2();
        bullet2Pos = new Vector2();
        bullet3Pos = new Vector2();
        bullet4Pos = new Vector2();
    }

    public void pew() {
        if (screen.isSoundOn() & screen.isNotNuked()) {
            sound.play(screen.getVolumeSound());
        }
    }

    private void shoot() {
        bulletPool.obtain().set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage, false);
        pew();
    }

    private void shootDual(boolean spin) {
        bulletPool.obtain().set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage, spin);
        bulletPool.obtain().set(this, bulletRegion, bullet2Pos, bulletV, bulletHeight, worldBounds, damage, spin);
        pew();
    }

    private void shootTriple() {
        bulletPool.obtain().set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage, false);
        bulletPool.obtain().set(this, bulletRegion, bullet2Pos, bulletV, bulletHeight, worldBounds, damage, false);
        bulletPool.obtain().set(this, bulletRegion, bullet3Pos, bulletV, bulletHeight, worldBounds, damage, false);
        pew();
    }

    private void shootQuad() {
        bulletPool.obtain().set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage, false);
        bulletPool.obtain().set(this, bulletRegion, bullet2Pos, bulletV, bulletHeight, worldBounds, damage, false);
        bulletPool.obtain().set(this, bulletRegion, bullet3Pos, bulletV, bulletHeight, worldBounds, damage, false);
        bulletPool.obtain().set(this, bulletRegion, bullet4Pos, bulletV, bulletHeight, worldBounds, damage, false);
        pew();
    }

    private void shootMulti() {
        bulletPool.obtain().set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage, false);
        bulletPool.obtain().set(this, bulletRegion, bulletPos, bulletV.cpy().add(0.05f, 0f), bulletHeight, worldBounds, damage, false);
        bulletPool.obtain().set(this, bulletRegion, bulletPos, bulletV.cpy().add(-0.05f, 0f), bulletHeight, worldBounds, damage, false);
        pew();
    }

    private void shootMultiDual() {
        bulletPool.obtain().set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage, false);
        bulletPool.obtain().set(this, bulletRegion, bulletPos, bulletV.cpy().add(0.05f, 0f), bulletHeight, worldBounds, damage, false);
        bulletPool.obtain().set(this, bulletRegion, bulletPos, bulletV.cpy().add(-0.05f, 0f), bulletHeight, worldBounds, damage, false);
        bulletPool.obtain().set(this, bulletRegion, bullet2Pos, bulletV, bulletHeight, worldBounds, damage, false);
        bulletPool.obtain().set(this, bulletRegion, bullet2Pos, bulletV.cpy().add(0.05f, 0f), bulletHeight, worldBounds, damage, false);
        bulletPool.obtain().set(this, bulletRegion, bullet2Pos, bulletV.cpy().add(-0.05f, 0f), bulletHeight, worldBounds, damage, false);
        pew();
    }

    public void damage(int damage) {
        hitExplodePool.obtain().set(getHeight(), getLeft(), getRight(), getTop(), getBottom());
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
    }

    private void boom() {
        if (isDestroyBottom) {
            explosionPool.obtain().set(getHeight(), worldBounds.getHalfWidth(), pos);
        } else {
            explosionPool.obtain().set(getHeight(), pos);
        }
    }
}
