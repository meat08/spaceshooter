package ru.spaceshooter.sprite.buttons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.ScaledButton;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;
import ru.spaceshooter.screen.MenuScreen;

public class ButtonConfig extends ScaledButton {

    private static final String BUTTON_TEXT = "Настройки";

    private MenuScreen menuScreen;
    private GameScreen gameScreen;

    public ButtonConfig(TextureAtlas atlas, MenuScreen menuScreen) {
        super(atlas, BUTTON_TEXT);
        this.menuScreen = menuScreen;
    }

    public ButtonConfig(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas, BUTTON_TEXT);
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + getHeight()*2 + MARGIN + 0.02f);
    }

    @Override
    public void action() {
        if (gameScreen != null) {
            gameScreen.enterConfig();
        } else if (menuScreen != null) {
            menuScreen.enterConfig();
        }
    }
}
