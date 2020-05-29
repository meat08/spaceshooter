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

    public ButtonLoad(TextureAtlas atlas, Game game, FileHandle fileHandle) {
        super(atlas.findRegion("loadButton"));
        this.game = game;
        this.fileHandle = fileHandle;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + getHeight() + MARGIN + 0.01f);
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
