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
package ru.spaceshooter.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.spaceshooter.pool.AsteroidPool;

public class AsteroidEmitter {

    private static final float GENERATE_INTERVAL = 30f;

    private final TextureRegion[] asteroidRegion;

    private AsteroidPool asteroidPool;
    private float generateTimer;

    public AsteroidEmitter(TextureAtlas atlas, AsteroidPool asteroidPool) {
        this.asteroidRegion = Regions.split(atlas.findRegion("asteroid"),5, 6, 30);
        this.asteroidPool = asteroidPool;
    }

    public void generate (float delta) {
        generateTimer += delta;
        if (generateTimer >= GENERATE_INTERVAL) {
            generateTimer = 0f;
            asteroidPool.obtain().set(asteroidRegion);
        }
    }
}
