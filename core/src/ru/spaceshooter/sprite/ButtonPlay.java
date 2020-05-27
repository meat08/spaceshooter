package ru.spaceshooter.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.ScaledButton;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;

public class ButtonPlay extends ScaledButton {

    private final Game game;

    private static final float MARGIN = 0.05f;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.085f);
        setBottom(worldBounds.getBottom() + MARGIN + getHeight() + 0.01f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
