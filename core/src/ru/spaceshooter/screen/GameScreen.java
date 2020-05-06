package ru.spaceshooter.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.BaseScreen;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.sprite.BackButtonSprite;
import ru.spaceshooter.sprite.BackgroundSprite;
import ru.spaceshooter.sprite.SphereSprite;

public class GameScreen extends BaseScreen {
    private Texture backgroundImage;
    private Texture actionImage;
    private Texture backButton;
    private BackButtonSprite backButtonSprite;
    private BackgroundSprite backgroundSprite;
    private SphereSprite actionSprite;

    public GameScreen(ScreenController screenController) {
        super(screenController);
    }

    @Override
    public void show() {
        super.show();
        backgroundImage = new Texture("textures/background.jpg");
        actionImage = new Texture("textures/sphere.png");
        backButton = new Texture("textures/back.png");

        backgroundSprite = new BackgroundSprite(backgroundImage);
        actionSprite = new SphereSprite(actionImage);
        backButtonSprite = new BackButtonSprite(backButton, screenController);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        backgroundSprite.draw(batch);
        actionSprite.draw(batch);
        backButtonSprite.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        actionImage.dispose();
        backgroundImage.dispose();
        backButton.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        actionSprite.touchDown(touch, pointer, button);
        backButtonSprite.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        backButtonSprite.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public void resize(Rect worldBounds) {
        backgroundSprite.resize(worldBounds);
        actionSprite.resize(worldBounds);
        backButtonSprite.resize(worldBounds);
    }

}
