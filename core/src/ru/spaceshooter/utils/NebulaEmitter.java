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

import ru.spaceshooter.pool.NebulaPool;

public class NebulaEmitter {

    private static final float GENERATE_INTERVAL = 30f;
    private static final float SPEED = -0.05f;
    private static final float SPEED_INCREASE = 0.007f;

    private final TextureRegion[] nebula0;
    private final TextureRegion[] nebula1;
    private final TextureRegion[] nebula2;

    private NebulaPool nebulaPool;
    private float generateTimer;
    private float level;

    public NebulaEmitter(TextureAtlas atlas, NebulaPool nebulaPool) {
        this.nebula0 = Regions.split(atlas.findRegion("nebula0"), 1, 1, 1);
        this.nebula1 = Regions.split(atlas.findRegion("nebula1"), 1, 1, 1);
        this.nebula2 = Regions.split(atlas.findRegion("nebula2"), 1, 1, 1);
        this.nebulaPool = nebulaPool;
        level = 1;
    }

    public void generate (float delta) {
        generateTimer += delta;
        if (generateTimer >= GENERATE_INTERVAL) {
            generateTimer = 0f;
            float type = (float) Math.random();
            if (type < 0.5f) {
                nebulaPool.obtain().set(nebula2, increaseSpeed());
            } else if (type < 0.8f) {
                nebulaPool.obtain().set(nebula1, increaseSpeed());
            } else {
                nebulaPool.obtain().set(nebula0, increaseSpeed());
            }
        }
    }

    public void setLevel(float level) {
        this.level = level;
    }

    private float increaseSpeed() {
        return Math.max((SPEED - level * SPEED_INCREASE), -0.3f);
    }
}
