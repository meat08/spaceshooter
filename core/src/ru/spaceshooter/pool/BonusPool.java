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
package ru.spaceshooter.pool;

import ru.spaceshooter.base.SpritesPool;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.sprite.Bonus;

public class BonusPool extends SpritesPool<Bonus> {
    private Rect worldBounds;

    public BonusPool(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    protected Bonus newObject() {
        return new Bonus(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
