package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.Title;
import ru.spaceshooter.math.Rect;

public class TitlePause extends Title {

    public TitlePause(TextureAtlas atlas) {
        super(atlas.findRegion("pause"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setTop(0.1f);
    }
}
