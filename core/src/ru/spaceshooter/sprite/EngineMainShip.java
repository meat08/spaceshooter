package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.Ship;
import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;

public class EngineMainShip extends Sprite {

    private static final float ANIMATE_INTERVAL = 0.017f;

    private float animateTimer;

    public EngineMainShip(TextureAtlas atlas) {
        super(atlas.findRegion("engine_main_ship"), 1, 4, 4);
        frame = 0;
    }

    public void draw(SpriteBatch batch, Ship ship) {
        batch.draw(
                regions[frame],
                ship.getLeft()+0.005f, ship.getBottom()-0.023f,
                halfWidth, halfHeight
        );
        batch.draw(
                regions[frame],
                ship.getRight()-0.013f, ship.getBottom()-0.023f,
                halfWidth, halfHeight
        );
    }

    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.05f);
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                frame = 0;
            }
        }
    }
}
