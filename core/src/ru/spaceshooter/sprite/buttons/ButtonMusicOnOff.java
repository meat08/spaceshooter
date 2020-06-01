package ru.spaceshooter.sprite.buttons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.ScaledButton;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;
import ru.spaceshooter.screen.MenuScreen;

public class ButtonMusicOnOff extends ScaledButton {

    private MenuScreen menuScreen;
    private GameScreen gameScreen;

    public ButtonMusicOnOff(TextureAtlas atlas, MenuScreen menuScreen) {
        super(atlas.findRegion("btnMusicOnOff"), 2, 1, 2);
        this.menuScreen = menuScreen;
        frame = 0;
    }

    public ButtonMusicOnOff(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("btnMusicOnOff"), 2, 1, 2);
        this.gameScreen = gameScreen;
        frame = 0;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + getHeight() + MARGIN + 0.01f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (gameScreen != null) {
            if (gameScreen.isMusicOn()) {
                frame = 0;
            } else if (!gameScreen.isMusicOn()) {
                frame = 1;
            }
        } else if (menuScreen != null) {
            if (menuScreen.isMusicOn()) {
                frame = 0;
            } else if (!menuScreen.isMusicOn()) {
                frame = 1;
            }
        }
    }

    @Override
    public void action() {
        if (gameScreen != null) {
            gameScreen.setMusicOn();
            gameScreen.musicChange();
        } else if (menuScreen != null) {
            menuScreen.setMusicOn();
            menuScreen.musicChange();
        }
    }
}
