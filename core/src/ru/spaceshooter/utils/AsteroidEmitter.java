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
