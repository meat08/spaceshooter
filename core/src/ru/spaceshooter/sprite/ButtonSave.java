package ru.spaceshooter.sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.ScaledButton;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;

public class ButtonSave extends ScaledButton {

    private final GameScreen gameScreen;

    private static final float MARGIN = 0.05f;

    public ButtonSave(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("saveButton"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(BUTTON_SIZE);
        setBottom(worldBounds.getBottom() + MARGIN + getHeight() + 0.01f);
    }

    @Override
    public void action() {
        gameScreen.saveGame();
    }
}
