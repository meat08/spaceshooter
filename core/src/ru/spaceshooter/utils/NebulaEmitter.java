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
