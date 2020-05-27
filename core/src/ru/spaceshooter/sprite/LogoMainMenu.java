package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.pool.HitExplodePool;

public class LogoMainMenu extends Sprite {
    private static final float MARGIN = 0.05f;
    private static final float ANIMATE_INTERVAL = 1f;

    private HitExplodePool hitExplodePool;
    private float animateTimer;

    public LogoMainMenu(TextureAtlas atlas, HitExplodePool hitExplodePool) {
        super(atlas.findRegion("menu_logo_ship"));
        this.hitExplodePool = hitExplodePool;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.25f);
        setTop(worldBounds.getTop() - MARGIN);
        setLeft(worldBounds.getLeft() + MARGIN);
        setRight(worldBounds.getRight());
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            HitExplode hitExplode = hitExplodePool.obtain();
            hitExplode.set(getHeight()/2, getLeft(), getRight(), getTop(), getBottom());
        }
    }
}
