package ru.spaceshooter.sprite.buttons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.ScaledButton;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;
import ru.spaceshooter.screen.MenuScreen;

public class ButtonAccelerometerOnOff extends ScaledButton {

    private MenuScreen menuScreen;
    private GameScreen gameScreen;

    public ButtonAccelerometerOnOff(TextureAtlas atlas, MenuScreen menuScreen) {
        super(atlas.findRegion("btnAccelOnOff"), 2, 1, 2);
        this.menuScreen = menuScreen;
        frame = 0;
    }

    public ButtonAccelerometerOnOff(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("btnAccelOnOff"), 2, 1, 2);
        this.gameScreen = gameScreen;
        frame = 0;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + getHeight()*3 + MARGIN + 0.03f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (gameScreen != null) {
            if (gameScreen.isAccelerometerOn()) {
                frame = 0;
            } else if (!gameScreen.isAccelerometerOn()) {
                frame = 1;
            }
        } else if (menuScreen != null) {
            if (menuScreen.isAccelerometerOn()) {
                frame = 0;
            } else if (!menuScreen.isAccelerometerOn()) {
                frame = 1;
            }
        }
    }

    @Override
    public void action() {
        if (gameScreen != null) {
            gameScreen.setAccelerometerOn();
        } else if (menuScreen != null) {
            menuScreen.setAccelerometerOn();
        }
    }
}
