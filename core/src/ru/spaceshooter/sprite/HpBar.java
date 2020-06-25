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

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.Ship;
import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;

public class HpBar extends Sprite {

    private Ship ship;
    private float width;

    public HpBar(TextureAtlas atlas) {
        super(atlas.findRegion("hp_bar"));
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                width, halfHeight
        );
    }

    public void resize(Rect worldBounds, Ship ship) {
        super.resize(worldBounds);
        this.ship = ship;
        width = worldBounds.getWidth();
        setHeightProportion(0.01f);
        setWidth(width);
        setBottom(worldBounds.getBottom());
    }

    @Override
    public void update(float delta) {
        width = getWidth() * (((float)ship.getHp() / ship.getMaxHp()));
    }
}
