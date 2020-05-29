package ru.spaceshooter.sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.ScaledButton;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;

public class ButtonMusicMute extends ScaledButton {

    private final GameScreen gameScreen;

    public ButtonMusicMute(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("musicOnOff"), 1, 2, 2);
        this.gameScreen = gameScreen;
        frame = 1;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(BUTTON_SIZE);
        setBottom(worldBounds.getBottom() + 0.01f);
        setRight(worldBounds.getRight() - 0.01f);
    }

    @Override
    public void update(float delta) {
        if (gameScreen.isMusicOn()) {
            frame = 1;
        } else {
            frame = 0;
        }
    }

    @Override
    public void action() {
        gameScreen.musicOnOff();
    }
}
