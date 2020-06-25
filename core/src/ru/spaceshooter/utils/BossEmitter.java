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
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.enums.ShootType;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.math.Rnd;
import ru.spaceshooter.pool.BossPool;
import ru.spaceshooter.sprite.Boss;

public class BossEmitter {

    private static final float BOSS_ONE_HEIGHT = 0.25f;
    private static final int BOSS_ONE_HP = 80;
    private static final float BOSS_ONE_SPEED = -0.12f;
    private static final float BOSS_ONE_BULLET_HEIGHT = 0.03f;
    private static final float BOSS_ONE_BULLET_VY = -0.45f;
    private static final int BOSS_ONE_BULLET_DAMAGE = 10;
    private static final float BOSS_ONE_RELOAD_INTERVAL = 0.75f;
    private static final ShootType BOSS_ONE_SHOOT_TYPE = ShootType.BOSS0;
    private static final int BOSS_ONE_TYPE = 0;

    private static final float BOSS_TWO_HEIGHT = 0.25f;
    private static final int BOSS_TWO_HP = 100;
    private static final float BOSS_TWO_SPEED = -0.15f;
    private static final float BOSS_TWO_BULLET_HEIGHT = 0.03f;
    private static final float BOSS_TWO_BULLET_VY = -0.45f;
    private static final int BOSS_TWO_BULLET_DAMAGE = 10;
    private static final float BOSS_TWO_RELOAD_INTERVAL = 0.95f;
    private static final ShootType BOSS_TWO_SHOOT_TYPE = ShootType.BOSS1;
    private static final int BOSS_TWO_TYPE = 1;

    private static final float BOSS_THREE_HEIGHT = 0.35f;
    private static final int BOSS_THREE_HP = 180;
    private static final float BOSS_THREE_SPEED = -0.09f;
    private static final float BOSS_THREE_BULLET_HEIGHT = 0.04f;
    private static final float BOSS_THREE_BULLET_VY = -0.45f;
    private static final int BOSS_THREE_BULLET_DAMAGE = 15;
    private static final float BOSS_THREE_RELOAD_INTERVAL = 1.6f;
    private static final ShootType BOSS_THREE_SHOOT_TYPE = ShootType.BOSS2;
    private static final int BOSS_THREE_TYPE = 2;

    private static final float BOSS_FOUR_HEIGHT = 0.35f;
    private static final int BOSS_FOUR_HP = 250;
    private static final float BOSS_FOUR_SPEED = -0.07f;
    private static final float BOSS_FOUR_BULLET_HEIGHT = 0.045f;
    private static final float BOSS_FOUR_BULLET_VY = -0.45f;
    private static final int BOSS_FOUR_BULLET_DAMAGE = 20;
    private static final float BOSS_FOUR_RELOAD_INTERVAL = 0.95f;
    private static final ShootType BOSS_FOUR_SHOOT_TYPE = ShootType.BOSS3;
    private static final int BOSS_FOUR_TYPE = 3;

    private Rect worldBounds;

    private final TextureRegion[] bossOneRegion;
    private final TextureRegion[] bossTwoRegion;
    private final TextureRegion[] bossThreeRegion;
    private final TextureRegion[] bossFourRegion;

    private final Vector2 bossOneV;
    private final Vector2 bossTwoV;
    private final Vector2 bossThreeV;
    private final Vector2 bossFourV;

    private final TextureRegion bulletRegion;
    private final TextureRegion bulletRegionMoon;
    private final BossPool bossPool;

    private float diffFactor;

    public BossEmitter(TextureAtlas atlas, BossPool bossPool) {
        this.bossOneRegion = Regions.split(atlas.findRegion("boss0"), 1, 4, 4);
        this.bossTwoRegion = Regions.split(atlas.findRegion("boss1"), 1, 4, 4);
        this.bossThreeRegion = Regions.split(atlas.findRegion("boss2"), 1, 4, 4);
        this.bossFourRegion = Regions.split(atlas.findRegion("boss3"), 1, 4, 4);

        this.bossOneV = new Vector2(BOSS_ONE_SPEED, 0f);
        this.bossTwoV = new Vector2(BOSS_TWO_SPEED, 0f);
        this.bossThreeV = new Vector2(BOSS_THREE_SPEED, 0f);
        this.bossFourV = new Vector2(BOSS_FOUR_SPEED, 0f);

        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.bulletRegionMoon = atlas.findRegion("bulletEnemy_type1");

        this.bossPool = bossPool;

        diffFactor = 1f;
    }

    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void setDiffFactor(float diff) {
        this.diffFactor = diff;
    }

    public void generate(int type) {
        Boss boss = bossPool.obtain();
        switch (type) {
            case 1: {
                boss.set(
                        bossOneRegion,
                        bossOneV,
                        bulletRegionMoon,
                        bulletRegion,
                        BOSS_ONE_BULLET_HEIGHT,
                        BOSS_ONE_BULLET_VY,
                        transform(BOSS_ONE_BULLET_DAMAGE),
                        BOSS_ONE_RELOAD_INTERVAL,
                        transform(BOSS_ONE_HP),
                        BOSS_ONE_HEIGHT,
                        BOSS_ONE_SHOOT_TYPE,
                        BOSS_ONE_TYPE
                );
                break;
            }
            case 2: {
                boss.set(
                        bossTwoRegion,
                        bossTwoV,
                        bulletRegion,
                        bulletRegionMoon,
                        BOSS_TWO_BULLET_HEIGHT,
                        BOSS_TWO_BULLET_VY,
                        transform(BOSS_TWO_BULLET_DAMAGE),
                        BOSS_TWO_RELOAD_INTERVAL,
                        transform(BOSS_TWO_HP),
                        BOSS_TWO_HEIGHT,
                        BOSS_TWO_SHOOT_TYPE,
                        BOSS_TWO_TYPE
                );
                break;
            }
            case 3: {
                boss.set(
                        bossThreeRegion,
                        bossThreeV,
                        bulletRegion,
                        bulletRegion,
                        BOSS_THREE_BULLET_HEIGHT,
                        BOSS_THREE_BULLET_VY,
                        transform(BOSS_THREE_BULLET_DAMAGE),
                        BOSS_THREE_RELOAD_INTERVAL,
                        transform(BOSS_THREE_HP),
                        BOSS_THREE_HEIGHT,
                        BOSS_THREE_SHOOT_TYPE,
                        BOSS_THREE_TYPE
                );
                break;
            }
            case 4: {
                boss.set(
                        bossFourRegion,
                        bossFourV,
                        bulletRegionMoon,
                        bulletRegion,
                        BOSS_FOUR_BULLET_HEIGHT,
                        BOSS_FOUR_BULLET_VY,
                        transform(BOSS_FOUR_BULLET_DAMAGE),
                        BOSS_FOUR_RELOAD_INTERVAL,
                        transform(BOSS_FOUR_HP),
                        BOSS_FOUR_HEIGHT,
                        BOSS_FOUR_SHOOT_TYPE,
                        BOSS_FOUR_TYPE
                );
                break;
            }
        }
        boss.pos.x = Rnd.nextFloat(worldBounds.getLeft() + boss.getHalfWidth(), worldBounds.getRight() - boss.getHalfWidth());
        boss.setBottom(worldBounds.getTop());
    }

    private int transform(int value) {
        return (int)(value * diffFactor) > 0 ? (int)(value * diffFactor) : value;
    }

}