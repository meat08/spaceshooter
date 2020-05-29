package ru.spaceshooter.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.spaceshooter.base.Ship;
import ru.spaceshooter.pool.BonusPool;

public class BonusEmitter {

    private static final float GENERATE_INTERVAL = 12f;

    private final TextureRegion[] hpBonusRegion;
    private final TextureRegion[] bulletBonusRegion;
    private final TextureRegion[] shieldBonusRegion;

    private BonusPool bonusPool;
    private Ship ship;
    private float generateTimer;

    public BonusEmitter (TextureAtlas atlas, BonusPool bonusPool, Ship ship) {
        this.hpBonusRegion = Regions.split(atlas.findRegion("bonus0"),2, 15, 28);
        this.bulletBonusRegion = Regions.split(atlas.findRegion("bonus1"),4, 15, 60);
        this.shieldBonusRegion = Regions.split(atlas.findRegion("bonus2"),2, 16, 30);
        this.bonusPool = bonusPool;
        this.ship = ship;
    }

    public void generate (float delta) {
        generateTimer += delta;
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
    }
}
