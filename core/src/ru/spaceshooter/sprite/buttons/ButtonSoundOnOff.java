package ru.spaceshooter.sprite.buttons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.ScaledButton;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;
import ru.spaceshooter.screen.MenuScreen;

public class ButtonSoundOnOff extends ScaledButton {

    private MenuScreen menuScreen;
    private GameScreen gameScreen;

    public ButtonSoundOnOff(TextureAtlas atlas, MenuScreen menuScreen) {
        super(atlas.findRegion("btnSoundOnOff"), 2, 1, 2);
        this.menuScreen = menuScreen;
        frame = 0;
    }

    public ButtonSoundOnOff(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("btnSoundOnOff"), 2, 1, 2);
        this.gameScreen = gameScreen;
        frame = 0;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + getHeight()*2 + MARGIN + 0.02f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (gameScreen != null) {
            if (gameScreen.isSoundOn()) {
                frame = 0;
            } else if (!gameScreen.isSoundOn()) {
                frame = 1;
            }
        } else if (menuScreen != null) {
            if (menuScreen.isSoundOn()) {
                frame = 0;
            } else if (!menuScreen.isSoundOn()) {
                frame = 1;
            }
        }
    }

    @Override
    public void action() {
        if (gameScreen != null) {
            gameScreen.setSoundOn();
        } else if (menuScreen != null) {
            menuScreen.setSoundOn();
        }
    }
}
