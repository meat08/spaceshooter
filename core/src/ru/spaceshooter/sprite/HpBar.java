package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.Ship;
import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;

public class HpBar extends Sprite {

    private float width;
    private Ship ship;

    public HpBar(TextureAtlas atlas) {
        super(atlas.findRegion("hp_bar"));
    }

    public void draw(SpriteBatch batch, float x, float y) {
        batch.draw(
                regions[frame],
                x, y,
                width, halfHeight
        );
    }

    public void resize(Rect worldBounds, Ship ship) {
        super.resize(worldBounds);
        this.width = ship.getWidth();
        this.ship = ship;
        setHeightProportion(width);
        setBottom(worldBounds.getBottom());
    }

    @Override
    public void update(float delta) {
        width = getWidth() * (((float)ship.getHp() / ship.getMaxHp()));
    }
}
