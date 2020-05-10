package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;

public class SphereSprite extends Sprite {
    private Vector2 currentPos;
    private Vector2 newPos;
    private Vector2 direction;

    public SphereSprite(Texture texture) {
        super(new TextureRegion(texture));
        currentPos = new Vector2(pos);
        newPos = new Vector2();
        direction = new Vector2();
    }

    @Override
    public void draw(SpriteBatch batch) {
        moveObjectToTouchPosition();
        super.draw(batch);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.15f);
        this.currentPos.set(worldBounds.pos);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        newPos = touch;
        direction.set((touch.cpy().sub(pos).setLength(0.01f)));
       return false;
    }

    private void moveObjectToTouchPosition() {
        if(pos.dst2(newPos) > direction.len2()) {
            pos.add(direction);
        }
        else  {
            pos.set(newPos);
        }
    }
}
