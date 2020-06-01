package ru.spaceshooter.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.SpritesPool;
import ru.spaceshooter.screen.GameScreen;
import ru.spaceshooter.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    private TextureAtlas atlas;
    private Sound sound;
    private GameScreen screen;

    public ExplosionPool(TextureAtlas atlas, GameScreen screen) {
        this.atlas = atlas;
        this.screen = screen;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(atlas, sound, screen);
    }

    @Override
    public void dispose() {
        super.dispose();
        sound.dispose();
    }
}
