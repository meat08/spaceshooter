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

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.math.Rnd;
import ru.spaceshooter.pool.ExplosionAsteroidPool;

public class Asteroid extends Sprite {

    private static final float ANIMATE_INTERVAL = 0.04f;
    private static final float SIZE_MIN = 0.035f;
    private static final float SIZE_MAX = 0.085f;
    private static final float SPEED = -0.1f;

    private float animateTimer;
    private Rect worldBounds;
    private ExplosionAsteroidPool explosionAsteroidPool;
    private Vector2 v;
    private int hp;

    public Asteroid(Rect worldBounds, ExplosionAsteroidPool explosionAsteroidPool) {
        this.worldBounds = worldBounds;
        this.explosionAsteroidPool = explosionAsteroidPool;
        this.v = new Vector2();
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                frame = 0;
            }
        }
        if (getBottom() <= worldBounds.getBottom()) {
            destroy();
        }
    }

    public void damage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
        frame = 0;
    }

    private void boom() {
        explosionAsteroidPool.obtain().set(getHeight(), pos);
    }

    public void set(TextureRegion[] regions) {
        this.regions = regions;
        this.hp = 20;
        v.set(0f, SPEED);
        setHeightProportion(Rnd.nextFloat(SIZE_MIN, SIZE_MAX));
        setBottom(worldBounds.getTop());
        pos.x = Rnd.nextFloat(worldBounds.getLeft() + getHalfWidth(), worldBounds.getRight() - getHalfWidth());
    }

    public boolean isBulletCollision(Bullet bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
        );
    }

    public int getDamage(int hp) {
        return (int) (hp * 0.1f);
    }

}
