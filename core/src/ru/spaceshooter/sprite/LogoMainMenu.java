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

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.pool.HitExplodePool;

public class LogoMainMenu extends Sprite {
    private static final float MARGIN = 0.05f;
    private static final float ANIMATE_INTERVAL = 1f;

    private HitExplodePool hitExplodePool;
    private float animateTimer;

    public LogoMainMenu(TextureAtlas atlas, HitExplodePool hitExplodePool) {
        super(atlas.findRegion("menu_logo_ship"));
        this.hitExplodePool = hitExplodePool;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.25f);
        setTop(worldBounds.getTop() - 0.3f);
        setLeft(worldBounds.getLeft() + MARGIN);
        setRight(worldBounds.getRight());
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            hitExplodePool.obtain().set(getHeight()/2, getLeft(), getRight(), getTop(), getBottom());
        }
    }
}
