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

public class Enemy extends Ship {

    private static final float V_Y = -0.3f;

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound, HitExplodePool hitExplodePool, GameScreen screen) {
        super(bulletPool, explosionPool, worldBounds, sound, hitExplodePool, screen);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getTop() < worldBounds.getTop()) {
            v.set(v0);
            switch (shootType) {
                case ONE: {
                    bulletPos.set(pos.x, pos.y - getHalfHeight());
                    break;
                }
                case DUAL:
                case DUAL_SPIN: {
                    bulletPos.set(pos.x - getHalfWidth()/2, pos.y - getHalfHeight()/2);
                    bullet2Pos.set(pos.x + getHalfWidth()/2, pos.y - getHalfHeight()/2);
                    break;
                }
                case QUAD: {
                    bulletPos.set(pos.x - getHalfHeight(), pos.y - getHalfHeight()/2);
                    bullet2Pos.set(pos.x - 0.02f, pos.y - getHalfHeight()/2);
                    bullet3Pos.set(pos.x + 0.02f, pos.y - getHalfHeight()/2);
                    bullet4Pos.set(pos.x + getHalfHeight(), pos.y - getHalfHeight()/2);
                }
            }
            if (screen.isNotNuked()) {
                autoShoot(delta);
            }
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            int hp,
            float height,
            ShootType shootType
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = reloadInterval;
        this.hp = hp;
        setHeightProportion(height);
        this.v.set(0, V_Y);
        this.shootType = shootType;
    }

    public boolean isBulletCollision(Bullet bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
        );
    }

}
