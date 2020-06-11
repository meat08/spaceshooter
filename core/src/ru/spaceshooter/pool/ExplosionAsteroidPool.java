package ru.spaceshooter.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.SpritesPool;
import ru.spaceshooter.sprite.ExplosionAsteroid;

public class ExplosionAsteroidPool extends SpritesPool<ExplosionAsteroid> {

    private TextureAtlas atlas;

    public ExplosionAsteroidPool(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    @Override
    protected ExplosionAsteroid newObject() {
        return new ExplosionAsteroid(atlas);
    }

}
