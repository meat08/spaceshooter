package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;

public class GameOver extends Sprite {

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.08f);
        setTop(0.1f);
    }
}
