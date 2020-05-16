package ru.spaceshooter.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.BaseScreen;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.pool.BulletPool;
import ru.spaceshooter.pool.EnemyShipPool;
import ru.spaceshooter.sprite.Background;
import ru.spaceshooter.sprite.EnemyShip;
import ru.spaceshooter.sprite.PlayerShip;
import ru.spaceshooter.sprite.Star;

public class GameScreen extends BaseScreen {

    private static final float ENEMY_INTERVAL = 1.5f;

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private PlayerShip playerShip;
    private Star[] stars;
    private BulletPool bulletPool;
    private EnemyShipPool enemyShipPool;
    private Sound bulletSound;
    private Music gameMusic;
    private Rect worldBounds;
    private float enemyTimer;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        stars = new Star[64];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        enemyShipPool = new EnemyShipPool();
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        playerShip = new PlayerShip(atlas, bulletPool, bulletSound);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameScreen.mp3"));
        gameMusic.play();
        gameMusic.setVolume(0.5f);
        gameMusic.setLooping(true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        free();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        playerShip.resize(worldBounds);

    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyShipPool.dispose();
        bulletSound.dispose();
        gameMusic.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        playerShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        playerShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        playerShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        playerShip.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        playerShip.touchDragged(touch, pointer);
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        enemyTimer += delta;
        if (enemyTimer >= ENEMY_INTERVAL) {
            createEnemy();
            enemyTimer = 0f;
        }
        bulletPool.updateActiveSprites(delta);
        enemyShipPool.updateActiveSprites(delta);
        playerShip.update(delta);
    }

    private void createEnemy() {
        EnemyShip enemyShip = enemyShipPool.obtain();
        enemyShip.set(atlas, worldBounds);
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        bulletPool.drawActiveSprites(batch);
        enemyShipPool.drawActiveSprites(batch);
        playerShip.draw(batch);
        batch.end();
    }

    private void free() {
        bulletPool.freeAllDestroyed();
        enemyShipPool.freeAllDestroyed();
    }
}
