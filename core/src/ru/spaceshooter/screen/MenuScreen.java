package ru.spaceshooter.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.BaseScreen;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.sprite.BackgroundSprite;
import ru.spaceshooter.sprite.ExitButtonSprite;
import ru.spaceshooter.sprite.PlayButtonSprite;


public class MenuScreen extends BaseScreen {
    private Texture exitButton;
    private Texture background;
    private Texture playButton;
    private ExitButtonSprite exitButtonSprite;
    private BackgroundSprite backgroundSprite;
    private PlayButtonSprite playButtonSprite;


    public MenuScreen(ScreenController screenController) {
        super(screenController);
    }

    @Override
    public void show() {
        super.show();
        exitButton = new Texture("textures/exit.png");
        background = new Texture("textures/bg.png");
        playButton = new Texture("textures/play.png");

        exitButtonSprite = new ExitButtonSprite(exitButton);
        backgroundSprite = new BackgroundSprite(background);
        playButtonSprite = new PlayButtonSprite(playButton, screenController);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        backgroundSprite.draw(batch);
        exitButtonSprite.draw(batch);
        playButtonSprite.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        backgroundSprite.resize(worldBounds);
        exitButtonSprite.resize(worldBounds);
        playButtonSprite.resize(worldBounds);
    }

    @Override
    public void dispose() {
        background.dispose();
        exitButton.dispose();
        playButton.dispose();
        super.dispose();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        exitButtonSprite.touchDown(touch, pointer, button);
        playButtonSprite.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        exitButtonSprite.touchUp(touch, pointer, button);
        playButtonSprite.touchUp(touch, pointer, button);
        return false;
    }
}
