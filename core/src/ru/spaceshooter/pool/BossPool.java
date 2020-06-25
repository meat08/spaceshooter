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

import com.badlogic.gdx.audio.Sound;

import ru.spaceshooter.base.SpritesPool;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;
import ru.spaceshooter.sprite.Boss;
import ru.spaceshooter.utils.Assets;

public class BossPool extends SpritesPool<Boss> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private HitExplodePool hitExplodePool;
    private Rect worldBounds;
    private Sound sound;
    private GameScreen screen;

    public BossPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, HitExplodePool hitExplodePool, GameScreen screen) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.hitExplodePool = hitExplodePool;
        this.worldBounds = worldBounds;
        this.screen = screen;
        sound = Assets.getInstance().getAssetManager().get("sounds/bullet.wav");
    }

    @Override
    protected Boss newObject() {
        return new Boss(bulletPool, explosionPool, worldBounds, sound, hitExplodePool, screen);
    }

    @Override
    public void dispose() {
        super.dispose();
        sound.dispose();
    }
}
