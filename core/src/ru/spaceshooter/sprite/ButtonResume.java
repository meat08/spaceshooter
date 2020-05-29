package ru.spaceshooter.sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.ScaledButton;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;

public class ButtonResume extends ScaledButton {

    private final GameScreen gameScreen;

    public ButtonResume(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("resume"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + getHeight()*2 + MARGIN + 0.02f);
    }

    @Override
    public void action() {
        gameScreen.resumeGame();
    }
}
