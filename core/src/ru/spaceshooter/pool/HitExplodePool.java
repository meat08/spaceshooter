package ru.spaceshooter.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.SpritesPool;
import ru.spaceshooter.sprite.HitExplode;

public class HitExplodePool extends SpritesPool<HitExplode> {

    private TextureAtlas atlas;

    public HitExplodePool(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    @Override
    protected HitExplode newObject() {
        return new HitExplode(atlas);
    }
}
