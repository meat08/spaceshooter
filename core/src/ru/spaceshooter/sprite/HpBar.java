package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.Ship;
import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;

public class HpBar extends Sprite {

    private Ship ship;
    private float width;

    public HpBar(TextureAtlas atlas) {
        super(atlas.findRegion("hp_bar"));
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                width, halfHeight
        );
    }

    public void resize(Rect worldBounds, Ship ship) {
        super.resize(worldBounds);
        this.ship = ship;
        width = worldBounds.getWidth();
        setHeightProportion(0.01f);
        setWidth(width);
        setBottom(worldBounds.getBottom());
    }

    @Override
    public void update(float delta) {
        width = getWidth() * (((float)ship.getHp() / ship.getMaxHp()));
    }
}
