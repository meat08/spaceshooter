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

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.Ship;
import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;

public class ForceShield extends Sprite {

    private Ship ship;
    private float animateTimer;

    public ForceShield(TextureAtlas atlas) {
        super(atlas.findRegion("shield"), 2, 16, 30);
    }

    public void resize(Rect worldBounds, Ship ship) {
        super.resize(worldBounds);
        this.ship = ship;
        setHeightProportion(ship.getHeight() + 0.05f);
        setBottom(worldBounds.getTop());
    }

    @Override
    public void update(float delta) {
        pos.set(ship.pos);
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                frame = 0;
            }
        }
    }

    public boolean isBulletCollision(Bullet bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
        );
    }
}
