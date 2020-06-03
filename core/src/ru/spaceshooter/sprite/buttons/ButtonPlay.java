package ru.spaceshooter.sprite.buttons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.ScaledButton;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;

public class ButtonPlay extends ScaledButton {

    private static final String BUTTON_TEXT = "Новая игра";

    private final Game game;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas, BUTTON_TEXT);
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + getHeight()*3 + MARGIN + 0.03f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
