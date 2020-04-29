package ru.spaceshooter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import ru.spaceshooter.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture backgroundImage;
    private Texture actionImage;
    private Sprite backgroundSprite;

    private float x = 0;
    private float y = 0;
    private float screenWidth;
    private float screenHeight;


    @Override
    public void show() {
        super.show();
        backgroundImage = new Texture("background.jpg");
        actionImage = new Texture("sphere.png");
        backgroundSprite = new Sprite(backgroundImage);
        screenWidth  = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        backgroundSprite.setSize(screenWidth, screenHeight);
        backgroundSprite.setPosition(0,0);
    }

    @Override
    public void render(float delta) {

        batch.begin();
        backgroundSprite.draw(batch);
        batch.draw(actionImage, x, y);
        batch.end();
    }

    @Override
    public void dispose() {
        actionImage.dispose();
        backgroundImage.dispose();
        super.dispose();
    }
}
