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

import ru.spaceshooter.math.Rect;
import ru.spaceshooter.math.Rnd;
import ru.spaceshooter.pool.EnemyPool;
import ru.spaceshooter.sprite.Enemy;

public class EnemyEmitter {

    private static final float GENERATE_INTERVAL = 4.05f;
    private static final float BOOST_GENERATE_INTERVAL = 0.05f;
    private static final int LEVEL_GAIN_ENEMY = 10;

    private static final float ENEMY_SMALL_HEIGHT = 0.08f;
    private static final int ENEMY_SMALL_HP = 3;
    private static final float ENEMY_SMALL_SPEED = -0.15f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.015f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_BULLET_DAMAGE = 2;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 1f;
    private static final int ENEMY_SMALL_SHOOT_TYPE = 1;
    private static final int ENEMY_SMALL_SHIP_TYPE = 1;

    private static final float ENEMY_SMALL_V1_HEIGHT = 0.09f;
    private static final int ENEMY_SMALL_V1_HP = 5;
    private static final float ENEMY_SMALL_V1_SPEED = -0.15f;
    private static final float ENEMY_SMALL_V1_BULLET_HEIGHT = 0.025f;
    private static final float ENEMY_SMALL_V1_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_V1_BULLET_DAMAGE = 3;
    private static final float ENEMY_SMALL_V1_RELOAD_INTERVAL = 1f;
    private static final int ENEMY_SMALL_V1_SHOOT_TYPE = 1;
    private static final int ENEMY_SMALL_V1_SHIP_TYPE = 1;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.12f;
    private static final int ENEMY_MEDIUM_HP = 8;
    private static final float ENEMY_MEDIUM_SPEED = -0.03f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.025f;
    private static final float ENEMY_MEDIUM_BULLET_VY = -0.25f;
    private static final int ENEMY_MEDIUM_BULLET_DAMAGE = 5;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_MEDIUM_SHOOT_TYPE = 1;
    private static final int ENEMY_MEDIUM_SHIP_TYPE = 1;

    private static final float ENEMY_MEDIUM_V1_HEIGHT = 0.13f;
    private static final int ENEMY_MEDIUM_V1_HP = 10;
    private static final float ENEMY_MEDIUM_V1_SPEED = -0.03f;
    private static final float ENEMY_MEDIUM_V1_BULLET_HEIGHT = 0.03f;
    private static final float ENEMY_MEDIUM_V1_BULLET_VY = -0.25f;
    private static final int ENEMY_MEDIUM_V1_BULLET_DAMAGE = 7;
    private static final float ENEMY_MEDIUM_V1_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_MEDIUM_V1_SHOOT_TYPE = 1;
    private static final int ENEMY_MEDIUM_V1_SHIP_TYPE = 1;

    private static final float ENEMY_MEDIUM_V2_HEIGHT = 0.13f;
    private static final int ENEMY_MEDIUM_V2_HP = 15;
    private static final float ENEMY_MEDIUM_V2_SPEED = -0.03f;
    private static final float ENEMY_MEDIUM_V2_BULLET_HEIGHT = 0.03f;
    private static final float ENEMY_MEDIUM_V2_BULLET_VY = -0.25f;
    private static final int ENEMY_MEDIUM_V2_BULLET_DAMAGE = 7;
    private static final float ENEMY_MEDIUM_V2_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_MEDIUM_V2_SHOOT_TYPE = 4;
    private static final int ENEMY_MEDIUM_V2_SHIP_TYPE = 3;

    private static final float ENEMY_BIG_HEIGHT = 0.15f;
    private static final int ENEMY_BIG_HP = 15;
    private static final float ENEMY_BIG_SPEED = -0.005f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.035f;
    private static final float ENEMY_BIG_BULLET_VY = -0.3f;
    private static final int ENEMY_BIG_BULLET_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 2.5f;
    private static final int ENEMY_BIG_SHOOT_TYPE = 2;
    private static final int ENEMY_BIG_SHIP_TYPE = 2;

    private static final float ENEMY_BIG_V1_HEIGHT = 0.16f;
    private static final int ENEMY_BIG_V1_HP = 18;
    private static final float ENEMY_BIG_V1_SPEED = -0.0048f;
    private static final float ENEMY_BIG_V1_BULLET_HEIGHT = 0.09f;
    private static final float ENEMY_BIG_V1_BULLET_VY = -0.3f;
    private static final int ENEMY_BIG_V1_BULLET_DAMAGE = 12;
    private static final float ENEMY_BIG_V1_RELOAD_INTERVAL = 2.5f;
    private static final int ENEMY_BIG_V1_SHOOT_TYPE = 3;
    private static final int ENEMY_BIG_V1_SHIP_TYPE = 2;

    private Rect worldBounds;
    private float generateTimer;

    private final TextureRegion[] enemySmallRegions;
    private final TextureRegion[] enemySmallT1Regions;
    private final TextureRegion[] enemyMediumRegions;
    private final TextureRegion[] enemyMediumT1Regions;
    private final TextureRegion[] enemyMediumT2Regions;
    private final TextureRegion[] enemyBigRegions;
    private final TextureRegion[] enemyBigT1Regions;

    private final Vector2 enemySmallV;
    private final Vector2 enemySmallV1;
    private final Vector2 enemyMediumV;
    private final Vector2 enemyMediumV1;
    private final Vector2 enemyMediumV2;
    private final Vector2 enemyBigV;
    private final Vector2 enemyBigV1;

    private final TextureRegion bulletRegion;
    private final TextureRegion bulletRegionMoon;
    private final TextureRegion bulletRegionSpine;
    private final EnemyPool enemyPool;
    private int level;
    private float diffFactor;

    public EnemyEmitter(TextureAtlas atlas, EnemyPool enemyPool) {
        TextureRegion enemy0 = atlas.findRegion("enemy0");
        this.enemySmallRegions = Regions.split(enemy0, 1, 4, 4);
        TextureRegion enemy0T1 = atlas.findRegion("enemy0_type1");
        this.enemySmallT1Regions = Regions.split(enemy0T1, 1, 4, 4);
        TextureRegion enemy1 = atlas.findRegion("enemy1");
        this.enemyMediumRegions = Regions.split(enemy1, 1, 4, 4);
        TextureRegion enemy1T1 = atlas.findRegion("enemy1_type1");
        this.enemyMediumT1Regions = Regions.split(enemy1T1, 1, 4, 4);
        TextureRegion enemy1T2 = atlas.findRegion("enemy1_type2");
        this.enemyMediumT2Regions = Regions.split(enemy1T2, 1, 4, 4);
        TextureRegion enemy2 = atlas.findRegion("enemy2");
        this.enemyBigRegions = Regions.split(enemy2, 1, 4, 4);
        TextureRegion enemy2T1 = atlas.findRegion("enemy2_type1");
        this.enemyBigT1Regions = Regions.split(enemy2T1, 1, 4, 4);

        this.enemySmallV = new Vector2(0, ENEMY_SMALL_SPEED);
        this.enemySmallV1 = new Vector2(0, ENEMY_SMALL_V1_SPEED);
        this.enemyMediumV = new Vector2(0, ENEMY_MEDIUM_SPEED);
        this.enemyMediumV1 = new Vector2(0, ENEMY_MEDIUM_V1_SPEED);
        this.enemyMediumV2 = new Vector2(0, ENEMY_MEDIUM_V2_SPEED);
        this.enemyBigV = new Vector2(0, ENEMY_BIG_SPEED);
        this.enemyBigV1 = new Vector2(0, ENEMY_BIG_V1_SPEED);

        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.bulletRegionMoon = atlas.findRegion("bulletEnemy_type1");
        this.bulletRegionSpine = atlas.findRegion("bulletEnemy_type2");

        this.enemyPool = enemyPool;
        level = 1;
        diffFactor = 1f;
    }

    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setDiffFactor(float diff) {
        this.diffFactor = diff;
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= (Math.max(GENERATE_INTERVAL - (level * BOOST_GENERATE_INTERVAL), 1.5f))) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                float typeSmall = (float) Math.random();
                if (typeSmall < 0.5f & level > 2) {
                    enemy.set(
                            enemySmallT1Regions,
                            enemySmallV1,
                            bulletRegionMoon,
                            ENEMY_SMALL_V1_BULLET_HEIGHT,
                            ENEMY_SMALL_V1_BULLET_VY,
                            transform(ENEMY_SMALL_V1_BULLET_DAMAGE),
                            ENEMY_SMALL_V1_RELOAD_INTERVAL,
                            transform(ENEMY_SMALL_V1_HP),
                            ENEMY_SMALL_V1_HEIGHT,
                            ENEMY_SMALL_V1_SHOOT_TYPE,
                            ENEMY_SMALL_V1_SHIP_TYPE
                    );
                } else {
                    enemy.set(
                            enemySmallRegions,
                            enemySmallV,
                            bulletRegion,
                            ENEMY_SMALL_BULLET_HEIGHT,
                            ENEMY_SMALL_BULLET_VY,
                            transform(ENEMY_SMALL_BULLET_DAMAGE),
                            ENEMY_SMALL_RELOAD_INTERVAL,
                            transform(ENEMY_SMALL_HP),
                            ENEMY_SMALL_HEIGHT,
                            ENEMY_SMALL_SHOOT_TYPE,
                            ENEMY_SMALL_SHIP_TYPE
                    );
                }
            } else if (type < 0.8f) {
                float typeMedium = (float) Math.random();
                if (typeMedium < 0.5f & level > 4) {
                    enemy.set(
                            enemyMediumT1Regions,
                            enemyMediumV1,
                            bulletRegionMoon,
                            ENEMY_MEDIUM_V1_BULLET_HEIGHT,
                            ENEMY_MEDIUM_V1_BULLET_VY,
                            transform(ENEMY_MEDIUM_V1_BULLET_DAMAGE),
                            ENEMY_MEDIUM_V1_RELOAD_INTERVAL,
                            transform(ENEMY_MEDIUM_V1_HP),
                            ENEMY_MEDIUM_V1_HEIGHT,
                            ENEMY_MEDIUM_V1_SHOOT_TYPE,
                            ENEMY_MEDIUM_V1_SHIP_TYPE
                    );
                } else if (typeMedium < 0.8f & level > 6) {
                    enemy.set(
                            enemyMediumT2Regions,
                            enemyMediumV2,
                            bulletRegion,
                            ENEMY_MEDIUM_V2_BULLET_HEIGHT,
                            ENEMY_MEDIUM_V2_BULLET_VY,
                            transform(ENEMY_MEDIUM_V2_BULLET_DAMAGE),
                            ENEMY_MEDIUM_V2_RELOAD_INTERVAL,
                            transform(ENEMY_MEDIUM_V2_HP),
                            ENEMY_MEDIUM_V2_HEIGHT,
                            ENEMY_MEDIUM_V2_SHOOT_TYPE,
                            ENEMY_MEDIUM_V2_SHIP_TYPE
                    );
                } else {
                    enemy.set(
                            enemyMediumRegions,
                            enemyMediumV,
                            bulletRegion,
                            ENEMY_MEDIUM_BULLET_HEIGHT,
                            ENEMY_MEDIUM_BULLET_VY,
                            transform(ENEMY_MEDIUM_BULLET_DAMAGE),
                            ENEMY_MEDIUM_RELOAD_INTERVAL,
                            transform(ENEMY_MEDIUM_HP),
                            ENEMY_MEDIUM_HEIGHT,
                            ENEMY_MEDIUM_SHOOT_TYPE,
                            ENEMY_MEDIUM_SHIP_TYPE
                    );
                }
            } else {
                float typeBig = (float) Math.random();
                if (typeBig < 0.5f & level > 6) {
                    enemy.set(
                            enemyBigT1Regions,
                            enemyBigV1,
                            bulletRegionSpine,
                            ENEMY_BIG_V1_BULLET_HEIGHT,
                            ENEMY_BIG_V1_BULLET_VY,
                            transform(ENEMY_BIG_V1_BULLET_DAMAGE),
                            ENEMY_BIG_V1_RELOAD_INTERVAL,
                            transform(ENEMY_BIG_V1_HP),
                            ENEMY_BIG_V1_HEIGHT,
                            ENEMY_BIG_V1_SHOOT_TYPE,
                            ENEMY_BIG_V1_SHIP_TYPE
                    );
                } else {
                    enemy.set(
                            enemyBigRegions,
                            enemyBigV,
                            bulletRegion,
                            ENEMY_BIG_BULLET_HEIGHT,
                            ENEMY_BIG_BULLET_VY,
                            transform(ENEMY_BIG_BULLET_DAMAGE),
                            ENEMY_BIG_RELOAD_INTERVAL,
                            transform(ENEMY_BIG_HP),
                            ENEMY_BIG_HEIGHT,
                            ENEMY_BIG_SHOOT_TYPE,
                            ENEMY_BIG_SHIP_TYPE
                    );
                }
            }
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }

    private int transform(int value) {
        return (int)(value * diffFactor);
    }
}