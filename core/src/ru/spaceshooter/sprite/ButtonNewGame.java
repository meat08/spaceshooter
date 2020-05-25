package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.ScaledButton;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;

public class ButtonNewGame extends ScaledButton {

    private static final float ANIMATE_INTERVAL = 0.8f;

    private GameScreen gameScreen;
    private float animateTimer;
    private boolean scaleUP = true;

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
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            scaleUP = !scaleUP;
        }
        if (scaleUP) {
            setScale(getScale() + 0.003f);
        } else {
            setScale(getScale() - 0.003f);
        }
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
