package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.ScreenController;

public class PlayButtonSprite extends Sprite {
    private ScreenController screenController;

    public PlayButtonSprite(Texture texture, ScreenController screenController) {
        super(new TextureRegion(texture));
        this.screenController = screenController;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.15f);
        this.pos.set(-0.22f, -0.4f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(isMe(touch)) {
            setScale(0.7f);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(isMe(touch)) {
            setScale(1f);
            screenController.setGameScreen();
        }

        return false;
    }
}
