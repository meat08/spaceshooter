package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.Title;
import ru.spaceshooter.math.Rect;

public class TitleConfig extends Title {

    public TitleConfig(TextureAtlas atlas) {
        super(atlas.findRegion("confLogo"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setTop(0.1f);
    }
}
