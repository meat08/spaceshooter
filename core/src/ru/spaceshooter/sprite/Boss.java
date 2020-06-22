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

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.Ship;
import ru.spaceshooter.base.enums.ShootType;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.pool.BulletPool;
import ru.spaceshooter.pool.ExplosionPool;
import ru.spaceshooter.pool.HitExplodePool;
import ru.spaceshooter.screen.GameScreen;

public class Boss extends Ship {

    private static final float V_Y = -0.3f;
    private static final float SPAWN_WAIT = 5f;
    private int bossType;
    private float spawnTimer;
    private boolean isSpawn;

    public Boss(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound, HitExplodePool hitExplodePool, GameScreen screen) {
        super(bulletPool, explosionPool, worldBounds, sound, hitExplodePool, screen);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (isSpawn) {
            screen.setLabelReadyVisible(true);
            spawnTimer += delta;
            if (spawnTimer > SPAWN_WAIT) {
                v.set(0, V_Y);
                spawnTimer = 0f;
                isSpawn = false;
                screen.setLabelReadyVisible(false);
            }
        }
        if (getTop() < worldBounds.getTop()) {
            move();
            shoot(delta);
        }
    }

    private void shoot(float delta) {
        switch (shootType) {
            case BOSS0: {
                bulletPos.set(pos.x - getHalfWidth()/2, pos.y - getHalfHeight()/2);
                bullet2Pos.set(pos.x, pos.y - getHalfHeight());
                bullet3Pos.set(pos.x + getHalfWidth()/2, pos.y - getHalfHeight()/2);
                break;
            }
            case BOSS1:
            case BOSS2: {
                bulletPos.set(pos.x - getHalfHeight()/2, pos.y - getHalfHeight()/2);
                bullet2Pos.set(pos.x - 0.02f, pos.y - getHalfHeight()/2);
                bullet3Pos.set(pos.x + 0.02f, pos.y - getHalfHeight()/2);
                bullet4Pos.set(pos.x + getHalfHeight()/2, pos.y - getHalfHeight()/2);
                break;
            }
        }
        if (screen.isNotNuked()) {
            autoShoot(delta);
        }
    }

    private void move() {
        v.set(v0);
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft() + 0.01f);
            v0.scl(-1f);
        } else if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight() - 0.01f);
            v0.scl(-1f);
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            TextureRegion bulletRegion1,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            int hp,
            float height,
            ShootType shootType,
            int bossType
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletRegion1 = bulletRegion1;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = reloadInterval;
        this.hp = hp;
        setHeightProportion(height);
        this.shootType = shootType;
        this.bossType = bossType;
        this.v.setZero();
        isSpawn = true;
    }

    public boolean isBulletCollision(Bullet bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
        );
    }

    public int getBossType() {
        return bossType;
    }
}
