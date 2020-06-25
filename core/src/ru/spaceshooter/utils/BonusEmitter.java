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

import ru.spaceshooter.base.Ship;
import ru.spaceshooter.pool.BonusPool;

public class BonusEmitter {

    private static final float GENERATE_INTERVAL = 12f;
    private static final float GENERATE_NUKE_INTERVAL = 60f;

    private final TextureRegion[] hpBonusRegion;
    private final TextureRegion[] bulletBonusRegion;
    private final TextureRegion[] shieldBonusRegion;
    private final TextureRegion[] nukeBonusRegion;

    private BonusPool bonusPool;
    private Ship ship;
    private float generateTimer;
    private float generateNukeTimer;

    public BonusEmitter (TextureAtlas atlas, BonusPool bonusPool, Ship ship) {
        this.hpBonusRegion = Regions.split(atlas.findRegion("bonusHeal"),1, 6, 6);
        this.bulletBonusRegion = Regions.split(atlas.findRegion("bonusGuns"),1, 6, 6);
        this.shieldBonusRegion = Regions.split(atlas.findRegion("bonusShield"),1, 6, 6);
        this.nukeBonusRegion = Regions.split(atlas.findRegion("bonusNuke"),1, 6, 6);
        this.bonusPool = bonusPool;
        this.ship = ship;
    }

    public void generate (float delta) {
        generateTimer += delta;
        generateNukeTimer += delta;
        if (generateTimer >= GENERATE_INTERVAL) {
            generateTimer = 0f;
            float type = (float) Math.random();
            if (type < 0.5f) {
                bonusPool.obtain().set(shieldBonusRegion, 3);
            } else if (type < 0.8f) {
                bonusPool.obtain().set(bulletBonusRegion, 2);
            } else {
                if (ship.getHp() < ship.getMaxHp()) {
                    bonusPool.obtain().set(hpBonusRegion, 1);
                }
            }
        }
        if (generateNukeTimer >= GENERATE_NUKE_INTERVAL) {
            generateNukeTimer = 0f;
            float chance = (float) Math.random();
            if (chance > 0.7f) {
                bonusPool.obtain().set(nukeBonusRegion, 4);
            }
        }
    }
}
