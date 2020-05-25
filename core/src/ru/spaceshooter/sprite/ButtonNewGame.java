package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.ScaledButton;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;

public class ButtonNewGame extends ScaledButton {

    private GameScreen gameScreen;

    public ButtonNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.065f);
        setBottom(-0.05f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
