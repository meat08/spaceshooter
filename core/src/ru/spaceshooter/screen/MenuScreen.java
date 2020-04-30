package ru.spaceshooter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture backgroundImage;
    private Texture actionImage;
    private Sprite backgroundSprite;
    private Vector2 pos;
    private Vector2 touch;
    private Vector2 direction;
    private float speed;

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
        pos = new Vector2();
        touch = new Vector2();
        direction = new Vector2();
        speed = 5;
    }

    @Override
    public void render(float delta) {

        batch.begin();
        backgroundSprite.draw(batch);
        moveObjectToTouchPosition();
        batch.draw(actionImage, pos.x, pos.y);
        batch.end();
    }

    private void moveObjectToTouchPosition() {
        if(pos.dst2(touch) > direction.len2()) {
            pos.add(direction);
        }
        else  {
            pos.set(touch);
        }
    }

    @Override
    public void dispose() {
        actionImage.dispose();
        backgroundImage.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        direction.set((touch.cpy().sub(pos).nor()).scl(speed));
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void resize(int width, int height) {
        backgroundSprite.setSize(width, height);
        batch.getProjectionMatrix().setToOrtho2D(0,0,width,height);
    }
}
