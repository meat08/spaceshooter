package ru.spaceshooter.sprite;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.ScaledButton;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;

public class ButtonLoad extends ScaledButton {

    private final Game game;
    private final FileHandle fileHandle;

    private static final float MARGIN = 0.05f;

    public ButtonLoad(TextureAtlas atlas, Game game, FileHandle fileHandle) {
        super(atlas.findRegion("loadButton"));
        this.game = game;
        this.fileHandle = fileHandle;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(BUTTON_SIZE);
        setBottom(worldBounds.getBottom() + MARGIN + getHeight() + 0.01f);
    }

    @Override
    public void action() {
        if (fileHandle.exists()) {
            GameScreen gameScreen = new GameScreen();
            game.setScreen(gameScreen);
            gameScreen.loadGame();
        }
    }
}
