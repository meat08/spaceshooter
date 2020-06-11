package ru.spaceshooter.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;

public class ExplosionNuke extends Sprite {

    private static final float ANIMATE_INTERVAL = 0.12f;

    private float animateTimer;
    private Sound sound;
    private GameScreen screen;
    private Rect worldBounds;

    public ExplosionNuke(TextureAtlas atlas, GameScreen screen, Rect worldBounds) {
        super(atlas.findRegion("big_explosion"), 4, 3, 12);
        this.sound = Gdx.audio.newSound(Gdx.files.internal("sounds/big_explosion.wav"));
        this.screen = screen;
        this.worldBounds = worldBounds;
    }

    private void pew() {
        if (screen.isSoundOn()) {
            sound.play(screen.getVolumeSound());
        }
    }

    public void generate() {
        destroyed = false;
        screen.setNuked(true);
        setHeightProportion(worldBounds.getHalfHeight());
        this.pos.set(0f ,0f);
        pew();
        frame = 0;
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                destroy();
                screen.setNuked(false);
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }

    public void dispose() {
        sound.dispose();
    }
}
