package ru.spaceshooter.sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.ScaledButton;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;

public class ButtonResume extends ScaledButton {

    private final GameScreen gameScreen;

    private static final float MARGIN = 0.05f;

    public ButtonResume(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("resume"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.085f);
        setBottom(worldBounds.getBottom() + MARGIN + getHeight() + 0.01f);
    }

    @Override
    public void action() {
        gameScreen.resumeGame();
    }
}
