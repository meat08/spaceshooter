package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.math.Rnd;
import ru.spaceshooter.utils.Regions;

public class EnemyShip extends Sprite {
    private Rect worldBounds;
    private Vector2 v;
    private String[] ships;

    public EnemyShip() {
        regions = new TextureRegion[1];
        v = new Vector2(0f, -0.2f);
        ships = new String[]{"enemy0","enemy1","enemy2"};
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    public void set(TextureAtlas atlas, Rect worldBounds) {
        this.worldBounds = worldBounds;
        final Random random = new Random();
        regions = Regions.split(atlas.findRegion(ships[random.nextInt(3)]), 1, 2, 2);
        final float vx = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        pos.set(vx, worldBounds.getTop());
        setHeightProportion(0.15f);
    }

}
