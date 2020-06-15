package ru.spaceshooter.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.math.Rect;
import ru.spaceshooter.math.Rnd;
import ru.spaceshooter.pool.BossPool;
import ru.spaceshooter.sprite.Boss;

public class BossEmitter {

    private static final float BOSS_ONE_HEIGHT = 0.25f;
    private static final int BOSS_ONE_HP = 100;
    private static final float BOSS_ONE_SPEED = -0.12f;
    private static final float BOSS_ONE_BULLET_HEIGHT = 0.03f;
    private static final float BOSS_ONE_BULLET_VY = -0.45f;
    private static final int BOSS_ONE_BULLET_DAMAGE = 10;
    private static final float BOSS_ONE_RELOAD_INTERVAL = 0.6f;
    private static final int BOSS_ONE_SHOOT_TYPE = 7;
    private static final int BOSS_ONE_SHIP_TYPE = 4;
    private static final int BOSS_ONE_TYPE = 0;

    private static final float BOSS_TWO_HEIGHT = 0.25f;
    private static final int BOSS_TWO_HP = 120;
    private static final float BOSS_TWO_SPEED = -0.15f;
    private static final float BOSS_TWO_BULLET_HEIGHT = 0.03f;
    private static final float BOSS_TWO_BULLET_VY = -0.45f;
    private static final int BOSS_TWO_BULLET_DAMAGE = 10;
    private static final float BOSS_TWO_RELOAD_INTERVAL = 0.8f;
    private static final int BOSS_TWO_SHOOT_TYPE = 6;
    private static final int BOSS_TWO_SHIP_TYPE = 3;
    private static final int BOSS_TWO_TYPE = 1;

    private Rect worldBounds;

    private final TextureRegion[] bossOneRegion;
    private final TextureRegion[] bossTwoRegion;

    private final Vector2 bossOneV;
    private final Vector2 bossTwoV;

    private final TextureRegion bulletRegion;
    private final TextureRegion bulletRegionMoon;
    private final BossPool bossPool;

    public BossEmitter(TextureAtlas atlas, BossPool bossPool) {
        TextureRegion boss0 = atlas.findRegion("boss0");
        this.bossOneRegion = Regions.split(boss0, 1, 4, 4);
        TextureRegion boss1 = atlas.findRegion("boss1");
        this.bossTwoRegion = Regions.split(boss1, 1, 4, 4);

        this.bossOneV = new Vector2(BOSS_ONE_SPEED, 0f);
        this.bossTwoV = new Vector2(BOSS_TWO_SPEED, 0f);

        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.bulletRegionMoon = atlas.findRegion("bulletEnemy_type1");

        this.bossPool = bossPool;
    }

    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
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
                        BOSS_ONE_BULLET_DAMAGE,
                        BOSS_ONE_RELOAD_INTERVAL,
                        BOSS_ONE_HP,
                        BOSS_ONE_HEIGHT,
                        BOSS_ONE_SHOOT_TYPE,
                        BOSS_ONE_SHIP_TYPE,
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
                        BOSS_TWO_BULLET_DAMAGE,
                        BOSS_TWO_RELOAD_INTERVAL,
                        BOSS_TWO_HP,
                        BOSS_TWO_HEIGHT,
                        BOSS_TWO_SHOOT_TYPE,
                        BOSS_TWO_SHIP_TYPE,
                        BOSS_TWO_TYPE
                );
                break;
            }
        }
        boss.pos.x = Rnd.nextFloat(worldBounds.getLeft() + boss.getHalfWidth(), worldBounds.getRight() - boss.getHalfWidth());
        boss.setBottom(worldBounds.getTop());
    }

}