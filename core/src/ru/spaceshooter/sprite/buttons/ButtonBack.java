package ru.spaceshooter.sprite.buttons;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.ScaledButton;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;
import ru.spaceshooter.screen.MenuScreen;

public class ButtonBack extends ScaledButton {

    private GameScreen gameScreen;
    private MenuScreen menuScreen;

    public ButtonBack(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("buttonBack"));
        this.gameScreen = gameScreen;
    }

    public ButtonBack(TextureAtlas atlas, MenuScreen menuScreen) {
        super(atlas.findRegion("buttonBack"));
        this.menuScreen = menuScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    @Override
    public void action() {
        if (gameScreen != null) {
            gameScreen.exitConfig();
            gameScreen.flushPreference();
        } else if (menuScreen != null) {
            menuScreen.exitConfig();
            menuScreen.flushPreference();
        }
    }
}
