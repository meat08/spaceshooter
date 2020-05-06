package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;

public class BackgroundSprite extends Sprite {

    public BackgroundSprite(Texture texture) {
        super(new TextureRegion(texture));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(1f);
        this.pos.set(worldBounds.pos);
    }
}
