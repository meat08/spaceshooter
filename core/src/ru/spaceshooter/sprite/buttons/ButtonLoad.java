package ru.spaceshooter.sprite.buttons;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.ScaledButton;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;

public class ButtonLoad extends ScaledButton {

    private static final String BUTTON_TEXT = "Загрузить";

    private Game game;
    private GameScreen gameScreen;
    private final FileHandle fileHandle;

    public ButtonLoad(TextureAtlas atlas, Game game, FileHandle fileHandle) {
        super(atlas, BUTTON_TEXT);
        this.game = game;
        this.fileHandle = fileHandle;
    }

    public ButtonLoad(TextureAtlas atlas, GameScreen gameScreen, FileHandle fileHandle) {
        super(atlas, BUTTON_TEXT);
        this.gameScreen = gameScreen;
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
            if (gameScreen == null) {
                GameScreen gameScreen = new GameScreen();
                game.setScreen(gameScreen);
                gameScreen.loadGame();
            } else {
                gameScreen.loadGame();
            }
        }
    }
}
